package org.example.bookingsystem.provider.application.create.booking;

import org.example.bookingsystem.booking.domain.model.BookingType;
import org.example.bookingsystem.user.domain.entity.User;

import java.time.LocalDateTime;

public record CreateBookingCommand(LocalDateTime time, BookingType bookingType, User owner,
                                   String description) {
}
