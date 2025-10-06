package org.example.bookingsystem.user.domain.factory;

import org.example.bookingsystem.user.domain.entity.User;

public interface UserFactory {

    User create(String email, String password);
}
