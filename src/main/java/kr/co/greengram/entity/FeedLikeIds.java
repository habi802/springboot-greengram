package kr.co.greengram.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@EqualsAndHashCode
@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedLikeIds implements Serializable {
    private long feedId;
    private long userId;
}
