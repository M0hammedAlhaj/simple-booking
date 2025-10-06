package org.example.bookingsystem.provider.controller.rest.create.booking;

import jakarta.validation.constraints.NotNull;
import org.example.bookingsystem.booking.domain.model.BookingType;

import java.time.LocalDateTime;

public record CreateBookingRequest(@NotNull LocalDateTime time, @NotNull BookingType bookingType,
                                   String description) {
}
