package wiki.appendoc.application.users.port.inbound;

import java.time.LocalDateTime;

public interface CreateNewUserAccountWithOAuth2UseCase {

    record CreateNewUserAccountWithOAuth2Request(
            String signUpKey,
            String displayName,
            LocalDateTime requestAt
    ) {
    }

    record CreateNewUserAccountWithOAuth2Response() {
    }

    CreateNewUserAccountWithOAuth2Response signUp(CreateNewUserAccountWithOAuth2Request request);
}
