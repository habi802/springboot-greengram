package kr.co.greengram.config.model;

import kr.co.greengram.config.enumcode.model.EnumUserRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
public class UserPrincipal implements UserDetails, OAuth2User {
    private final JwtUser jwtUser;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(JwtUser jwtUser) {
        this.jwtUser = jwtUser;
        //this.authorities = roles.stream().map(role -> new SimpleGrantedAuthority(String.format("ROLE.%s", role.name()))).toList();
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        for (EnumUserRole role : jwtUser.getRoles()) {
            String roleName = String.format("ROLE_%s", role.name());
            list.add(new SimpleGrantedAuthority(roleName));
        }
        this.authorities = list;
    }

    public Long getSignedUserId() {
        return jwtUser.getSignedUserId();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
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
        return String.valueOf(jwtUser.getSignedUserId());
    }

    @Override
    public String getName() {
        return "oauth2";
    }
}
