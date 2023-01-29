package wiki.appendoc.application.users.port.outbound;

public interface PublishNewUserCreatedByOAuth2Port {

    record NewUserCreatedByOAuth2Event(
            String signUpKey,
            String wikiUserId
    ) {
    }

    void publish(NewUserCreatedByOAuth2Event event);
}
