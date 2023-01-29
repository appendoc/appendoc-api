package wiki.appendoc.api.authentication;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wiki.appendoc.application.authentication.port.inbound.GetGoogleAuthorizationUrlUseCase;
import wiki.appendoc.application.authentication.port.inbound.ProcessGoogleOAuth2CallbackUseCase;

import java.time.LocalDateTime;

@Slf4j
@RestController
public class GoogleAuthenticationController {

    private final GetGoogleAuthorizationUrlUseCase getGoogleAuthorizationUrlUseCase;
    private final ProcessGoogleOAuth2CallbackUseCase processGoogleOAuth2CallbackUseCase;

    public GoogleAuthenticationController(
            GetGoogleAuthorizationUrlUseCase getGoogleAuthorizationUrlUseCase,
            ProcessGoogleOAuth2CallbackUseCase processGoogleOAuth2CallbackUseCase
    ) {
        this.getGoogleAuthorizationUrlUseCase = getGoogleAuthorizationUrlUseCase;
        this.processGoogleOAuth2CallbackUseCase = processGoogleOAuth2CallbackUseCase;
    }

    record GetAuthorizationUrlResponse(String authorizationUrl) {
    }

    @GetMapping("/authentication/google/url/authorization")
    public GetAuthorizationUrlResponse getAuthorizationUrl() {
        final String authorizationUrl = getGoogleAuthorizationUrlUseCase.getGoogleAuthorizationUrl();
        final GetAuthorizationUrlResponse response = new GetAuthorizationUrlResponse(authorizationUrl);
        log.debug("/google/url/authorization - response: {}", response);
        return response;
    }

    record GoogleOAuth2AuthenticationRequest(
            String code,
            String state,
            String authuser,
            String prompt
    ) {
    }

    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            property = "type"
    )
    @JsonSubTypes({
            @JsonSubTypes.Type(
                    value = GoogleOAuth2AuthenticationResponse.UserNotVerified.class,
                    name = "USER_NOT_VERIFIED"
            ),
            @JsonSubTypes.Type(
                    value = GoogleOAuth2AuthenticationResponse.UserNotSignedUp.class,
                    name = "USER_NOT_SIGNED_UP"
            ),
            @JsonSubTypes.Type(
                    value = GoogleOAuth2AuthenticationResponse.UserAlreadySignedUp.class,
                    name = "USER_ALREADY_SIGNED_UP"
            )
    })
    sealed static class GoogleOAuth2AuthenticationResponse {

        private final Type type;

        public GoogleOAuth2AuthenticationResponse(Type type) {
            this.type = type;
        }

        @Getter
        public static final class UserNotVerified extends GoogleOAuth2AuthenticationResponse {

            public UserNotVerified() {
                super(Type.USER_NOT_VERIFIED);
            }
        }

        @Getter
        public static final class UserNotSignedUp extends GoogleOAuth2AuthenticationResponse {

            private final String signUpKey;
            private final String email;

            public UserNotSignedUp(String signUpKey, String email) {
                super(Type.USER_NOT_SIGNED_UP);
                this.signUpKey = signUpKey;
                this.email = email;
            }

        }

        @Getter
        public static final class UserAlreadySignedUp extends GoogleOAuth2AuthenticationResponse {

            public UserAlreadySignedUp() {
                super(Type.USER_ALREADY_SIGNED_UP);
            }
        }

        enum Type {
            USER_NOT_VERIFIED,
            USER_NOT_SIGNED_UP,
            USER_ALREADY_SIGNED_UP
        }
    }

    @PostMapping("/authentication/google/callback")
    public ResponseEntity<GoogleOAuth2AuthenticationResponse>
    authenticate(@RequestBody GoogleOAuth2AuthenticationRequest request) {
        final var processGoogleOAuth2CallbackParam = getProcessGoogleOAuth2CallbackParam(request);
        final var result = processGoogleOAuth2CallbackUseCase.process(processGoogleOAuth2CallbackParam);
        return handleResult(result);
    }

    private static ProcessGoogleOAuth2CallbackUseCase.ProcessGoogleOAuth2CallbackRequest
    getProcessGoogleOAuth2CallbackParam(GoogleOAuth2AuthenticationRequest request) {
        log.debug("google callback request: {}", request);
        return new ProcessGoogleOAuth2CallbackUseCase.ProcessGoogleOAuth2CallbackRequest(
                request.code,
                LocalDateTime.now()
        );
    }

    private static ResponseEntity<GoogleOAuth2AuthenticationResponse>
    handleResult(ProcessGoogleOAuth2CallbackUseCase.ProcessGoogleOAuth2CallbackResult result) {
        log.debug("process result: {}", result);

        if (result instanceof ProcessGoogleOAuth2CallbackUseCase.ProcessGoogleOAuth2CallbackResult
                .UserNotSignedUp userNotSignedUp
        ) {
            final var userNotSignedUpResponse = new GoogleOAuth2AuthenticationResponse.UserNotSignedUp(
                    userNotSignedUp.signUpKey(),
                    userNotSignedUp.email()
            );
            return ResponseEntity.ok(userNotSignedUpResponse);
        }

        if (result instanceof ProcessGoogleOAuth2CallbackUseCase.ProcessGoogleOAuth2CallbackResult
                .AlreadySignedUpUser alreadySignedUpUser) {
            final var accesToken = alreadySignedUpUser.accesToken();
            final var accessTokenCookie = buildCookie(accesToken);
            final var refreshToken = alreadySignedUpUser.refreshToken();
            final var refreshTokenCookie = buildCookie(refreshToken);
            final var response = new GoogleOAuth2AuthenticationResponse.UserAlreadySignedUp();
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                    .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                    .body(response);
        }

        final var response = new GoogleOAuth2AuthenticationResponse.UserNotVerified();
        return ResponseEntity.ok(response);
        /*return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, "value1=None; Path=/; Domain=127.0.0.1; Max-Age=3600; Secure; HttpOnly; SameSite=None")
                .header(HttpHeaders.SET_COOKIE, "value2=Strict; Path=/; Domain=127.0.0.1; Max-Age=3600; Secure; HttpOnly; SameSite=Strict")
                .header(HttpHeaders.SET_COOKIE, "value3=LAX; Path=/; Domain=127.0.0.1; Max-Age=3600; Secure; HttpOnly; SameSite=LAX")
                .body(response);*/
    }

    private static ResponseCookie buildCookie(
            ProcessGoogleOAuth2CallbackUseCase.ProcessGoogleOAuth2CallbackResult.AlreadySignedUpUser.Token token
    ) {
        return ResponseCookie.from(token.name(), token.value())
                .maxAge(token.retentionInSeconds())
                .secure(false)
                .httpOnly(true)
                .domain(token.domain())
                .path(token.path())
                .build();
    }
}
