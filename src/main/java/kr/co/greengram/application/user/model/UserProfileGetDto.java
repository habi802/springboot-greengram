package kr.co.greengram.application.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class UserProfileGetDto {
    private long signedUserId;
    private long profileUserId;
}
