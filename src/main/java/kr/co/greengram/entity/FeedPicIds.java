package kr.co.greengram.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@EqualsAndHashCode
@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedPicIds implements Serializable {
    private long feedId;

    @Column(length = 50)
    private String pic;
}
