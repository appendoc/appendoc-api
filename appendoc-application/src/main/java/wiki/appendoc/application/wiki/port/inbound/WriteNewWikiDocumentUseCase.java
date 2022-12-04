package wiki.appendoc.application.wiki.port.inbound;

import java.time.LocalDateTime;

public interface WriteNewWikiDocumentUseCase {

    WriteNewWikiResult writeWikiDocument(WriteNewWikiCommand command);

    record WriteNewWikiCommand(
            String documentName,
            String content,
            LocalDateTime writtenAt
    ) {
    }

    record WriteNewWikiResult(
            String newDocumentId
    ) {
    }
}
