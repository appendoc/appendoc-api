package wiki.appendoc.application.users.port.outbound;

import wiki.appendoc.domain.users.WikiUser;

import java.time.LocalDateTime;

public interface SaveWikiUserPort {

    void save(WikiUser wikiUser, LocalDateTime localDateTime);
}
