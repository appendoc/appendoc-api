package wiki.appendoc.domain.wiki;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class WikiDocumentTest {

    @DisplayName("위키문서를 새로 생성한다.")
    @Test
    void testCreateNewWikiDocument() {
        final String documentName = "문서제목";
        final String content = "내용";
        final LocalDateTime writtenAt = LocalDateTime.now();
        final WikiDocument newDocument = WikiDocument.createNewDocument(
                documentName,
                content,
                writtenAt
        );
        assertNotNull(newDocument);
        assertEquals(documentName, newDocument.getDocumentName());
        assertEquals(content, newDocument.getContent());
        assertEquals(writtenAt, newDocument.getWrittenAt());
    }
}
