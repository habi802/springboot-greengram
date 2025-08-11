package kr.co.greengram.application.feed;

import kr.co.greengram.application.feed.model.FeedGetDto;
import kr.co.greengram.application.feed.model.FeedGetRes;
import kr.co.greengram.application.feed.model.FeedPostReq;
import kr.co.greengram.application.feed.model.FeedPostRes;
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
        return feedMapper.findAllLimitedTo(dto);
    }
}
