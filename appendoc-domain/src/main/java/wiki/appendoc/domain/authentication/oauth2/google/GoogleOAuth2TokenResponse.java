package wiki.appendoc.domain.authentication.oauth2.google;

public record GoogleOAuth2TokenResponse(
        String accessToken,
        long expiresIn,
        String scope,
        String tokenType,
        String idToken
) {
}
