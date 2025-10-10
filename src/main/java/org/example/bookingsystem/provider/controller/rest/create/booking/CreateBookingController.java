package org.example.bookingsystem.provider.controller.rest.create.booking;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bookingsystem.provider.application.create.booking.CreateBookingCommand;
import org.example.bookingsystem.provider.application.create.booking.CreateBookingUseCase;
import org.example.bookingsystem.user.domain.model.UserAuthentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Providers")
public class CreateBookingController {

    private final CreateBookingUseCase useCase;

    @Operation(
            summary = "Create a new booking",
            description = "Creates a booking for the authenticated provider.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Booking created successfully",
                            content = @Content(schema = @Schema(implementation = CreateBookingResponse.class))),
            }
    )
    @PostMapping("/api/v1/providers/bookings")
    public ResponseEntity<CreateBookingResponse> createBooking(@Valid @RequestBody CreateBookingRequest request,
                                                               @AuthenticationPrincipal UserAuthentication userAuthentication) {
        final var command = new CreateBookingCommand(request.time(),
                request.bookingType(),
                userAuthentication.user(),
                request.description());

        final var result = useCase.create(command);

        return ResponseEntity.ok(new CreateBookingResponse(result));
    }
}
