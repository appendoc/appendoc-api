package wiki.appendoc.domain.users;

import java.util.UUID;

public record WikiUserProfileId(String value) {

    static WikiUserProfileId createWikiUserProfileId() {
        final String value = UUID.randomUUID().toString();
        return new WikiUserProfileId(value);
    }

    public static WikiUserProfileId loadFromExistingValue(String value) {
        return new WikiUserProfileId(value);
    }
}
