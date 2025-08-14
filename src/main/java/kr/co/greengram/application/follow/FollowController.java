package kr.co.greengram.application.follow;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
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

    @DeleteMapping
    public ResultResponse<?> deleteUserFollow(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                              @Valid @RequestParam("to_user_id") @Positive Long toUserId) {
        log.info("delete fromUserId: {}", userPrincipal.getSignedUserId());
        log.info("delete toUserId: {}", toUserId);
        followService.deleteUserFollow(userPrincipal.getSignedUserId(), toUserId);
        return new ResultResponse<>("팔로우 취소", null);
    }
}
