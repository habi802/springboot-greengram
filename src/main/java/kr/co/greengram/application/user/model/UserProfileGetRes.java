package kr.co.greengram.application.user.model;

import lombok.Getter;

@Getter
public class UserProfileGetRes {
    private long userId;
    private String pic;
    private String createdAt;
    private String uid;
    private String nickName;
    private int feedCount; // 프로파일 유저가 등록한 피드 수
    private int allFeedLikeCount; // 나의 모든 피드의 좋아요 수
}
