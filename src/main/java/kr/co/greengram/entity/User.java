package kr.co.greengram.entity;

import jakarta.persistence.*;
import kr.co.greengram.config.enumcode.model.EnumUserRole;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@EqualsAndHashCode
@Table(
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = { "uid" }
        )
    }
)
public class User extends UpdatedAt {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
    private long userId;

    @Column(length = 30)
    private String nickName;

    @Column(nullable = false, length = 50)
    private String uid;

    @Column(length = 100)
    private String pic;

    @Column(nullable = false, length = 100)
    private String upw;

    // cascade는 자식과 나랑 모든 연결(내가 영속성되면 자식도 영속성되고, 내가 삭제되면 자식도 삭제된다 등등..)
    // orphanRemoval은 userRoles 에서 자식을 하나 제거하면 DB에서 뺀 자식은 삭제 처리된다.
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRole> userRoles = new ArrayList<>(1);

    public void addUserRoles(List<EnumUserRole> enumUserRoles) {
        for (EnumUserRole e : enumUserRoles) {
            UserRoleIds ids = new UserRoleIds(this.userId, e);
            UserRole userRole = new UserRole(ids, this);

            this.userRoles.add(userRole);
        }
    }
}
