package wiki.appendoc.domain.authentication.oauth2;

import java.util.UUID;

public record OAuth2AuthenticationId(String value) {

    static OAuth2AuthenticationId createOAuth2AuthenticationId() {
        final String value = UUID.randomUUID().toString();
        return new OAuth2AuthenticationId(value);
    }

    public static OAuth2AuthenticationId loadFromExistingValue(String value) {
        return new OAuth2AuthenticationId(value);
    }
}
