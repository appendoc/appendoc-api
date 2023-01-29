package wiki.appendoc.resolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wiki.appendoc.domain.authentication.TokenIssuer;
import wiki.appendoc.domain.authentication.TokenIssuerDomainService;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

class WikiUserMethodArgumentResolverTest {

    private final ObjectMapper objectMapper = JsonMapper.builder()
            .addModules(new ParameterNamesModule(), new Jdk8Module(), new JavaTimeModule())
            .configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(WRITE_DATES_AS_TIMESTAMPS, false)
            .build();

    private final TokenIssuerDomainService sut = new TokenIssuer("abc", "test", "localhost", "/");

    @Disabled
    @DisplayName("learning test")
    @Test
    void test() throws JsonProcessingException {
        final var token = sut.issueRefreshToken(UUID.randomUUID().toString(), LocalDateTime.now());
        final String rawPayload = sut.extractRawPayloadPartFromTokenValue(token.value());
        final Map map = objectMapper.readValue(rawPayload, Map.class);
        final String uid = sut.extractUidFromTokenValue(token.value());
        System.out.println("map = " + map);
        System.out.println("uid = " + uid);
    }
}