package kr.co.greengram.application.feedlike;

import kr.co.greengram.entity.Feed;
import kr.co.greengram.entity.FeedLike;
import kr.co.greengram.entity.FeedLikeIds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedLikeRepository extends JpaRepository<FeedLike, FeedLikeIds> {
    void deleteAllByFeed(Feed feed);
}
