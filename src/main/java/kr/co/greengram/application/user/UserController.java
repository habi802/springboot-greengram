package kr.co.greengram.application.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kr.co.greengram.application.user.model.*;
import kr.co.greengram.config.jwt.JwtTokenManager;
import kr.co.greengram.config.model.ResultResponse;
import kr.co.greengram.config.model.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtTokenManager jwtTokenManager;

    @PostMapping("/sign-up")
    public ResultResponse<?> signUp(@Valid @RequestPart UserSignUpReq req,
                                    @RequestPart(required = false) MultipartFile pic,
                                    HttpServletRequest request) {
        log.info("req: {}", req);
        log.info("pic: {}", pic);

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

        userService.signUp(req, pic);
        return new ResultResponse<Integer>("회원 가입 성공!", 1);
    }

    @PostMapping("/sign-in")
    public ResultResponse<?> signIn(@Valid @RequestBody UserSignInReq req, HttpServletResponse response) {
        log.info("req: {}", req);
        UserSignInDto userSignInDto = userService.signIn(req);
        jwtTokenManager.issue(response, userSignInDto.getJwtUser());

        return new ResultResponse<>("로그인 성공!", userSignInDto.getUserSignInRes());
    }

    @PostMapping("/sign-out")
    public ResultResponse<?> signOut(HttpServletResponse response) {
        jwtTokenManager.signOut(response);
        return new ResultResponse<>("로그아웃 성공!", null);
    }

    @PostMapping("/reissue")
    public ResultResponse<?> reissue(HttpServletResponse response, HttpServletRequest request) {
        jwtTokenManager.reissue(request, response);
        return new ResultResponse<>("AccessToken 재발행 성공!", null);
    }

    @GetMapping("/profile")
    public ResultResponse<?> getProfileUser(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                     @RequestParam("profile_user_id") long profileUserId) {
        log.info("signedUserId: {}", userPrincipal.getSignedUserId());
        log.info("get profile user id: {}", profileUserId);
        UserProfileGetDto dto = new UserProfileGetDto(userPrincipal.getSignedUserId(), profileUserId);
        log.info("user profile get dto: {}", dto);
        UserProfileGetRes userProfileGetRes = userService.getProfileUser(dto);
        return new ResultResponse<>("프로파일 유저 정보", userProfileGetRes);
    }

    @PatchMapping("/profile/pic")
    public ResultResponse<?> patchProfilePic(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                             @RequestPart MultipartFile pic) {
        String savedFileName = userService.patchProfilePic(userPrincipal.getSignedUserId(), pic);
        return new ResultResponse<>("프로파일 사진 수정 완료!", savedFileName);
    }

    @DeleteMapping("/profile/pic")
    public ResultResponse<?> deleteProfilePic(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        log.info("signedUserId: {}", userPrincipal.getSignedUserId());
        userService.deleteProfilePic(userPrincipal.getSignedUserId());
        return new ResultResponse<>("프로파일 사진 삭제 완료", null);
    }
}
