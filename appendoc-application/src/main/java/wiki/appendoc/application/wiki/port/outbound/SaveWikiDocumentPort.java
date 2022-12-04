package wiki.appendoc.application.wiki.port.outbound;

import wiki.appendoc.domain.wiki.WikiDocument;

public interface SaveWikiDocumentPort {

    void save(WikiDocument wikiDocument);
}
