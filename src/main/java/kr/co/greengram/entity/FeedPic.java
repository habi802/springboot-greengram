package kr.co.greengram.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EqualsAndHashCode
public class FeedPic extends CreatedAt {
    @EmbeddedId
    private FeedPicIds feedPicIds;
}
