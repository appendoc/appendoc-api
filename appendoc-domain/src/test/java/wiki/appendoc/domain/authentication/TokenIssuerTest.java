package wiki.appendoc.domain.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TokenIssuerTest {

    private final TokenIssuerDomainService sut = new TokenIssuer("abc", "test", "localhost", "/");

    @DisplayName("리프레시 토큰 - 정상")
    @Test
    void testRefreshToken() {
        final var token = sut.issueRefreshToken(UUID.randomUUID().toString(), LocalDateTime.now());
        final boolean isValid = sut.isValidToken(token);
        assertTrue(isValid);
    }

    @DisplayName("리프레시 토큰 - 만료")
    @Test
    void testExpiredRefreshToken() {
        final LocalDateTime issuedAt = LocalDateTime.now().minusDays(15).minusSeconds(3);
        final var token = sut.issueRefreshToken(UUID.randomUUID().toString(), issuedAt);
        final boolean isValid = sut.isValidToken(token);
        assertFalse(isValid);
    }

    @DisplayName("액세스 토큰 - 정상")
    @Test
    void testAccessToken() {
        final var token = sut.issueAccessToken(
                UUID.randomUUID().toString(),
                LocalDateTime.now()
        );
        final boolean isValid = sut.isValidToken(token);
        assertTrue(isValid);
    }

    @DisplayName("액세스 토큰 - 만료")
    @Test
    void testExpiredAccessToken() {
        final LocalDateTime issuedAt = LocalDateTime.now().minusDays(15).minusSeconds(3);
        final var token = sut.issueAccessToken(
                UUID.randomUUID().toString(),
                issuedAt
        );
        final boolean isValid = sut.isValidToken(token);
        assertFalse(isValid);
    }

    @Test
    void test() throws InterruptedException {
        final LocalDateTime oldTime = LocalDateTime.now();
        final ZoneId oldZone = ZoneId.systemDefault();
        final ZoneId utc = ZoneId.of("UTC");
        final LocalDateTime newLdt = oldTime.atZone(oldZone).withZoneSameInstant(utc).toLocalDateTime();
        System.out.println("oldTime = " + oldTime);
        System.out.println("newLdt = " + newLdt);

        final Algorithm algorithmHMAC512 = Algorithm.HMAC512("abc");
        final LocalDateTime issuedAt = LocalDateTime.now(ZoneId.of("UTC"));
        final String token = JWT.create()
                .withIssuer("127.0.0.1:8080")
                .withClaim("email", "hyeyoom@appendoc.wiki")
                .withClaim("tokenName", "혜윰")
                .withClaim("uid", UUID.randomUUID().toString())
                .withIssuedAt(issuedAt.plusSeconds(2).toInstant(ZoneOffset.UTC))
                .withExpiresAt(issuedAt.plusDays(15).toInstant(ZoneOffset.UTC))
                .sign(algorithmHMAC512);

        System.out.println("token = " + token);
        System.out.println(issuedAt);
        final JWTVerifier jwtVerifier = JWT.require(algorithmHMAC512).acceptLeeway(2).build();
        final DecodedJWT decodedJWT = jwtVerifier.verify(token);
        final String payload = sut.extractRawPayloadPartFromTokenValue(token);
        System.out.println("decodedJWT = " + decodedJWT.getPayload());
        System.out.println("payload = " + payload);
    }
}