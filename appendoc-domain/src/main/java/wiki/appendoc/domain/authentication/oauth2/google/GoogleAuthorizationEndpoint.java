package wiki.appendoc.domain.authentication.oauth2.google;

import com.google.common.base.Joiner;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 참고 문서: https://developers.google.com/identity/protocols/oauth2/web-server#httprest
 */
public class GoogleAuthorizationEndpoint {

    private static final String GOOGLE_OAUTH2_ENDPOINT = "https://accounts.google.com/o/oauth2/v2/auth";

    private final String clientId;
    private final String redirectUri;
    private final String responseType;
    private final List<String> scope;
    private final String accessType;
    private String state = null;
    private String prompt = null;

    public GoogleAuthorizationEndpoint(String clientId, String redirectUri) {
        Objects.requireNonNull(clientId, "ClientId cannot be null.");
        Objects.requireNonNull(redirectUri, "RedirectUri cannot be null.");
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.responseType = "code";
        this.scope = new ArrayList<>();
        this.scope.add("email");
        this.accessType = "online";
    }

    public GoogleAuthorizationEndpoint setPrompt(String prompt) {
        this.prompt = prompt;
        return this;
    }

    public GoogleAuthorizationEndpoint setState(String state) {
        this.state = state;
        return this;
    }

    private static String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    public String getAuthorizationUrl() {
        final StringBuilder sb = new StringBuilder(GOOGLE_OAUTH2_ENDPOINT);
        sb.append("?");
        sb.append("client_id=").append(clientId);
        sb.append("&redirect_uri=").append(encode(redirectUri));
        sb.append("&response_type=").append(responseType);
        sb.append("&scope=").append(Joiner.on(",").join(this.scope));
        sb.append("&access_type=").append(accessType);
        if (state != null) {
            sb.append("&state=").append(state);
        }
        if (prompt != null) {
            sb.append("&prompt=").append(prompt);
        }
        return sb.toString();
    }
}
