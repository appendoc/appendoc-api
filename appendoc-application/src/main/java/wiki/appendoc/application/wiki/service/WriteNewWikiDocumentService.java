package wiki.appendoc.application.wiki.service;

import wiki.appendoc.application.UseCase;
import wiki.appendoc.application.wiki.port.inbound.WriteNewWikiDocumentUseCase;
import wiki.appendoc.application.wiki.port.outbound.FindWikiDocumentPort;
import wiki.appendoc.application.wiki.port.outbound.SaveWikiDocumentPort;
import wiki.appendoc.application.wiki.service.exception.WikiDocumentAlreadyExistException;
import wiki.appendoc.domain.wiki.WikiDocument;

@UseCase
public class WriteNewWikiDocumentService implements WriteNewWikiDocumentUseCase {

    private final SaveWikiDocumentPort saveWikiDocumentPort;
    private final FindWikiDocumentPort findWikiDocumentPort;

    public WriteNewWikiDocumentService(SaveWikiDocumentPort saveWikiDocumentPort, FindWikiDocumentPort findWikiDocumentPort) {
        this.saveWikiDocumentPort = saveWikiDocumentPort;
        this.findWikiDocumentPort = findWikiDocumentPort;
    }

    @Override
    public WriteNewWikiResult writeWikiDocument(WriteNewWikiCommand command) {
        final WikiDocument maybeExistingDocument = findWikiDocumentPort.findByDocumentName(command.documentName());
        if (maybeExistingDocument != null) {
            throw new WikiDocumentAlreadyExistException("문서가 이미 존재합니다.");
        }

        final WikiDocument newDocument = WikiDocument.createNewDocument(
                command.documentName(),
                command.content(),
                command.writtenAt()
        );
        saveWikiDocumentPort.save(newDocument);
        return new WriteNewWikiResult(newDocument.getWikiUserId().value());
    }
}
