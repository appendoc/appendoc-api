package wiki.appendoc.adapter.authentication.outbound.cache;

import lombok.extern.slf4j.Slf4j;
import wiki.appendoc.adapter.CacheStorage;
import wiki.appendoc.application.authentication.port.outbound.EvictTemporarilyOAuth2DataPort;
import wiki.appendoc.application.authentication.port.outbound.LoadTemporarilyOAuth2DataPort;
import wiki.appendoc.application.authentication.port.outbound.SaveTemporarilyOAuth2DataPort;
import wiki.appendoc.application.users.port.outbound.CheckTempAuthDataExistencePort;
import wiki.appendoc.domain.authentication.oauth2.TemporalOAuth2Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 지금은 싱글 인스턴스이기 때문에 인메모리 구현으로 끝낸다.
 * 나중에 멀티 인스턴스의 구현으로 변경되면 이 안은 폐기하고 redis로 옮긴다.
 */
@Slf4j
@CacheStorage
public class TemporalOAuth2DataRepository
        implements SaveTemporarilyOAuth2DataPort, LoadTemporarilyOAuth2DataPort, EvictTemporarilyOAuth2DataPort, CheckTempAuthDataExistencePort {

    private static final long CACHE_RETENTION_IN_SECONDS = 60 * 60;
    private final Map<String, TemporalOAuth2Data> cache = new ConcurrentHashMap<>();

    @Override
    public void save(String key, TemporalOAuth2Data data) {
        log.debug("data {} saved by key {}", data, key);
        optimizeCapacity();
        cache.put(key, data);
    }

    private void optimizeCapacity() {
        final var now = LocalDateTime.now();
        final var candidates = new ArrayList<String>();
        for (Map.Entry<String, TemporalOAuth2Data> entry : cache.entrySet()) {
            final var createdAt = entry.getValue().createdAt();
            final var duration = Duration.between(createdAt, now);
            final var minutes = duration.toMinutes();
            if (minutes > CACHE_RETENTION_IN_SECONDS) {
                candidates.add(entry.getKey());
            }
        }
        for (var candidate : candidates) {
            cache.remove(candidate);
        }
    }

    @Override
    public TemporalOAuth2Data load(String key) {
        return cache.get(key);
    }

    @Override
    public void evict(String key) {
        cache.remove(key);
    }

    @Override
    public boolean exists(String key) {
        return cache.get(key) != null;
    }
}
