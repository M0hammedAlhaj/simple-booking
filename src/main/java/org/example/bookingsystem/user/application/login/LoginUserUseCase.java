package org.example.bookingsystem.user.application.login;

import lombok.RequiredArgsConstructor;
import org.example.bookingsystem.user.domain.entity.User;
import org.example.bookingsystem.user.domain.exception.InvalidCredentialsException;
import org.example.bookingsystem.user.domain.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User login(LoginUserCommand loginUserCommand) {

        final var optionalUser = userRepository.findByEmail(loginUserCommand.email());

        if (optionalUser.isPresent() &&
            passwordEncoder.matches(loginUserCommand.password(), optionalUser.get().getPassword())) {
            return optionalUser.get();
        }

        throw new InvalidCredentialsException("Email or password is invalid");
    }
}
