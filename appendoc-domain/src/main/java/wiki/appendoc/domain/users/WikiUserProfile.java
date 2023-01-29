package wiki.appendoc.domain.users;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WikiUserProfile {
    private final WikiUserProfileId wikiUserProfileId;
    private final WikiUserId wikiUserId;
    private final String displayName;
    private final String bio;

    public static WikiUserProfile createNew(WikiUserId wikiUserId, String displayName) {
        return new WikiUserProfile(
                WikiUserProfileId.createWikiUserProfileId(),
                wikiUserId,
                displayName,
                "자기소개가 없습니다."
        );
    }
}
