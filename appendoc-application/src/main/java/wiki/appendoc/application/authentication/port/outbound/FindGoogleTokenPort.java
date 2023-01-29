package wiki.appendoc.application.authentication.port.outbound;

import wiki.appendoc.domain.authentication.oauth2.google.GoogleOAuth2TokenRequest;
import wiki.appendoc.domain.authentication.oauth2.google.GoogleOAuth2TokenResponse;

public interface FindGoogleTokenPort {
    GoogleOAuth2TokenResponse find(GoogleOAuth2TokenRequest request);
}
