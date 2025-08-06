package kr.co.greengram.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EqualsAndHashCode
public class UserRole {
    @EmbeddedId
    private UserRoleIds userRoleIds;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @MapsId("userId")
    private User user;
}
