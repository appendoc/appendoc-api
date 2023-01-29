package wiki.appendoc.application.authentication.port.outbound;

public interface EvictTemporarilyOAuth2DataPort {
    void evict(String key);
}
