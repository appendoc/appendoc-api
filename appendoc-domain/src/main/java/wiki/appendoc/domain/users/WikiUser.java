package wiki.appendoc.domain.users;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class WikiUser {

    @Getter
    private final WikiUserId wikiUserId;
    @Getter
    private final LocalDateTime createdAt;
    @Getter
    private final WikiUserProfile userProfile;

    public static WikiUser createNew(String displayName) {
        final WikiUserId newWikiUserId = WikiUserId.createNewWikiUserId();
        return new WikiUser(
                newWikiUserId,
                LocalDateTime.now(),
                WikiUserProfile.createNew(newWikiUserId, displayName)
        );
    }
}
