package com.eatrack.security;

import com.eatrack.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

    private String username;

    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Usuario usuario){
        this.username = usuario.getLogin();
        this.password = usuario.getPassword();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        authorities = usuario.getRoles().stream().map(role -> {
            return new SimpleGrantedAuthority("ROLE_" + role.getName());
        }).collect(Collectors.toList());

        this.authorities = authorities;
    }

    public static UserPrincipal create(Usuario usuario){
        return new UserPrincipal(usuario);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
