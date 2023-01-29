package wiki.appendoc.adapter.users.outbound.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class NewUserCreatedByOAuth2ApplicationEvent extends ApplicationEvent {
    private final String signUpKey;
    private final String wikiUserId;

    public NewUserCreatedByOAuth2ApplicationEvent(
            Object source,
            String signUpKey,
            String wikiUserId
    ) {
        super(source);
        this.signUpKey = signUpKey;
        this.wikiUserId = wikiUserId;
    }
}
