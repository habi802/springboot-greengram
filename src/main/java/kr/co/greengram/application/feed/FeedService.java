package kr.co.greengram.application.feed;

import kr.co.greengram.application.feed.model.FeedGetDto;
import kr.co.greengram.application.feed.model.FeedGetRes;
import kr.co.greengram.application.feed.model.FeedPostReq;
import kr.co.greengram.application.feed.model.FeedPostRes;
import kr.co.greengram.application.feedcomment.FeedCommentMapper;
import kr.co.greengram.application.feedcomment.model.FeedCommentGetReq;
import kr.co.greengram.application.feedcomment.model.FeedCommentGetRes;
import kr.co.greengram.application.feedcomment.model.FeedCommentItem;
import kr.co.greengram.config.constants.ConstComment;
import kr.co.greengram.config.util.ImgUploadManager;
import kr.co.greengram.entity.Feed;
import kr.co.greengram.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedRepository feedRepository;
    private final FeedMapper feedMapper;
    private final ImgUploadManager imgUploadManager;
    private final FeedCommentMapper feedCommentMapper;
    private final ConstComment constComment;

    @Transactional
    public FeedPostRes postFeed(long signedUserId, FeedPostReq req, List<MultipartFile> pics) {
        User writerUser = new User();
        writerUser.setUserId(signedUserId);

        Feed feed = Feed.builder()
                .writerUser(writerUser)
                .location(req.getLocation())
                .contents(req.getContents())
                .build();

        feedRepository.save(feed); // feed 객체는 영속성을 갖는다.

        List<String> fileNames = imgUploadManager.saveFeedPics(feed.getFeedId(), pics);

        feed.addFeedPics(fileNames);

        return new FeedPostRes(feed.getFeedId(), fileNames);
    }

    public List<FeedGetRes> getFeedList(FeedGetDto dto) {
        List<FeedGetRes> list = feedMapper.findAllLimitedTo(dto);

        // 각 피드에서 사진 가져오기, 댓글 가져오기(4개만)
        for (FeedGetRes feedGetRes : list) {
            feedGetRes.setPics(feedMapper.findAllPicByFeedId(feedGetRes.getFeedId()));

            FeedCommentGetReq feedCommentGetReq = new FeedCommentGetReq(feedGetRes.getFeedId(), constComment.startIndex, constComment.needForViewCount + 1);
            List<FeedCommentItem> commentList = feedCommentMapper.findAllByFeedIdLimitedTo(feedCommentGetReq);

            boolean moreComment = commentList.size() > constComment.needForViewCount;
            FeedCommentGetRes feedCommentGetRes = new FeedCommentGetRes(moreComment, commentList);
            if (moreComment) {
                commentList.remove(commentList.size() - 1);
            }
            feedGetRes.setComments(feedCommentGetRes);
        }
        return list;
    }
}
