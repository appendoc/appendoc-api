package wiki.appendoc.application.authentication.port.outbound;

import wiki.appendoc.domain.authentication.oauth2.google.ProfileFromGoogle;

public interface SearchGoogleProfilePort {
    ProfileFromGoogle search(String token);
}
