package wiki.appendoc.domain.authentication.oauth2;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class OAuth2Authentication {

    private final OAuth2AuthenticationId oAuth2AuthenticationId;
    private final String wikiUserId;
    private final String idFromProvider;
    private final Provider provider;
    private final String verifiedEmail;


    public static OAuth2Authentication createNew(
            String wikiUserId,
            String idFromProvider,
            Provider provider,
            String verifiedEmail
    ) {
        return new OAuth2Authentication(
                OAuth2AuthenticationId.createOAuth2AuthenticationId(),
                wikiUserId,
                idFromProvider,
                provider,
                verifiedEmail
        );
    }

    public static OAuth2Authentication load(
            String oAuth2AuthenticationIdValue,
            String wikiUserId,
            String idFromProvider,
            Provider provider,
            String verifiedEmail
    ) {
        return new OAuth2Authentication(
                OAuth2AuthenticationId.loadFromExistingValue(oAuth2AuthenticationIdValue),
                wikiUserId,
                idFromProvider,
                provider,
                verifiedEmail
        );
    }

    public enum Provider {
        GOOGLE
    }
}
