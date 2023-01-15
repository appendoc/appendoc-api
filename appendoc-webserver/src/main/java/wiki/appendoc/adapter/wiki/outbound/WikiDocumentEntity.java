package wiki.appendoc.adapter.wiki.outbound;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "wiki_document")
public class WikiDocumentEntity {

    @Id
    @GeneratedValue
    private Long id = null;

    /**
     * 도메인에서 사용하는 domain unique id
     */
    @Column(name = "uid", nullable = false, unique = true)
    private String uid;

    @Column(name = "document_name", nullable = false)
    private String documentName;

    @Column(name = "content", nullable = false, columnDefinition = "text")
    private String content;

    @Column(name = "written_at", nullable = false)
    private LocalDateTime writtenAt;

    @Builder
    private WikiDocumentEntity(String uid, String documentName, String content, LocalDateTime writtenAt) {
        this.uid = uid;
        this.documentName = documentName;
        this.content = content;
        this.writtenAt = writtenAt;
    }
}
