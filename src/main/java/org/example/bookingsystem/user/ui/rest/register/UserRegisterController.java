package org.example.bookingsystem.user.ui.rest.register;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bookingsystem.user.application.register.RegisterUserCommand;
import org.example.bookingsystem.user.application.register.RegisterUserUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth")
@RestController
@RequiredArgsConstructor
public class UserRegisterController {

    private final RegisterUserUseCase registerUserUseCase;

    @Operation(
            summary = "Register a new user",
            description = "Registers a new user using email, password, and user type.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User registered successfully",
                            content = @Content(schema = @Schema(implementation = UserRegisterResponse.class)))
            }
    )
    @PostMapping("/api/v1/auth/register")
    public ResponseEntity<UserRegisterResponse> invoke(@Valid @RequestBody UserRegisterRequest request) {

        final var command = new RegisterUserCommand(request.email(), request.password(), request.userType());
        final var user = registerUserUseCase.registerUser(command);
        final var response = new UserRegisterResponse(user.getId(), user.getEmail(), user.getType());

        return ResponseEntity.ok(response);
    }
}
