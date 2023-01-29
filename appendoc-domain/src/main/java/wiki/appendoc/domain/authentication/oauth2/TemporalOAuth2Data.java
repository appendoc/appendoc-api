package wiki.appendoc.domain.authentication.oauth2;

import java.time.LocalDateTime;

public record TemporalOAuth2Data(
        String idFromProvider,
        OAuth2Authentication.Provider provider,
        String email,
        LocalDateTime createdAt
) {
}