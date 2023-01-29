package wiki.appendoc.application.authentication.service;

import wiki.appendoc.application.UseCase;
import wiki.appendoc.application.authentication.port.inbound.HandleNewAccountCreatedEventUseCase;
import wiki.appendoc.application.authentication.port.outbound.EvictTemporarilyOAuth2DataPort;
import wiki.appendoc.application.authentication.port.outbound.LoadTemporarilyOAuth2DataPort;
import wiki.appendoc.application.authentication.port.outbound.SaveOAuth2AuthenticationPort;
import wiki.appendoc.domain.authentication.oauth2.OAuth2Authentication;

@UseCase
public class NewAccountCreatedEventHandler implements HandleNewAccountCreatedEventUseCase {

    private final LoadTemporarilyOAuth2DataPort loadTemporarilyOAuth2DataPort;
    private final SaveOAuth2AuthenticationPort saveOAuth2AuthenticationPort;
    private final EvictTemporarilyOAuth2DataPort evictTemporarilyOAuth2DataPort;

    public NewAccountCreatedEventHandler(
            LoadTemporarilyOAuth2DataPort loadTemporarilyOAuth2DataPort,
            SaveOAuth2AuthenticationPort saveOAuth2AuthenticationPort,
            EvictTemporarilyOAuth2DataPort evictTemporarilyOAuth2DataPort
    ) {
        this.loadTemporarilyOAuth2DataPort = loadTemporarilyOAuth2DataPort;
        this.saveOAuth2AuthenticationPort = saveOAuth2AuthenticationPort;
        this.evictTemporarilyOAuth2DataPort = evictTemporarilyOAuth2DataPort;
    }

    @Override
    public void handle(String wikiUserId, String signUpKey) {
        final var temporalOAuth2Data = loadTemporarilyOAuth2DataPort.load(signUpKey);
        if (temporalOAuth2Data == null) {
            throw new IllegalStateException("임시로 저장된 데이터가 사라졌어요.");
        }

        final OAuth2Authentication newOAuth2Authentication = OAuth2Authentication.createNew(
                wikiUserId,
                temporalOAuth2Data.idFromProvider(),
                temporalOAuth2Data.provider(),
                temporalOAuth2Data.email()
        );
        saveOAuth2AuthenticationPort.save(newOAuth2Authentication);
        evictTemporarilyOAuth2DataPort.evict(signUpKey);
    }
}
