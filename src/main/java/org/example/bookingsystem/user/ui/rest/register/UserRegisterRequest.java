package org.example.bookingsystem.user.ui.rest.register;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.example.bookingsystem.user.domain.model.UserType;

public record UserRegisterRequest(@Email String email, @NotEmpty @Size(min = 6, max = 24) String password,
                                  @NotNull UserType userType) {

}
