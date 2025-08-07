package kr.co.greengram.config.model;

import kr.co.greengram.config.enumcode.model.EnumUserRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class UserPrincipal implements UserDetails {
    private final long memberId;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(long memberId, List<EnumUserRole> roles) {
        this.memberId = memberId;
        //this.authorities = roles.stream().map(role -> new SimpleGrantedAuthority(String.format("ROLE.%s", role.name()))).toList();
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        for (EnumUserRole role : roles) {
            String roleName = String.format("ROLE_%s", role.name());
            list.add(new SimpleGrantedAuthority(roleName));
        }
        this.authorities = list;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }
}
