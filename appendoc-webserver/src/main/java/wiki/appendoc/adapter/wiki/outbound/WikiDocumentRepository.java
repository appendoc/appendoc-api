package wiki.appendoc.adapter.wiki.outbound;

import lombok.extern.slf4j.Slf4j;
import wiki.appendoc.adapter.PersistenceAdapter;
import wiki.appendoc.application.wiki.port.outbound.FindWikiDocumentPort;
import wiki.appendoc.application.wiki.port.outbound.SaveWikiDocumentPort;
import wiki.appendoc.domain.wiki.WikiDocument;

@Slf4j
@PersistenceAdapter
public class WikiDocumentRepository implements SaveWikiDocumentPort, FindWikiDocumentPort {

    private final WikiDocumentJpaRepository wikiDocumentJpaRepository;

    public WikiDocumentRepository(WikiDocumentJpaRepository wikiDocumentJpaRepository) {
        this.wikiDocumentJpaRepository = wikiDocumentJpaRepository;
    }

    @Override
    public void save(WikiDocument wikiDocument) {
        final WikiDocumentEntity newWikiDocumentEntity = toEntity(wikiDocument);
        final WikiDocumentEntity savedEntity = wikiDocumentJpaRepository.save(newWikiDocumentEntity);
        log.debug("new entity id: {}", savedEntity);
    }

    private WikiDocumentEntity toEntity(WikiDocument wikiDocument) {
        final WikiDocumentEntity entity = WikiDocumentEntity
                .builder()
                .uid(wikiDocument.getWikiUserId().value())
                .documentName(wikiDocument.getDocumentName())
                .content(wikiDocument.getContent())
                .writtenAt(wikiDocument.getWrittenAt())
                .build();

        log.debug("저장될 엔티티: {}", entity);
        return entity;
    }

    private WikiDocument toDomainEntity(WikiDocumentEntity wikiDocumentEntity) {
        return WikiDocument.loadDocument(
                wikiDocumentEntity.getUid(),
                wikiDocumentEntity.getDocumentName(),
                wikiDocumentEntity.getContent(),
                wikiDocumentEntity.getWrittenAt()
        );
    }

    @Override
    public WikiDocument findByDocumentName(String documentName) {
        final WikiDocumentEntity foundDocument = wikiDocumentJpaRepository.findByDocumentName(documentName);
        if (foundDocument != null) {
            return toDomainEntity(foundDocument);
        }
        return null;
    }
}
