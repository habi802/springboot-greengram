package kr.co.greengram.application.feedcomment;

import kr.co.greengram.application.feedcomment.model.FeedCommentGetReq;
import kr.co.greengram.application.feedcomment.model.FeedCommentGetRes;
import kr.co.greengram.application.feedcomment.model.FeedCommentItem;
import kr.co.greengram.application.feedcomment.model.FeedCommentPostReq;
import kr.co.greengram.entity.Feed;
import kr.co.greengram.entity.FeedComment;
import kr.co.greengram.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedCommentService {
    private final FeedCommentRepository feedCommentRepository;
    private final FeedCommentMapper feedCommentMapper;

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

    public FeedCommentGetRes getFeedList(FeedCommentGetReq req) {
        List<FeedCommentItem> commentList = feedCommentMapper.findAllByFeedIdLimitedTo(req);

        boolean moreComment = commentList.size() > req.getSize();
        if (moreComment) {
            commentList.remove(commentList.size() - 1);
        }

        return new FeedCommentGetRes(moreComment, commentList);
    }

    public void deleteFeedComment(long signedUserId, long feedCommentId) {
        FeedComment feedComment = feedCommentRepository.findById(feedCommentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "feed_comment_id를 확인해 주세요."));

        if (feedComment.getUser().getUserId() != signedUserId) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "본인이 작성한 댓글만 삭제할 수 있습니다.");
        }

        feedCommentRepository.delete(feedComment);
    }
}
