package wiki.appendoc.application.authentication.port.outbound;

import wiki.appendoc.domain.authentication.oauth2.OAuth2Authentication;

public interface SaveOAuth2AuthenticationPort {

    void save(OAuth2Authentication oAuth2Authentication);
}
