package kr.co.greengram.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedLike extends CreatedAt {
    @EmbeddedId
    private FeedLikeIds feedLikeIds;

    @ManyToOne
    @MapsId("feedId")
    @JoinColumn(name = "feed_id")
    private Feed feed;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;
}
