package org.example.bookingsystem.booking.domain.repository;

import org.example.bookingsystem.booking.domain.entity.Booking;
import org.example.bookingsystem.booking.domain.model.BookingSearchCriteria;
import org.example.bookingsystem.shared.domain.BaseRepository;

import java.util.List;
import java.util.UUID;

public interface BookingRepository extends BaseRepository<Booking, UUID> {

    List<Booking> searchBooking(BookingSearchCriteria bookingSearchCriteria);
}
