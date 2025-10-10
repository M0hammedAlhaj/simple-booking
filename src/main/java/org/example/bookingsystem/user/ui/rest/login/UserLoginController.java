package org.example.bookingsystem.user.ui.rest.login;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bookingsystem.shared.infrastructre.jwt.JwtService;
import org.example.bookingsystem.user.application.login.LoginUserCommand;
import org.example.bookingsystem.user.application.login.LoginUserUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth")
@RestController
@RequiredArgsConstructor
public class UserLoginController {

    private final LoginUserUseCase useCase;
    private final JwtService jwtService;

    @Operation(
            summary = "Login user",
            description = "Authenticates a user and returns a JWT token.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login successful, JWT token returned",
                            content = @Content(schema = @Schema(implementation = String.class))),
            }
    )
    @PostMapping("/api/v1/auth/login")
    public ResponseEntity<String> invoke(@Valid @RequestBody UserLoginRequest request) {

        final var command = new LoginUserCommand(request.email(), request.password());
        final var user = useCase.login(command);
        final var token = jwtService.generateToken(user);

        return ResponseEntity.ok(token);
    }
}
