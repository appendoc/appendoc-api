package wiki.appendoc.application.users.service;

import wiki.appendoc.application.UseCase;
import wiki.appendoc.application.users.port.inbound.CreateNewUserAccountWithOAuth2UseCase;
import wiki.appendoc.application.users.port.outbound.CheckTempAuthDataExistencePort;
import wiki.appendoc.application.users.port.outbound.PublishNewUserCreatedByOAuth2Port;
import wiki.appendoc.application.users.port.outbound.SaveWikiUserPort;
import wiki.appendoc.domain.authentication.TokenIssuerDomainService;
import wiki.appendoc.domain.users.WikiUser;

@UseCase
public class NewUserAccountWithOAuth2Service implements CreateNewUserAccountWithOAuth2UseCase {
    private final SaveWikiUserPort saveWikiUserPort;
    private final PublishNewUserCreatedByOAuth2Port publishNewUserCreatedByOAuth2Port;
    private final CheckTempAuthDataExistencePort checkTempAuthDataExistencePort;

    public NewUserAccountWithOAuth2Service(
            SaveWikiUserPort saveWikiUserPort,
            PublishNewUserCreatedByOAuth2Port publishNewUserCreatedByOAuth2Port,
            CheckTempAuthDataExistencePort checkTempAuthDataExistencePort
    ) {
        this.saveWikiUserPort = saveWikiUserPort;
        this.publishNewUserCreatedByOAuth2Port = publishNewUserCreatedByOAuth2Port;
        this.checkTempAuthDataExistencePort = checkTempAuthDataExistencePort;
    }

    @Override
    public CreateNewUserAccountWithOAuth2Response signUp(CreateNewUserAccountWithOAuth2Request request) {
        final boolean tempAuthDataExists = checkTempAuthDataExistencePort.exists(request.signUpKey());
        if (!tempAuthDataExists) {
            throw new IllegalStateException("인증 데이터가 없습니다. 가입을 진행할 수 없어요.");
        }
        final WikiUser newWikiUser = WikiUser.createNew(request.displayName());
        saveWikiUserPort.save(newWikiUser, request.requestAt());
        publishNewUserCreatedByOAuth2Port.publish(
                new PublishNewUserCreatedByOAuth2Port.NewUserCreatedByOAuth2Event(
                        request.signUpKey(),
                        newWikiUser.getWikiUserId().value()
                )
        );
        return new CreateNewUserAccountWithOAuth2Response();
    }
}
