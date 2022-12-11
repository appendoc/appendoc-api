package wiki.appendoc.application.wiki.port.inbound;

import java.time.LocalDateTime;

public interface ReadWikiDocumentByDocumentNameUseCase {

    ReadWikiDocumentByDocumentNameQueryResult
    readWikiDocument(ReadWikiDocumentByDocumentNameQuery query);

    record ReadWikiDocumentByDocumentNameQuery(
            String documentName
    ) {
    }

    record ReadWikiDocumentByDocumentNameQueryResult(
            String documentName,
            String content,
            LocalDateTime writtenAt
    ) {
    }
}
