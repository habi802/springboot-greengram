package kr.co.greengram.application.feedcomment;

import jakarta.validation.Valid;
import kr.co.greengram.application.feedcomment.model.FeedCommentGetReq;
import kr.co.greengram.application.feedcomment.model.FeedCommentPostReq;
import kr.co.greengram.config.model.ResultResponse;
import kr.co.greengram.config.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/feed/comment")
@RequiredArgsConstructor
public class FeedCommentController {
    private final FeedCommentService feedCommentService;

    @PostMapping
    public ResultResponse<?> postFeedComment(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                             @Valid @RequestBody FeedCommentPostReq req) {
        log.info("signedUserId: {}", userPrincipal.getSignedUserId());
        log.info("post feed comment req: {}", req);
        long feedCommentId = feedCommentService.postFeedComment(userPrincipal.getSignedUserId(), req);
        return new ResultResponse<>("댓글 등록 성공!", feedCommentId);
    }

    @GetMapping
    public ResultResponse<?> getFeedCommentList(@Valid @ModelAttribute FeedCommentGetReq req) {
        log.info("get feed comment req: {}", req);
        return null;
    }
}
