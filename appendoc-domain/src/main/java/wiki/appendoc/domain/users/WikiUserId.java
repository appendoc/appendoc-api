package wiki.appendoc.domain.users;

import java.util.UUID;

/**
 * @param value
 */
public record WikiUserId(String value) {

    static WikiUserId createNewWikiUserId() {
        final String value = UUID.randomUUID().toString();
        return new WikiUserId(value);
    }

    public static WikiUserId loadFromExistingValue(String value) {
        return new WikiUserId(value);
    }
}
