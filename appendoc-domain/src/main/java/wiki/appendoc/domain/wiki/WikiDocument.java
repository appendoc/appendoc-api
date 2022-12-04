package wiki.appendoc.domain.wiki;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WikiDocument {

    private final WikiUserId wikiUserId;
    private final String documentName;
    private final String content;
    private final LocalDateTime writtenAt;

    public static WikiDocument createNewDocument(
            String name, String content, LocalDateTime writtenAt
    ) {
        final WikiUserId newWikiUserId = WikiUserId.createNewWikiUserId();
        return new WikiDocument(
                newWikiUserId,
                name,
                content,
                writtenAt
        );
    }

    public static WikiDocument loadDocument(
            String uid,
            String documentName,
            String content,
            LocalDateTime writtenAt
    ) {
        return new WikiDocument(
                WikiUserId.restoreWikiUserIdFromExistingValue(uid),
                documentName,
                content,
                writtenAt
        );
    }
}
