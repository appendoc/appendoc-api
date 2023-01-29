package wiki.appendoc.adapter.authentication.inbound;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import wiki.appendoc.adapter.EventSubscriber;
import wiki.appendoc.adapter.users.outbound.event.NewUserCreatedByOAuth2ApplicationEvent;
import wiki.appendoc.application.authentication.port.inbound.HandleNewAccountCreatedEventUseCase;

@Slf4j
@EventSubscriber
public class NewUserCreatedByOAuth2ApplicationEventSubscriber implements ApplicationListener<NewUserCreatedByOAuth2ApplicationEvent> {

    private final HandleNewAccountCreatedEventUseCase useCase;

    public NewUserCreatedByOAuth2ApplicationEventSubscriber(HandleNewAccountCreatedEventUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void onApplicationEvent(NewUserCreatedByOAuth2ApplicationEvent event) {
        useCase.handle(event.getWikiUserId(), event.getSignUpKey());
    }
}
