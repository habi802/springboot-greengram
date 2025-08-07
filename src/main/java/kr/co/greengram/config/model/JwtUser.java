package kr.co.greengram.config.model;

import kr.co.greengram.config.enumcode.model.EnumUserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class JwtUser {
    private long signedUserId;
    private List<EnumUserRole> roles;
}
