package kr.co.greengram.application.follow;

import jakarta.validation.Valid;
import kr.co.greengram.application.follow.model.FollowPostReq;
import kr.co.greengram.config.model.ResultResponse;
import kr.co.greengram.config.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user/follow")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    @PostMapping
    public ResultResponse<?> postUserFollow(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                            @Valid @RequestBody FollowPostReq req) {
        log.info("follow fromUserId: {}", userPrincipal.getSignedUserId());
        log.info("follow toUserId: {}", req.getToUserId());
        followService.postUserFollow(userPrincipal.getSignedUserId(), req.getToUserId());
        return new ResultResponse<>("팔로우 성공!", null);
    }
}
