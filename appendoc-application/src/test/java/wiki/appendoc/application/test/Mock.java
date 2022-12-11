package wiki.appendoc.application.test;

import wiki.appendoc.application.wiki.port.outbound.FindWikiDocumentPort;
import wiki.appendoc.application.wiki.port.outbound.SaveWikiDocumentPort;
import wiki.appendoc.domain.wiki.WikiDocument;

public final class Mock {

    public static final class OutboundPort {

        public static SaveWikiDocumentPort createMockSaveWikiDocumentPort(Spy spy) {
            return wikiDocument -> {
                spy.invoke();
            };
        }

        public static FindWikiDocumentPort createMockFindWikiDocumentPort(WikiDocument mockWikiDocument) {
            return documentName -> mockWikiDocument;
        }
    }
}
