package wiki.appendoc.adapter.wiki.inbound;

import org.springframework.web.bind.annotation.RequestBody;

public interface WikiApi {

    record CreateNewWikiDocumentRequest(
            String documentName,
            String content
    ) {
    }

    record CreateNewWikiDocumentResponse(
            String newDocumentId
    ) {
    }

    CreateNewWikiDocumentResponse postNewWikiDocument(@RequestBody CreateNewWikiDocumentRequest request);
}
