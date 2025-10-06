package org.example.bookingsystem.booking.ui.rest.search;

import lombok.Getter;
import org.example.bookingsystem.booking.domain.entity.Booking;
import org.example.bookingsystem.booking.domain.model.BookingType;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class BookingSearchResponse {

    private final UUID id;

    private final UUID owner;

    private final LocalDateTime appointment;

    private final BookingType bookingType;

    private String description;

    public BookingSearchResponse(Booking booking) {
        this.id = booking.getId();
        this.owner = booking.getOwner().getId();
        this.appointment = booking.getAppointment();
        this.bookingType = booking.getBookingType();
        this.description = booking.getDescription();
    }
}
