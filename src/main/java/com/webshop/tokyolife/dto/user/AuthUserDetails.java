package com.webshop.tokyolife.dto.user;

import com.webshop.tokyolife.model.RolesEntity;
import com.webshop.tokyolife.model.UsersEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class AuthUserDetails implements UserDetails {
    private UsersEntity usersEntity;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<RolesEntity> roles = usersEntity.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role ->{authorities.add(new SimpleGrantedAuthority(role.getName()));});
        return null;
    }
    public AuthUserDetails(UsersEntity usersEntity){
        this.usersEntity = usersEntity;
    }

    @Override
    public String getPassword() {
        return usersEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return usersEntity.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return usersEntity.getLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return usersEntity.getEnable();
    }
}
