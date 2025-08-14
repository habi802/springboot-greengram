package kr.co.greengram.application.feed.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedGetDto {
    private long signedUserId;
    private Long profileUserId;
    private int startIdx;
    private int size;
}
