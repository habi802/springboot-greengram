package kr.co.greengram.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EqualsAndHashCode
public class UserFollow extends CreatedAt {
    @EmbeddedId
    private UserFollowIds userFollowIds;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    @JoinColumn(name="to_user_id")
    @MapsId("toUserId")
    private User user;
}
