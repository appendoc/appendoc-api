package wiki.appendoc.application.authentication.port.outbound;

import wiki.appendoc.domain.authentication.oauth2.TemporalOAuth2Data;

/**
 * 첫 소셜 가입 시, 가입 화면에서 사용할 데이터 저장소
 */
public interface SaveTemporarilyOAuth2DataPort {

    void save(String key, TemporalOAuth2Data data);
}
