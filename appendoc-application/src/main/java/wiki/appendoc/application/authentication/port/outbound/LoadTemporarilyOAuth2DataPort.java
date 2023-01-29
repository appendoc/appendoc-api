package wiki.appendoc.application.authentication.port.outbound;

import wiki.appendoc.domain.authentication.oauth2.TemporalOAuth2Data;

public interface LoadTemporarilyOAuth2DataPort {

    TemporalOAuth2Data load(String key);
}
