package wiki.appendoc.api.users;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wiki.appendoc.api.users.params.SignUpRequest;
import wiki.appendoc.api.users.params.SignUpResponse;
import wiki.appendoc.application.users.port.inbound.CreateNewUserAccountWithOAuth2UseCase;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/users")
public class SignUpController {

    private final CreateNewUserAccountWithOAuth2UseCase useCaseForOAuth2;

    public SignUpController(CreateNewUserAccountWithOAuth2UseCase useCaseForOAuth2) {
        this.useCaseForOAuth2 = useCaseForOAuth2;
    }

    @PostMapping
    public SignUpResponse signUp(@RequestBody SignUpRequest request) {
        log.debug("request: {}", request);
        if (request instanceof SignUpRequest.ByOAuth2 byOAuth2) {
            final var createNewAccountRequest = new CreateNewUserAccountWithOAuth2UseCase.CreateNewUserAccountWithOAuth2Request(
                    byOAuth2.getSignUpKey(),
                    byOAuth2.getDisplayName(),
                    LocalDateTime.now()
            );
            useCaseForOAuth2.signUp(createNewAccountRequest);
            return new SignUpResponse.SignUpAccepted();
        }
        return new SignUpResponse.SignUpDeclined();
    }
}
