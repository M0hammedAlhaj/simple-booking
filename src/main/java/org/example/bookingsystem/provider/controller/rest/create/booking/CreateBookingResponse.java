package org.example.bookingsystem.provider.controller.rest.create.booking;

import lombok.Getter;
import org.example.bookingsystem.booking.domain.entity.Booking;
import org.example.bookingsystem.booking.domain.model.BookingType;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class CreateBookingResponse {

    private final UUID id;
    private final UUID owner;
    private final String description;
    private final BookingType type;
    private final LocalDateTime appointment;

    public CreateBookingResponse(Booking booking) {
        id = booking.getId();
        owner = booking.getOwner().getId();
        description = booking.getDescription();
        type = booking.getBookingType();
        appointment = booking.getAppointment();
    }
}
