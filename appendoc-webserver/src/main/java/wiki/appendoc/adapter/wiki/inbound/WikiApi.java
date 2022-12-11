package wiki.appendoc.adapter.wiki.inbound;

import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

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

    record FindWikiDocumentResponse(
            String documentName,
            String content,
            LocalDateTime writtenAt
    ) {
    }

    FindWikiDocumentResponse getWikiDocument(String documentName);
}
