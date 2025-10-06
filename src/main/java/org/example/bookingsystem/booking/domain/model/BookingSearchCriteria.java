package org.example.bookingsystem.booking.domain.model;

import java.time.LocalDateTime;

public record BookingSearchCriteria(LocalDateTime appointment, BookingType type) {
}
