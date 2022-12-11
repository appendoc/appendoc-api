package wiki.appendoc.application.wiki.service;

import wiki.appendoc.application.UseCase;
import wiki.appendoc.application.wiki.port.inbound.ReadWikiDocumentByDocumentNameUseCase;
import wiki.appendoc.application.wiki.port.outbound.FindWikiDocumentPort;
import wiki.appendoc.application.wiki.service.exception.WikiDocumentNotFoundException;
import wiki.appendoc.domain.wiki.WikiDocument;

@UseCase
public class ReadWikiDocumentByDocumentNameService implements ReadWikiDocumentByDocumentNameUseCase {

    private final FindWikiDocumentPort findWikiDocumentPort;

    public ReadWikiDocumentByDocumentNameService(FindWikiDocumentPort findWikiDocumentPort) {
        this.findWikiDocumentPort = findWikiDocumentPort;
    }

    @Override
    public ReadWikiDocumentByDocumentNameQueryResult readWikiDocument(ReadWikiDocumentByDocumentNameQuery query) {
        final String documentName = query.documentName();
        final WikiDocument maybeDocument = findWikiDocumentPort.findByDocumentName(documentName);
        if (maybeDocument == null) {
            throw new WikiDocumentNotFoundException("문서를 찾을 수 없습니다. 문서 이름: " + documentName, documentName);
        }

        return new ReadWikiDocumentByDocumentNameQueryResult(
                maybeDocument.getDocumentName(),
                maybeDocument.getContent(),
                maybeDocument.getWrittenAt()
        );
    }
}
