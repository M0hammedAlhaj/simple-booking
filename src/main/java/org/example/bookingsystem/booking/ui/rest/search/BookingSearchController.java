package org.example.bookingsystem.booking.ui.rest.search;

import lombok.RequiredArgsConstructor;
import org.example.bookingsystem.booking.application.search.SearchBookingCommand;
import org.example.bookingsystem.booking.application.search.SearchBookingUseCase;
import org.example.bookingsystem.booking.domain.model.BookingType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class BookingSearchController {

    private final SearchBookingUseCase searchBookingUseCase;

    @GetMapping("/api/v1/bookings")
    public ResponseEntity<BookingSearchCollection> invoke(@RequestParam(required = false) LocalDateTime appointment,
                                                          @RequestParam(required = false) BookingType type) {

        final var command = new SearchBookingCommand(type, appointment);
        final var bookings = searchBookingUseCase.execute(command);
        final var response = new BookingSearchCollection(bookings);

        return ResponseEntity.ok(response);
    }
}
