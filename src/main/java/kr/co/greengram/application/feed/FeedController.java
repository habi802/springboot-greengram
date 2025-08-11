package kr.co.greengram.application.feed;

import jakarta.validation.Valid;
import kr.co.greengram.application.feed.model.FeedPostReq;
import kr.co.greengram.config.model.ResultResponse;
import kr.co.greengram.config.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
public class FeedController {
    private final FeedService feedService;

    @PostMapping
    public ResultResponse<?> postFeed(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                      @Valid @RequestPart FeedPostReq req,
                                      @RequestPart(name = "pic") List<MultipartFile> pics) {
        log.info("signedUserId: {}", userPrincipal.getSignedUserId());
        log.info("req: {}", req);
        log.info("pics.size: {}", pics.size());
        feedService.postFeed(userPrincipal.getSignedUserId(), req, pics);
        return new ResultResponse<>("피드 등록 성공!", null);
    }

    // 페이징, 피드(사진, 댓글(3개만))
    // 현재는 피드+사진만 (N+1)
}
