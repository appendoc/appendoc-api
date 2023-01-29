package wiki.appendoc.domain.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Base64;

public class TokenIssuer implements TokenIssuerDomainService {

    final Algorithm algorithm;
    private final String issuer;
    private final String domain;
    private final String path;
    private final JWTVerifier jwtVerifier;

    public TokenIssuer(
            String secret,
            String issuer,
            String domain,
            String path
    ) {
        this.algorithm = Algorithm.HMAC512(secret);
        this.issuer = issuer;
        this.domain = domain;
        this.path = path;
        this.jwtVerifier = JWT.require(algorithm).acceptLeeway(2).build();
    }

    @Override
    public boolean isValidToken(Token token) {
        try {
            jwtVerifier.verify(token.value());
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    @Override
    public Token issueRefreshToken(String wikiUserId, LocalDateTime issuedAt) {
        final ZoneId oldZone = ZoneId.systemDefault();
        final ZoneId utc = ZoneId.of("UTC");
        final LocalDateTime issuedAtInUtc = issuedAt.atZone(oldZone).withZoneSameInstant(utc).toLocalDateTime();
        var tokenValue = JWT.create()
                .withIssuer(issuer)
                .withClaim("uid", wikiUserId)
                .withSubject(REFRESH_TOKEN_NAME)
                .withIssuedAt(issuedAtInUtc.toInstant(ZoneOffset.UTC))
                .withExpiresAt(issuedAtInUtc.plusDays(15).toInstant(ZoneOffset.UTC))
                .sign(algorithm);
        return new Token(
                REFRESH_TOKEN_NAME,
                tokenValue,
                Duration.ofDays(15),
                domain,
                path
        );
    }

    @Override
    public Token issueAccessToken(String wikiUserId, LocalDateTime issuedAt) {
        final ZoneId oldZone = ZoneId.systemDefault();
        final ZoneId utc = ZoneId.of("UTC");
        final LocalDateTime issuedAtInUtc = issuedAt.atZone(oldZone).withZoneSameInstant(utc).toLocalDateTime();
        var tokenValue = JWT.create()
                .withIssuer(issuer)
                .withClaim("uid", wikiUserId)
                .withSubject(ACCESS_TOKEN_NAME)
                .withIssuedAt(issuedAtInUtc.toInstant(ZoneOffset.UTC))
                .withExpiresAt(issuedAtInUtc.plusMinutes(5).toInstant(ZoneOffset.UTC))
                .sign(algorithm);
        return new Token(
                ACCESS_TOKEN_NAME,
                tokenValue,
                Duration.ofMinutes(15),
                domain,
                path
        );
    }

    @Override
    public String extractRawPayloadPartFromTokenValue(String tokenValue) {
        final DecodedJWT decodedJWT = jwtVerifier.verify(tokenValue);
        final byte[] decode = Base64.getDecoder().decode(decodedJWT.getPayload());
        return new String(decode);
    }

    @Override
    public String extractUidFromTokenValue(String tokenValue) {
        final String rawPayload = extractRawPayloadPartFromTokenValue(tokenValue);
        if (!rawPayload.contains("uid")) {
            return null;
        }
        final String[] attributes = rawPayload.split(",");
        for (String attribute : attributes) {
            if (attribute != null && attribute.contains("uid")) {
                final String uid = attribute.split(":")[1];
                return uid.trim();
            }
        }
        return null;
    }
}
