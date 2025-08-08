package kr.co.greengram.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EqualsAndHashCode
public class Feed extends UpdatedAt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long feedId;

    @Column(length = 30)
    private String location;

    @Column(length = 1_000)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "write_user_id", nullable = false)
    private User user;
}
