package wiki.appendoc.adapter.authentication.outbound.database;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OAuth2AuthenticationJpaRepository extends JpaRepository<OAuth2AuthenticationEntity, Long> {

    OAuth2AuthenticationEntity findByIdFromProviderAndProviderAndEmail(
            String idFromProvider,
            OAuth2AuthenticationEntity.OAuth2Provider provider,
            String email
    );
}
