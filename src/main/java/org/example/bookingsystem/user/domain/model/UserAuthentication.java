package org.example.bookingsystem.user.domain.model;

import org.example.bookingsystem.customer.domain.entity.Customer;
import org.example.bookingsystem.provider.domain.entity.Provider;
import org.example.bookingsystem.user.domain.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public record UserAuthentication(User user) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user instanceof Provider) {
            return List.of(new SimpleGrantedAuthority("ROLE_PROVIDER"));
        } else if (user instanceof Customer) {
            return List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
        }
        return List.of(new SimpleGrantedAuthority("ROLE_UNKNOWN"));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    public UUID getId() {
        return user.getId();
    }
}
