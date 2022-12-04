package wiki.appendoc.domain.wiki;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WikiUserIdTest {

    @DisplayName("위키문서 ID를 새롭게 생성한다")
    @Test
    void test1() {
        final WikiUserId actual = WikiUserId.createNewWikiUserId();
        assertNotNull(actual);
        assertNotNull(actual.value());
    }

    @DisplayName("위키문서 ID를 기존에 존재하던 ID에서 생성한다")
    @Test
    void test() {
        final String randomId = "아무아이디";
        final WikiUserId actual = WikiUserId.restoreWikiUserIdFromExistingValue(randomId);
        assertNotNull(actual);
        assertNotNull(actual.value());
        assertEquals(randomId, actual.value());
    }
}