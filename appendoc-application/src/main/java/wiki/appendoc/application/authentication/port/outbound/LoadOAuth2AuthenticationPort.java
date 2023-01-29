package wiki.appendoc.application.authentication.port.outbound;

import wiki.appendoc.domain.authentication.oauth2.OAuth2Authentication;

public interface LoadOAuth2AuthenticationPort {

    final class WrongProviderException extends RuntimeException {
        public WrongProviderException(String message) {
            super(message);
        }
    }

    record LoadOAuth2AuthenticationRequest(
            String idFromProvider,
            String verifiedEmail,
            String provider
    ) { }

    OAuth2Authentication load(LoadOAuth2AuthenticationRequest request);
}
