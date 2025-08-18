package kr.co.greengram.application.feedcomment;

import kr.co.greengram.entity.Feed;
import kr.co.greengram.entity.FeedComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedCommentRepository extends JpaRepository<FeedComment, Long> {
    void deleteAllByFeed(Feed feed);
}
