package wiki.appendoc.adapter.users.outbound.database;

import lombok.extern.slf4j.Slf4j;
import wiki.appendoc.adapter.PersistenceAdapter;
import wiki.appendoc.application.users.port.outbound.SaveWikiUserPort;
import wiki.appendoc.domain.users.WikiUser;

import java.time.LocalDateTime;

@Slf4j
@PersistenceAdapter
public class WikiUserRepository implements SaveWikiUserPort {

    private final WikiUserJpaRepository wikiUserJpaRepository;
    private final WikiUserProfileJpaRepository wikiUserProfileJpaRepository;

    public WikiUserRepository(
            WikiUserJpaRepository wikiUserJpaRepository,
            WikiUserProfileJpaRepository wikiUserProfileJpaRepository
    ) {
        this.wikiUserJpaRepository = wikiUserJpaRepository;
        this.wikiUserProfileJpaRepository = wikiUserProfileJpaRepository;
    }

    @Override
    public void save(WikiUser wikiUser, LocalDateTime requestedAt) {
        final var wikiUserEntity = new WikiUserEntity(
                wikiUser.getWikiUserId().value(),
                wikiUser.getCreatedAt()
        );
        wikiUserJpaRepository.save(wikiUserEntity);

        final var userProfile = wikiUser.getUserProfile();
        final var wikiUserProfileEntity = new WikiUserProfileEntity(
                userProfile.getWikiUserProfileId().value(),
                userProfile.getWikiUserId().value(),
                userProfile.getDisplayName(),
                userProfile.getBio(),
                requestedAt
        );
        wikiUserProfileJpaRepository.save(wikiUserProfileEntity);
    }
}
