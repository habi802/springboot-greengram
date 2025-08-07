package kr.co.greengram.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kr.co.greengram.config.enumcode.model.EnumUserRole;
import lombok.*;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@EqualsAndHashCode
public class UserRoleIds implements Serializable {
    private Long userId;
    @Column(length = 2)
    private EnumUserRole roleCode;
}
