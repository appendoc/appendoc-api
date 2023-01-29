package wiki.appendoc.adapter.authentication.outbound.database;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "oauth2_authentication",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "UQ_OAUTH2_AUTH_COMP_ID",
                        columnNames = {
                                "wiki_user_id",
                                "id_from_provider",
                                "oauth2_provider",
                                "email"
                        }
                )
        }
)
public class OAuth2AuthenticationEntity {

    @Id
    @GeneratedValue
    private Long id = null;

    @Column(name = "oauth2_auth_id", nullable = false)
    private String oAuth2AuthenticationId;

    @Column(name = "wiki_user_id", nullable = false)
    private String wikiUserId;

    @Column(name = "id_from_provider", nullable = false)
    private String idFromProvider;

    @Column(name = "oauth2_provider", nullable = false)
    @Enumerated(EnumType.STRING)
    private OAuth2Provider provider;

    @Column(name = "email", nullable = false)
    private String email;

    enum OAuth2Provider {
        GOOGLE
    }

    public OAuth2AuthenticationEntity(
            String oAuth2AuthenticationIdValue,
            String wikiUserIdValue,
            String idFromProvider,
            OAuth2Provider provider,
            String email
    ) {
        this.oAuth2AuthenticationId = oAuth2AuthenticationIdValue;
        this.wikiUserId = wikiUserIdValue;
        this.idFromProvider = idFromProvider;
        this.provider = provider;
        this.email = email;
    }
}
