package wiki.appendoc.application.authentication.port.inbound;

public interface HandleNewAccountCreatedEventUseCase {

    void handle(String wikiUserId, String signUpKey);
}
