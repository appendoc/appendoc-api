package wiki.appendoc.application.wiki.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wiki.appendoc.application.test.Mock;
import wiki.appendoc.application.test.Spy;
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

@DisplayName("위키 문서 작성 유스케이스 테스트")
class WriteNewWikiDocumentServiceTest {

    @DisplayName("새 위키 문서를 작성한다.")
    @Test
    void testWriteNewWikiDocument() {
        final Spy spy = Spy.getNewSpy();
        final SaveWikiDocumentPort mockSaveWikiDocumentPort = Mock.OutboundPort.createMockSaveWikiDocumentPort(spy);
        final FindWikiDocumentPort mockFindWikiDocumentPort = Mock.OutboundPort.createMockFindWikiDocumentPort(null);
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
        final SaveWikiDocumentPort mockSaveWikiDocumentPort = Mock.OutboundPort.createMockSaveWikiDocumentPort(null);
        final FindWikiDocumentPort mockFindWikiDocumentPort = Mock.OutboundPort.createMockFindWikiDocumentPort(mockWikiDocument);

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
}
