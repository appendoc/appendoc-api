package wiki.appendoc.application.wiki.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wiki.appendoc.application.wiki.port.inbound.WriteNewWikiDocumentUseCase;
import wiki.appendoc.application.wiki.port.outbound.FindWikiDocumentPort;
import wiki.appendoc.application.wiki.port.outbound.SaveWikiDocumentPort;
import wiki.appendoc.application.wiki.service.exception.WikiDocumentAlreadyExistException;
import wiki.appendoc.domain.wiki.WikiDocument;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WriteNewWikiDocumentServiceTest {

    @DisplayName("새 위키 문서를 작성한다.")
    @Test
    void testWriteNewWikiDocument() {
        final Spy spy = Spy.getNewSpy();
        final SaveWikiDocumentPort mockSaveWikiDocumentPort = createMockSaveWikiDocumentPort(spy);
        final FindWikiDocumentPort mockFindWikiDocumentPort = createMockFindWikiDocumentPort(null);
        var sut = new WriteNewWikiDocumentService(mockSaveWikiDocumentPort, mockFindWikiDocumentPort);

        final WriteNewWikiDocumentUseCase.WriteNewWikiResult result = sut.writeWikiDocument(
                new WriteNewWikiDocumentUseCase.WriteNewWikiCommand(
                        "제목", "문서", LocalDateTime.now()
                )
        );

        assertNotNull(result.newDocumentId());
        assertTrue(spy.isCalled());
    }

    @DisplayName("이미 존재하는 문서가 있다면 WikiDocumentAlreadyExistException 예외가 발생한다.")
    @Test
    void exceptionWilLBeThrownWhenSameDocumentAlreadyExists() {
        final WikiDocument mockWikiDocument = WikiDocument.loadDocument(
                UUID.randomUUID().toString(),
                "제목",
                "내용",
                LocalDateTime.now()
        );
        final SaveWikiDocumentPort mockSaveWikiDocumentPort = createMockSaveWikiDocumentPort(null);
        final FindWikiDocumentPort mockFindWikiDocumentPort = createMockFindWikiDocumentPort(mockWikiDocument);

        var sut = new WriteNewWikiDocumentService(mockSaveWikiDocumentPort, mockFindWikiDocumentPort);

        assertThrows(
                WikiDocumentAlreadyExistException.class,
                () -> {
                    sut.writeWikiDocument(
                            new WriteNewWikiDocumentUseCase.WriteNewWikiCommand(
                                    "제목", "문서", LocalDateTime.now()
                            )
                    );
                }
        );
    }

    private SaveWikiDocumentPort createMockSaveWikiDocumentPort(Spy spy) {
        return wikiDocument -> {
            spy.invoke();
        };
    }

    private FindWikiDocumentPort createMockFindWikiDocumentPort(WikiDocument mockWikiDocument) {
        return documentName -> mockWikiDocument;
    }
}
