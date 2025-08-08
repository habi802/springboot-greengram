package kr.co.greengram.application.feed.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FeedPostReq {
    @Size(max = 1_000, message = "")
    private String contents;

    @Size(max = 30, message = "")
    private String location;
}
