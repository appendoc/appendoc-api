package wiki.appendoc.application.wiki.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wiki.appendoc.application.test.Mock;
import wiki.appendoc.application.wiki.port.inbound.ReadWikiDocumentByDocumentNameUseCase;
import wiki.appendoc.application.wiki.port.outbound.FindWikiDocumentPort;
import wiki.appendoc.application.wiki.service.exception.WikiDocumentNotFoundException;
import wiki.appendoc.domain.wiki.WikiDocument;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("위키 문서 조회 유스케이스 테스트")
class ReadWikiDocumentByDocumentNameServiceTest {

    @DisplayName("문서를 조회한다.")
    @Test
    void test() {
        final String expectedTitle = "제목";
        final String expectedContent = "내용";
        final LocalDateTime expectedWrittenAt = LocalDateTime.now();
        final WikiDocument mockWikiDocument = WikiDocument.loadDocument(
                UUID.randomUUID().toString(),
                expectedTitle,
                expectedContent,
                expectedWrittenAt
        );
        final FindWikiDocumentPort mockFindWikiDocumentPort = Mock.OutboundPort.createMockFindWikiDocumentPort(mockWikiDocument);
        final var sut = new ReadWikiDocumentByDocumentNameService(mockFindWikiDocumentPort);
        final var result = sut.readWikiDocument(
                new ReadWikiDocumentByDocumentNameUseCase.ReadWikiDocumentByDocumentNameQuery(expectedTitle)
        );

        assertNotNull(result);
        assertEquals(expectedTitle, result.documentName());
        assertEquals(expectedContent, result.content());
        assertEquals(expectedWrittenAt, result.writtenAt());
    }

    @DisplayName("찾는 문서가 없다면 WikiDocumentNotFoundException 예외가 발생한다.")
    @Test
    void exceptionWillBeThrownWhenDocumentDoesNotExist() {
        final var mockFindWikiDocumentPort = Mock.OutboundPort.createMockFindWikiDocumentPort(null);
        final var sut = new ReadWikiDocumentByDocumentNameService(mockFindWikiDocumentPort);

        assertThrows(
                WikiDocumentNotFoundException.class,
                () -> {
                    sut.readWikiDocument(
                            new ReadWikiDocumentByDocumentNameUseCase.ReadWikiDocumentByDocumentNameQuery(
                                    "whatever"
                            )
                    );
                }
        );
    }
}
