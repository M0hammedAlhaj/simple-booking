package org.example.bookingsystem.provider.controller.rest.create.booking;

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
public class CreateBookingController {

    private final CreateBookingUseCase useCase;

    @PostMapping("/api/v1/providers/bookings/")
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
