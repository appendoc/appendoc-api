package wiki.appendoc.domain.authentication.oauth2.google;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class GoogleAuthorizationEndpointTest {

    @DisplayName("구글 인증 URL 빌드 테스트")
    @Test
    void test() {
        final String clientId = "125569676046-vv5pkr06bdrsm1cm7j4kg3a6b6u182jn.apps.googleusercontent.com";
        final String redirectUri = "http://127.0.0.1:3000/abc";
        final GoogleAuthorizationEndpoint sut = new GoogleAuthorizationEndpoint(clientId, redirectUri);
        sut.setPrompt("select_account").setState(UUID.randomUUID().toString());
        System.out.println(sut.getAuthorizationUrl());
    }

}