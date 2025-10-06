package org.example.bookingsystem.user.ui.rest.login;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bookingsystem.shared.infrastructre.jwt.JwtService;
import org.example.bookingsystem.user.application.login.LoginUserCommand;
import org.example.bookingsystem.user.application.login.LoginUserUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserLoginController {

    private final LoginUserUseCase useCase;
    private final JwtService jwtService;

    @PostMapping("/api/v1/users/login")
    public ResponseEntity<String> invoke(@Valid @RequestBody UserLoginRequest request) {

        final var command = new LoginUserCommand(request.email(), request.password());
        final var user = useCase.login(command);
        final var token = jwtService.generateToken(user);

        return ResponseEntity.ok(token);
    }
}
