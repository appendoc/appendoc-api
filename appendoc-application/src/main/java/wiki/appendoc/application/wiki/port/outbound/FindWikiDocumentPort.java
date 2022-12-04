package wiki.appendoc.application.wiki.port.outbound;

import wiki.appendoc.domain.wiki.WikiDocument;

public interface FindWikiDocumentPort {

    WikiDocument findByDocumentName(String documentName);
}
