package kr.co.greengram.application.follow.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class FollowPostReq {
    @Positive
    @NotNull(message = "to_user_id 값은 필수입니다.")
    private Long toUserId;
}
