package org.example.bookingsystem.booking.application.search;

import org.example.bookingsystem.booking.domain.model.BookingType;

import java.time.LocalDateTime;

public record SearchBookingCommand(BookingType type, LocalDateTime appointment) {
}
