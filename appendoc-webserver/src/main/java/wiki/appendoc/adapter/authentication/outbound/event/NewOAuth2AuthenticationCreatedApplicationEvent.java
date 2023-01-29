package wiki.appendoc.adapter.authentication.outbound.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;

@Getter
public class NewOAuth2AuthenticationCreatedApplicationEvent extends ApplicationEvent {

    private final String newOAuth2AuthenticationId;
    private final LocalDateTime eventOccurredAt;

    public NewOAuth2AuthenticationCreatedApplicationEvent(
            Object source,
            String newOAuth2AuthenticationId,
            LocalDateTime eventOccurredAt
    ) {
        super(source);
        this.newOAuth2AuthenticationId = newOAuth2AuthenticationId;
        this.eventOccurredAt = eventOccurredAt;
    }
}
