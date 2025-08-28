package kr.co.greengram.application.feed.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.bind.annotation.BindParam;

@Getter
@ToString
public class FeedGetReq {
    @Positive
    @NotNull(message = "page 값은 필수입니다.")
    private Integer page;

    @NotNull(message = "row_per_page 값은 필수입니다.")
    @Min(value = 20, message = "row_per_page 값은 20 이상")
    @Max(value = 100, message = "row_per_page 값은 100 이하")
    private Integer rowPerPage;

    @Positive
    private Long profileUserId;

    private String keyword;

    public FeedGetReq(Integer page,
                      @BindParam("row_per_page") Integer rowPerPage,
                      @BindParam("profile_user_id") Long profileUserId,
                      String keyword) {
        this.page = page;
        this.rowPerPage = rowPerPage;
        this.profileUserId = profileUserId;
        this.keyword = keyword;
    }
}
