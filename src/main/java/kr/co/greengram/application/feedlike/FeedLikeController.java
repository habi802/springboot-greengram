package kr.co.greengram.application.feedlike;

import jakarta.validation.Valid;
import kr.co.greengram.application.feedlike.model.FeedLikeToggleReq;
import kr.co.greengram.config.model.ResultResponse;
import kr.co.greengram.config.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/feed/like")
@RequiredArgsConstructor
public class FeedLikeController {
    public final FeedLikeService feedLikeService;

    @PostMapping
    public ResultResponse<?> toggle(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                    @Valid @RequestBody FeedLikeToggleReq req) {
        log.info("signedUserId: {}", userPrincipal.getSignedUserId());
        log.info("feed like toggle req: {}", req);
        boolean result = feedLikeService.toggle(userPrincipal.getSignedUserId(), req);
        // true: 좋아요 처리, false: 좋아요 취소
        return new ResultResponse<>(result ? "좋아요 처리" : "좋아요 취소", result);
    }
}
