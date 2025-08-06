package kr.co.greengram.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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
}
