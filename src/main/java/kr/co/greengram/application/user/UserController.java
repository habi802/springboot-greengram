package kr.co.greengram.application.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import kr.co.greengram.application.user.model.UserSignInDto;
import kr.co.greengram.application.user.model.UserSignUpReq;
import kr.co.greengram.application.user.model.UserSignInReq;
import kr.co.greengram.config.jwt.JwtTokenManager;
import kr.co.greengram.config.model.ResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
                                    @RequestPart(required = false) MultipartFile pic) {
        log.info("req: {}", req);
        log.info("pic: {}", pic);
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
}
