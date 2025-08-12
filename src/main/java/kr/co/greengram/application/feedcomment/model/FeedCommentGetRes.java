package kr.co.greengram.application.feedcomment.model;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
public class FeedCommentGetRes {
    private boolean moreComment; // 댓글 더 있는지에 대한 정보
    private List<FeedCommentItem> commentList; // 댓글 리스트 정보
}
