package org.example.bookingsystem.user.application.login;

import org.example.bookingsystem.user.domain.entity.User;
import org.example.bookingsystem.user.domain.exception.InvalidCredentialsException;
import org.example.bookingsystem.user.domain.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginUserUseCaseTest {

    @InjectMocks
    LoginUserUseCase undertest;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    void user_should_login_sucessfully() {
        final var email = "email@gmail.com";
        final var rawPassword = "password";
        final var command = new LoginUserCommand(email, rawPassword);

        final var user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail(email);
        user.setPassword("hashedPassword");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(rawPassword, user.getPassword())).thenReturn(true);

        var returnedUser = Assertions.assertDoesNotThrow(() -> undertest.login(command));

        Assertions.assertEquals(user.getEmail(), returnedUser.getEmail());
        Assertions.assertEquals(user.getPassword(), returnedUser.getPassword());

        verify(userRepository).findByEmail(email);
        verify(passwordEncoder).matches(rawPassword, user.getPassword());
    }

    @Test
    void user_should_not_login_sucessfully_invalid_credientials_when_user_not_found() {
        final var email = "email@gmail.com";
        final var rawPassword = "password";
        final var command = new LoginUserCommand(email, rawPassword);

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Assertions.assertThrows(InvalidCredentialsException.class, () -> undertest.login(command));

        verify(userRepository).findByEmail(email);
    }

    @Test
    void user_should_not_login_sucessfully_invalid_credientials_when_password_invalid() {
        final var email = "email@gmail.com";
        final var rawPassword = "password";
        final var command = new LoginUserCommand(email, rawPassword);

        final var user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail(email);
        user.setPassword("hashedPassword");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(rawPassword, user.getPassword())).thenReturn(false);

        Assertions.assertThrows(InvalidCredentialsException.class, () -> undertest.login(command));

        verify(userRepository).findByEmail(email);
        verify(passwordEncoder).matches(rawPassword, user.getPassword());
    }
}
