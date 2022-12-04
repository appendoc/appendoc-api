package wiki.appendoc.domain.wiki;

import java.util.UUID;

/**
 * WikiUserId Value Object
 *
 * @param value
 */
public record WikiUserId(String value) {

    static WikiUserId createNewWikiUserId() {
        final String value = UUID.randomUUID().toString();
        return new WikiUserId(value);
    }

    public static WikiUserId restoreWikiUserIdFromExistingValue(String value) {
        return new WikiUserId(value);
    }
}
