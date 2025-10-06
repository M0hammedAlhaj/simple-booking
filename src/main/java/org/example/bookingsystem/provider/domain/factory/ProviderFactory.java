package org.example.bookingsystem.provider.domain.factory;

import org.example.bookingsystem.provider.domain.entity.Provider;
import org.example.bookingsystem.user.domain.entity.User;
import org.example.bookingsystem.user.domain.factory.UserFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProviderFactory implements UserFactory {

    @Override
    public User create(String email, String password) {
        var provider = new Provider();
        provider.setId(UUID.randomUUID());
        provider.setEmail(email);
        provider.setPassword(password);
        return provider;
    }
}
