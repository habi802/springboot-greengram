package kr.co.greengram.application.feed;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kr.co.greengram.application.feed.model.*;
import kr.co.greengram.config.model.ResultResponse;
import kr.co.greengram.config.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/feed")
@RequiredArgsConstructor
public class FeedController {
    private final FeedService feedService;

    private final int MAX_PIC_COUNT = 10;

    @PostMapping
    public ResultResponse<?> postFeed(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                      @Valid @RequestPart FeedPostReq req,
                                      @RequestPart(name = "pic") List<MultipartFile> pics,
                                      HttpServletRequest request) {
        if (pics.size() > MAX_PIC_COUNT) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("사진은 %d장까지 등록이 가능합니다.", MAX_PIC_COUNT));
        }

        log.info("signedUserId: {}", userPrincipal.getSignedUserId());
        log.info("post feed req: {}", req);
        log.info("pics.size: {}", pics.size());

        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // X-Forwarded-For에 여러 개의 IP가 있을 수 있으니, 첫 번째 IP만 사용
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        req.setIp(ip);

        FeedPostRes result = feedService.postFeed(userPrincipal.getSignedUserId(), req, pics);
        return new ResultResponse<>("피드 등록 성공!", result);
    }

    @GetMapping
    public ResultResponse<?> getFeedList(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                        @Valid @ModelAttribute FeedGetReq req) {
        log.info("signedUserId: {}", userPrincipal.getSignedUserId());
        log.info("get feed req: {}", req);
        FeedGetDto dto = FeedGetDto.builder()
                .signedUserId(userPrincipal.getSignedUserId())
                .startIdx((req.getPage() - 1) * req.getRowPerPage())
                .size(req.getRowPerPage())
                .build();
        List<FeedGetRes> result = feedService.getFeedList(dto);
        return new ResultResponse<>("피드 조회 성공!", result);
    }

    // 페이징, 피드(사진, 댓글(3개만))
    // 현재는 피드+사진만 (N+1)
}
