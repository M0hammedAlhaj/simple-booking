package org.example.bookingsystem.booking.ui.rest.search;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.bookingsystem.booking.application.search.SearchBookingCommand;
import org.example.bookingsystem.booking.application.search.SearchBookingUseCase;
import org.example.bookingsystem.booking.domain.model.BookingType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Tag(name = "Bookings")
@RestController
@RequiredArgsConstructor
public class BookingSearchController {

    private final SearchBookingUseCase searchBookingUseCase;

    @Operation(
            summary = "Search bookings",
            description = "Search bookings optionally filtered by appointment time and booking type.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Bookings retrieved successfully",
                            content = @Content(schema = @Schema(implementation = BookingSearchCollection.class))),
            }
    )
    @GetMapping("/api/v1/bookings")
    public ResponseEntity<BookingSearchCollection> invoke(@RequestParam(required = false) LocalDateTime appointment,
                                                          @RequestParam(required = false) BookingType type) {

        final var command = new SearchBookingCommand(type, appointment);
        final var bookings = searchBookingUseCase.execute(command);
        final var response = new BookingSearchCollection(bookings);

        return ResponseEntity.ok(response);
    }
}
