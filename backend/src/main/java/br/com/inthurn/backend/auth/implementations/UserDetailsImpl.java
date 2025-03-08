package br.com.inthurn.backend.auth.implementations;

import br.com.inthurn.backend.model.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class UserDetailsImpl implements UserDetails {
    private final User USER;

    public UserDetailsImpl(User user) {
        this.USER = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return USER.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return USER.getEmail();
    }

    @Override
    public String getPassword() {
        return USER.getPassword();
    }
}
