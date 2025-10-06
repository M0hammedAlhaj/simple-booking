package org.example.bookingsystem.user.ui.rest.register;

import org.example.bookingsystem.user.domain.model.UserType;

import java.util.UUID;

public record UserRegisterResponse(UUID id, String email, String password, UserType type) {
}
