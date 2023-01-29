package wiki.appendoc.adapter.users.outbound.database;

import lombok.AccessLevel;
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
@Table(
        name = "wiki_user"
)
public class WikiUserEntity {

    @Id
    @GeneratedValue
    private Long id = null;

    @Column(name = "wiki_user_id", nullable = false, unique = true, updatable = false)
    private String wikiUserId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public WikiUserEntity(String wikiUserId, LocalDateTime createdAt) {
        this.wikiUserId = wikiUserId;
        this.createdAt = createdAt;
    }
}
