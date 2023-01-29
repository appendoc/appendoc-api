package wiki.appendoc.adapter.authentication.outbound.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import wiki.appendoc.application.authentication.port.outbound.FindGoogleTokenPort;
import wiki.appendoc.domain.authentication.oauth2.google.GoogleOAuth2TokenRequest;
import wiki.appendoc.domain.authentication.oauth2.google.GoogleOAuth2TokenResponse;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class GoogleOAuth2ApiClient implements FindGoogleTokenPort {

    private static final String GOOGLE_OAUTH2_TOKEN_ENDPOINT = "https://oauth2.googleapis.com/token";

    private final RestTemplate restTemplate;

    public GoogleOAuth2ApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    record TokenResponse(
            @JsonProperty("access_token")
            String accessToken,
            @JsonProperty("expires_in")
            long expiresIn,
            @JsonProperty("scope")
            String scope,
            @JsonProperty("token_type")
            String tokenType,
            @JsonProperty("id_token")

            String idToken
    ) {
    }

    @Override
    public GoogleOAuth2TokenResponse find(GoogleOAuth2TokenRequest request) {
        log.debug("GoogleOAuth2TokenRequest: {}", request);
        final Map<String, Object> params = new HashMap<>();
        params.put("code", request.code());
        params.put("client_id", request.clientId());
        params.put("client_secret", request.clientSecret());
        params.put("redirect_uri", request.redirectUri());
        params.put("grant_type", request.grantType());
        final ResponseEntity<TokenResponse> response = restTemplate
                .postForEntity(
                        GOOGLE_OAUTH2_TOKEN_ENDPOINT,
                        params,
                        TokenResponse.class
                );
        log.debug("map: {}", response);
        final TokenResponse tokenResponse = response.getBody();
        return new GoogleOAuth2TokenResponse(
                tokenResponse.accessToken,
                tokenResponse.expiresIn,
                tokenResponse.scope,
                tokenResponse.tokenType,
                tokenResponse.idToken
        );
    }
}
