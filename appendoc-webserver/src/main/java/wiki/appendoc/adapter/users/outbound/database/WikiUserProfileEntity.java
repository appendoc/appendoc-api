package wiki.appendoc.adapter.users.outbound.database;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "wiki_user_profile"
)
public class WikiUserProfileEntity {

    @Id
    @GeneratedValue
    private Long id = null;

    @Column(name = "wiki_user_profile_id", nullable = false, unique = true, updatable = false)
    private String wikiUserProfileId;

    @Column(name = "wiki_user_id", nullable = false, unique = true, updatable = false)
    private String wikiUserId;

    @Column(name = "display_name", nullable = false, unique = true)
    private String displayName;

    @Column(name = "bio")
    private String bio = null;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public WikiUserProfileEntity(
            String wikiUserProfileId,
            String wikiUserId,
            String displayName,
            String bio,
            LocalDateTime createdAt
    ) {
        this.wikiUserProfileId = wikiUserProfileId;
        this.wikiUserId = wikiUserId;
        this.displayName = displayName;
        this.bio = bio;
        this.createdAt = createdAt;
    }
}
