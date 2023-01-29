package wiki.appendoc.adapter.users.outbound.database;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WikiUserProfileJpaRepository extends JpaRepository<WikiUserProfileEntity, Long> {
}
