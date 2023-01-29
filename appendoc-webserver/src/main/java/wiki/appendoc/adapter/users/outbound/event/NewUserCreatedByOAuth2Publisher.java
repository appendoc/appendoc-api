package wiki.appendoc.adapter.users.outbound.event;

import org.springframework.context.ApplicationEventPublisher;
import wiki.appendoc.adapter.EventPublisher;
import wiki.appendoc.application.users.port.outbound.PublishNewUserCreatedByOAuth2Port;

@EventPublisher
public class NewUserCreatedByOAuth2Publisher implements PublishNewUserCreatedByOAuth2Port {

    private final ApplicationEventPublisher applicationEventPublisher;

    public NewUserCreatedByOAuth2Publisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publish(NewUserCreatedByOAuth2Event event) {
        final var applicationEvent = new NewUserCreatedByOAuth2ApplicationEvent(
                this,
                event.signUpKey(),
                event.wikiUserId()
        );
        applicationEventPublisher.publishEvent(applicationEvent);
    }
}
