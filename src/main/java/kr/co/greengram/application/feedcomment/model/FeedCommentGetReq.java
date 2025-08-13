package kr.co.greengram.application.feedcomment.model;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.ToString;

import java.beans.ConstructorProperties;

@Getter
@ToString
public class FeedCommentGetReq {
    @Positive
    @NotNull(message = "feed_id 값은 필수입니다.")
    private Long feedId;

    @PositiveOrZero
    @NotNull(message = "start_idx 값은 필수입니다.")
    private Integer startIdx;

    @Min(3)
    @Max(50)
    @NotNull(message = "size 값은 필수입니다.")
    private Integer size;

    @ConstructorProperties({"feed_id", "start_idx", "size"})
    public FeedCommentGetReq(Long feedId, Integer startIdx, Integer size) {
        this.feedId = feedId;
        this.startIdx = startIdx;
        this.size = size;
    }

    public Integer getSizePlusOne() {
        return size + 1;
    }
}
