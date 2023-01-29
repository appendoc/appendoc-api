package wiki.appendoc.adapter.authentication.outbound.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import wiki.appendoc.application.authentication.port.outbound.SearchGoogleProfilePort;
import wiki.appendoc.domain.authentication.oauth2.google.ProfileFromGoogle;

@Slf4j
@Component
public class SearchGoogleProfileApiClient implements SearchGoogleProfilePort {

    private static final String GOOGLE_PROFILE_ENDPOINT = "https://www.googleapis.com/oauth2/v1/userinfo";

    private final RestTemplate restTemplate;

    public SearchGoogleProfileApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    record ProfileResponse(
            @JsonProperty("id")
            String id,
            @JsonProperty("email")
            String email,
            @JsonProperty("verified_email")
            boolean verifiedEmail,
            @JsonProperty("picture")
            String picture
    ) {
    }


    @Override
    public ProfileFromGoogle search(String token) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token);
        final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(httpHeaders);
        final ResponseEntity<ProfileResponse> response = restTemplate
                .exchange(
                        GOOGLE_PROFILE_ENDPOINT,
                        HttpMethod.GET,
                        request,
                        ProfileResponse.class
                );
        log.debug("response: {}", response);
        final ProfileResponse profileResponse = response.getBody();
        return new ProfileFromGoogle(
                profileResponse.id,
                profileResponse.email,
                profileResponse.verifiedEmail,
                profileResponse.picture
        );
    }
}
