package wiki.appendoc.adapter.wiki.outbound;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WikiDocumentJpaRepository extends JpaRepository<WikiDocumentEntity, Long> {

    WikiDocumentEntity findByDocumentName(String documentName);
}
