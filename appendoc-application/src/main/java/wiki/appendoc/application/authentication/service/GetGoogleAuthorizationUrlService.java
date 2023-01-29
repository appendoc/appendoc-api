package wiki.appendoc.application.authentication.service;

import org.springframework.beans.factory.annotation.Value;
import wiki.appendoc.application.UseCase;
import wiki.appendoc.application.authentication.port.inbound.GetGoogleAuthorizationUrlUseCase;
import wiki.appendoc.domain.authentication.oauth2.google.GoogleAuthorizationEndpoint;

@UseCase
public class GetGoogleAuthorizationUrlService implements GetGoogleAuthorizationUrlUseCase {

    @Value("${application.config.oauth2.google.clientId:clientId}")
    private String clientId;
    @Value("${application.config.oauth2.google.redirectUri:redirectUri}")
    private String redirectUri;

    @Override
    public String getGoogleAuthorizationUrl() {
        final GoogleAuthorizationEndpoint endpoint = new GoogleAuthorizationEndpoint(clientId, redirectUri);
        endpoint.setPrompt("select_account");
        return endpoint.getAuthorizationUrl();
    }
}
