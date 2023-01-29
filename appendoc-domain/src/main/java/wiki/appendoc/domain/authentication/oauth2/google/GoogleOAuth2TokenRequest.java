package wiki.appendoc.domain.authentication.oauth2.google;

public record GoogleOAuth2TokenRequest(
        String clientId,
        String clientSecret,
        String code,
        String grantType,
        String redirectUri
) {
}
