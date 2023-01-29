package wiki.appendoc.application.authentication.service;

import org.springframework.beans.factory.annotation.Value;
import wiki.appendoc.application.UseCase;
import wiki.appendoc.application.authentication.port.inbound.ProcessGoogleOAuth2CallbackUseCase;
import wiki.appendoc.application.authentication.port.outbound.FindGoogleTokenPort;
import wiki.appendoc.application.authentication.port.outbound.LoadOAuth2AuthenticationPort;
import wiki.appendoc.application.authentication.port.outbound.SaveTemporarilyOAuth2DataPort;
import wiki.appendoc.application.authentication.port.outbound.SearchGoogleProfilePort;
import wiki.appendoc.domain.authentication.TokenIssuerDomainService;
import wiki.appendoc.domain.authentication.oauth2.OAuth2Authentication;
import wiki.appendoc.domain.authentication.oauth2.TemporalOAuth2Data;
import wiki.appendoc.domain.authentication.oauth2.google.GoogleOAuth2TokenRequest;

import java.util.UUID;

@UseCase
public class GoogleOAuth2CallbackAuthenticator implements ProcessGoogleOAuth2CallbackUseCase {

    @Value("${application.config.oauth2.google.clientId:clientId}")
    private String clientId;

    @Value("${application.config.oauth2.google.clientSecret:clientSecret}")
    private String clientSecret;

    @Value("${application.config.oauth2.google.redirectUri:redirectUri}")
    private String redirectUri;

    private final FindGoogleTokenPort findGoogleTokenPort;
    private final SearchGoogleProfilePort searchGoogleProfilePort;
    private final LoadOAuth2AuthenticationPort loadOAuth2AuthenticationPort;
    private final SaveTemporarilyOAuth2DataPort saveTemporarilyOAuth2DataPort;
    private final TokenIssuerDomainService tokenIssuerDomainService;

    public GoogleOAuth2CallbackAuthenticator(
            FindGoogleTokenPort findGoogleTokenPort,
            SearchGoogleProfilePort searchGoogleProfilePort,
            LoadOAuth2AuthenticationPort loadOAuth2AuthenticationPort,
            SaveTemporarilyOAuth2DataPort saveTemporarilyOAuth2DataPort,
            TokenIssuerDomainService tokenIssuerDomainService
    ) {
        this.findGoogleTokenPort = findGoogleTokenPort;
        this.searchGoogleProfilePort = searchGoogleProfilePort;
        this.loadOAuth2AuthenticationPort = loadOAuth2AuthenticationPort;
        this.saveTemporarilyOAuth2DataPort = saveTemporarilyOAuth2DataPort;
        this.tokenIssuerDomainService = tokenIssuerDomainService;
    }

    @Override
    public ProcessGoogleOAuth2CallbackResult process(ProcessGoogleOAuth2CallbackRequest request) {
        final var googleOAuth2TokenRequest = convertToGoogleOAuth2Request(request);
        final var googleOAuth2TokenResponse = findGoogleTokenPort.find(googleOAuth2TokenRequest);
        final var profileFromGoogle = searchGoogleProfilePort.search(googleOAuth2TokenResponse.accessToken());
        if (!profileFromGoogle.verifiedEmail()) {
            return new ProcessGoogleOAuth2CallbackResult.UserNotVerified();
        }

        final var findOAuth2AuthenticationRequest = new LoadOAuth2AuthenticationPort
                .LoadOAuth2AuthenticationRequest(profileFromGoogle.id(), profileFromGoogle.email(), OAuth2Authentication.Provider.GOOGLE.name());
        final var oAuth2Authentication = loadOAuth2AuthenticationPort.load(findOAuth2AuthenticationRequest);
        if (oAuth2Authentication == null) {
            final TemporalOAuth2Data temporalOAuth2Data = new TemporalOAuth2Data(
                    profileFromGoogle.id(),
                    OAuth2Authentication.Provider.GOOGLE,
                    profileFromGoogle.email(),
                    request.requestedAt()
            );
            final String signUpKey = UUID.randomUUID().toString();
            saveTemporarilyOAuth2DataPort.save(signUpKey, temporalOAuth2Data);
            return new ProcessGoogleOAuth2CallbackResult.UserNotSignedUp(signUpKey, profileFromGoogle.email());
        }

        final var accessToken = tokenIssuerDomainService.issueAccessToken(oAuth2Authentication.getWikiUserId(), request.requestedAt());
        final var refreshToken = tokenIssuerDomainService.issueRefreshToken(oAuth2Authentication.getWikiUserId(), request.requestedAt());
        return new ProcessGoogleOAuth2CallbackResult.AlreadySignedUpUser(
                new ProcessGoogleOAuth2CallbackResult.AlreadySignedUpUser.Token(
                        accessToken.tokenName(),
                        accessToken.value(),
                        accessToken.retention().toSeconds(),
                        accessToken.domain(),
                        accessToken.path()
                ),
                new ProcessGoogleOAuth2CallbackResult.AlreadySignedUpUser.Token(
                        refreshToken.tokenName(),
                        refreshToken.value(),
                        refreshToken.retention().toSeconds(),
                        refreshToken.domain(),
                        refreshToken.path()
                )
        );
    }

    private GoogleOAuth2TokenRequest convertToGoogleOAuth2Request(ProcessGoogleOAuth2CallbackRequest request) {
        return new GoogleOAuth2TokenRequest(
                clientId,
                clientSecret,
                request.code(),
                "authorization_code",
                redirectUri
        );
    }
}
