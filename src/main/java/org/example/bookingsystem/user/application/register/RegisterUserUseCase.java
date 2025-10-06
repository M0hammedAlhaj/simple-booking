package org.example.bookingsystem.user.application.register;

import lombok.RequiredArgsConstructor;
import org.example.bookingsystem.user.domain.entity.User;
import org.example.bookingsystem.user.domain.factory.UserFactoryProvider;
import org.example.bookingsystem.user.domain.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserFactoryProvider userFactoryProvider;

    @Transactional
    public User registerUser(RegisterUserCommand command) {

        if (userRepository.existByEmail(command.email())) {
            String randomPassword = UUID.randomUUID().toString();
            final var hashedPassword = passwordEncoder.encode(randomPassword);
            return userFactoryProvider.createUser(command.email(),
                    hashedPassword,
                    command.userType());
        }

        final var hashedPassword = passwordEncoder.encode(command.password());
        var user = userFactoryProvider.createUser(command.email(),
                hashedPassword,
                command.userType());

        userRepository.save(user);
        return user;
    }
}
