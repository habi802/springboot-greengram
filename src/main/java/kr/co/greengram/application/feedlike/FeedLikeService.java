package kr.co.greengram.application.feedlike;

import kr.co.greengram.application.feedlike.model.FeedLikeToggleReq;
import kr.co.greengram.entity.Feed;
import kr.co.greengram.entity.FeedLike;
import kr.co.greengram.entity.FeedLikeIds;
import kr.co.greengram.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedLikeService {
    public final FeedLikeRepository feedLikeRepository;

    public boolean toggle(Long signedUserId, FeedLikeToggleReq req) {
        FeedLikeIds feedLikeIds = FeedLikeIds.builder()
                .feedId(req.getFeedId())
                .userId(signedUserId)
                .build();

        FeedLike feedLike = feedLikeRepository.findById(feedLikeIds).orElse(null);
        if (feedLike == null) {
            // 좋아요가 아닌 상태
            Feed feed = Feed.builder()
                    .feedId(req.getFeedId())
                    .build();

            User user = new User();
            user.setUserId(signedUserId);

            FeedLike feedLikeSave = FeedLike.builder()
                    .feedLikeIds(feedLikeIds)
                    .user(user)
                    .feed(feed)
                    .build();

            feedLikeRepository.save(feedLikeSave);
            return true;
        }

        // 좋아요인 상태
        feedLikeRepository.delete(feedLike);
        return false;
    }
}
