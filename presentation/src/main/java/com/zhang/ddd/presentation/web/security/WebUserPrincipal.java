package com.zhang.ddd.presentation.web.security;

import com.zhang.ddd.domain.aggregate.user.entity.User;
import com.zhang.ddd.presentation.facade.dto.user.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class WebUserPrincipal implements UserDetails {

    private User user;

    private final Set<GrantedAuthority> authorities = new HashSet<>();

    public WebUserPrincipal(User user) {
        this.user = user;

        // TODO: add role system in application
        authorities.add(new SimpleGrantedAuthority("USER"));
    }

    public User getUser() {
        return this.user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
