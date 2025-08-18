package kr.co.greengram.application.feedpic;

import kr.co.greengram.entity.Feed;
import kr.co.greengram.entity.FeedPic;
import kr.co.greengram.entity.FeedPicIds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedPicRepository extends JpaRepository<FeedPic, FeedPicIds> {
    void deleteAllByFeed(Feed feed);
}
