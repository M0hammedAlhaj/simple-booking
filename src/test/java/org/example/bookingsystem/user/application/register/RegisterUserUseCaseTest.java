package org.example.bookingsystem.user.application.register;

import org.example.bookingsystem.customer.domain.entity.Customer;
import org.example.bookingsystem.provider.domain.entity.Provider;
import org.example.bookingsystem.user.domain.factory.UserFactoryProvider;
import org.example.bookingsystem.user.domain.model.UserType;
import org.example.bookingsystem.user.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterUserUseCaseTest {

    @InjectMocks
    RegisterUserUseCase underTest;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    UserFactoryProvider userFactoryProvider;

    private Provider provider(UUID id, String email, String password) {
        var p = new Provider();
        p.setId(id);
        p.setEmail(email);
        p.setPassword(password);
        return p;
    }

    private Customer customer(UUID id, String email, String password) {
        var c = new Customer();
        c.setId(id);
        c.setEmail(email);
        c.setPassword(password);
        return c;
    }


    @Test
    void provider_should_register_successfully() {
        var id = UUID.randomUUID();
        var email = "provider@gmail.com";
        var password = "password";
        var hashed = "hashedPassword";

        var request = new RegisterUserCommand(email, password, UserType.PROVIDER);
        var provider = provider(id, email, hashed);

        when(userRepository.existByEmail(email)).thenReturn(false);
        when(passwordEncoder.encode(password)).thenReturn(hashed);
        when(userFactoryProvider.createUser(email, hashed, UserType.PROVIDER)).thenReturn(provider);

        assertDoesNotThrow(() -> underTest.registerUser(request));

        verify(userRepository).existByEmail(email);
        verify(userRepository).save(provider);
    }

    @Test
    void customer_should_register_successfully() {
        var id = UUID.randomUUID();
        var email = "customer@gmail.com";
        var password = "password";
        var hashed = "hashedPassword";

        var request = new RegisterUserCommand(email, password, UserType.PROVIDER);
        var customer = customer(id, email, hashed);

        when(userRepository.existByEmail(email)).thenReturn(false);
        when(passwordEncoder.encode(password)).thenReturn(hashed);
        when(userFactoryProvider.createUser(email, hashed, UserType.PROVIDER)).thenReturn(customer);

        assertDoesNotThrow(() -> underTest.registerUser(request));

        verify(userRepository).existByEmail(email);
        verify(userRepository).save(customer);
    }

    @Test
    void provider_should_not_saved_account() {
        var id = UUID.randomUUID();
        var email = "provider@gmail.com";
        var password = "password";
        var hashed = "hashed_password";

        var request = new RegisterUserCommand(email, password, UserType.PROVIDER);
        var provider = provider(id, email, hashed);

        when(userRepository.existByEmail(email)).thenReturn(true);
        when(passwordEncoder.encode(any())).thenReturn(hashed);
        when(userFactoryProvider.createUser(email, hashed, UserType.PROVIDER)).thenReturn(provider);

        assertDoesNotThrow(() -> underTest.registerUser(request));

        verify(userRepository).existByEmail(email);
        verify(userRepository, never()).save(any());
    }

    @Test
    void customer_should_not_saved_account() {
        var id = UUID.randomUUID();
        var email = "customer@gmail.com";
        var password = "password";
        var hashed = "hashed_password";

        var request = new RegisterUserCommand(email, password, UserType.PROVIDER);
        var customer = customer(id, email, hashed);

        when(userRepository.existByEmail(email)).thenReturn(true);
        when(passwordEncoder.encode(any())).thenReturn(hashed);
        when(userFactoryProvider.createUser(email, hashed, UserType.PROVIDER)).thenReturn(customer);

        assertDoesNotThrow(() -> underTest.registerUser(request));

        verify(userRepository).existByEmail(email);
        verify(userRepository, never()).save(any());
    }
}
