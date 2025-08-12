package kr.co.greengram.application.feedcomment;

import kr.co.greengram.application.feedcomment.model.FeedCommentPostReq;
import kr.co.greengram.entity.Feed;
import kr.co.greengram.entity.FeedComment;
import kr.co.greengram.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedCommentService {
    private final FeedCommentRepository feedCommentRepository;

    public long postFeedComment(long signedUserId, FeedCommentPostReq req) {
        User user = new User();
        user.setUserId(signedUserId);

        Feed feed = Feed.builder()
                .feedId(req.getFeedId())
                .build();

        FeedComment feedComment = FeedComment.builder()
                .user(user)
                .feed(feed)
                .comment(req.getComment())
                .build();

        feedCommentRepository.save(feedComment);

        return feedComment.getFeedCommentId();
    }
}
