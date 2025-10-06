package org.example.bookingsystem.customer.domain.factory;

import org.example.bookingsystem.customer.domain.entity.Customer;
import org.example.bookingsystem.user.domain.entity.User;
import org.example.bookingsystem.user.domain.factory.UserFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomerFactory implements UserFactory {

    @Override
    public User create(String email, String password) {
        var user = new Customer();
        user.setId(UUID.randomUUID());
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }
}
