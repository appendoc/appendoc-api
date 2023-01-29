package wiki.appendoc.application.authentication.port.inbound;

import java.time.LocalDateTime;

public interface ProcessGoogleOAuth2CallbackUseCase {
    record ProcessGoogleOAuth2CallbackRequest(
            String code,
            LocalDateTime requestedAt
    ) {
    }

    sealed interface ProcessGoogleOAuth2CallbackResult {

        final class UserNotVerified implements ProcessGoogleOAuth2CallbackResult {
        }

        record UserNotSignedUp(String signUpKey, String email) implements ProcessGoogleOAuth2CallbackResult {

        }

        record AlreadySignedUpUser(
                ProcessGoogleOAuth2CallbackResult.AlreadySignedUpUser.Token accesToken,
                ProcessGoogleOAuth2CallbackResult.AlreadySignedUpUser.Token refreshToken
        ) implements ProcessGoogleOAuth2CallbackResult {
            public record Token(String name, String value, long retentionInSeconds, String domain, String path) {
            }
        }
    }

    ProcessGoogleOAuth2CallbackResult process(ProcessGoogleOAuth2CallbackRequest request);
}
