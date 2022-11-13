package wiki.appendoc.application.users;

import wiki.appendoc.domain.users.WikiUser;

public class TestUseCase {
    private WikiUser wikiUser;

    public TestUseCase(WikiUser wikiUser) {
        wikiUser.getWikiUserId();
        this.wikiUser = wikiUser;
    }
}
