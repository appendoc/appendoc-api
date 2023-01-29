package wiki.appendoc.domain.authentication;

import java.time.Duration;
import java.time.LocalDateTime;

public interface TokenIssuerDomainService {
    String REFRESH_TOKEN_NAME = "refresh_token";
    String ACCESS_TOKEN_NAME = "access_token";

    record Token(
            String tokenName,
            String value,
            Duration retention,
            String domain,
            String path
    ) {

    }

    boolean isValidToken(Token token);

    Token issueRefreshToken(String wikiUserId, LocalDateTime issuedAt);

    Token issueAccessToken(String wikiUserId, LocalDateTime issuedAt);

    String extractRawPayloadPartFromTokenValue(String tokenValue);

    String extractUidFromTokenValue(String tokenValue);
}
