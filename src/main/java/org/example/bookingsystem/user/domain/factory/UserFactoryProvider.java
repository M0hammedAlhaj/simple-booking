package org.example.bookingsystem.user.domain.factory;

import org.example.bookingsystem.customer.domain.factory.CustomerFactory;
import org.example.bookingsystem.provider.domain.factory.ProviderFactory;
import org.example.bookingsystem.user.domain.entity.User;
import org.example.bookingsystem.user.domain.model.UserType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;

@Component
public class UserFactoryProvider {

    private final Map<UserType, UserFactory> userFactoryMap;

    public UserFactoryProvider() {
        userFactoryMap = new EnumMap<>(UserType.class);
        userFactoryMap.put(UserType.PROVIDER, new ProviderFactory());
        userFactoryMap.put(UserType.CUSTOMER, new CustomerFactory());
    }

    protected UserFactory getUserFactory(UserType userType) {
        if (userFactoryMap.containsKey(userType)) {
            return userFactoryMap.get(userType);
        }
        throw new UnsupportedOperationException("Unsupported user type");
    }

    public User createUser(String email, String password, UserType userType) {
        var user = getUserFactory(userType).create(email, password);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }
}
