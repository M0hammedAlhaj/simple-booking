package org.example.bookingsystem.booking.ui.rest.search;

import lombok.Getter;
import org.example.bookingsystem.booking.domain.entity.Booking;

import java.util.List;

@Getter
public class BookingSearchCollection {

    private final List<BookingSearchResponse> bookings;

    public BookingSearchCollection(List<Booking> bookings) {
        this.bookings = bookings.stream()
                .map(BookingSearchResponse::new)
                .toList();
    }
}
