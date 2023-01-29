package wiki.appendoc.adapter.authentication.outbound.database;

import lombok.extern.slf4j.Slf4j;
import wiki.appendoc.adapter.PersistenceAdapter;
import wiki.appendoc.application.authentication.port.outbound.LoadOAuth2AuthenticationPort;
import wiki.appendoc.application.authentication.port.outbound.SaveOAuth2AuthenticationPort;
import wiki.appendoc.domain.authentication.oauth2.OAuth2Authentication;

@Slf4j
@PersistenceAdapter
public class OAuth2AuthenticationRepository
        implements LoadOAuth2AuthenticationPort, SaveOAuth2AuthenticationPort {

    private final OAuth2AuthenticationJpaRepository jpaRepository;

    public OAuth2AuthenticationRepository(OAuth2AuthenticationJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public OAuth2Authentication load(LoadOAuth2AuthenticationRequest request) {
        try {
            final var provider = OAuth2AuthenticationEntity
                    .OAuth2Provider
                    .valueOf(request.provider());

            final OAuth2AuthenticationEntity oAuth2AuthenticationEntity = jpaRepository
                    .findByIdFromProviderAndProviderAndEmail(
                            request.idFromProvider(),
                            provider,
                            request.verifiedEmail()
                    );

            if (oAuth2AuthenticationEntity == null) {
                return null;
            }

            return OAuth2Authentication.load(
                    oAuth2AuthenticationEntity.getOAuth2AuthenticationId(),
                    oAuth2AuthenticationEntity.getWikiUserId(),
                    oAuth2AuthenticationEntity.getIdFromProvider(),
                    OAuth2Authentication.Provider.valueOf(oAuth2AuthenticationEntity.getProvider().name()),
                    oAuth2AuthenticationEntity.getEmail()
            );
        } catch (IllegalArgumentException e) {
            throw new WrongProviderException("잘못된 OAuth2 제공자입니다: " + request.provider());
        }
    }

    @Override
    public void save(OAuth2Authentication oAuth2Authentication) {
        final OAuth2AuthenticationEntity entityToSave = new OAuth2AuthenticationEntity(
                oAuth2Authentication.getOAuth2AuthenticationId().value(),
                oAuth2Authentication.getWikiUserId(),
                oAuth2Authentication.getIdFromProvider(),
                OAuth2AuthenticationEntity.OAuth2Provider.valueOf(oAuth2Authentication.getProvider().name()),
                oAuth2Authentication.getVerifiedEmail()
        );
        jpaRepository.save(entityToSave);
    }
}
