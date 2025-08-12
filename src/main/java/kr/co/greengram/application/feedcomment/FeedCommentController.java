package kr.co.greengram.application.feedcomment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/feed/comment")
@RequiredArgsConstructor
public class FeedCommentController {
    private final FeedCommentService feedCommentService;
}
