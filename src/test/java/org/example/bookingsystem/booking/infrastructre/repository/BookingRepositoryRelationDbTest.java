package org.example.bookingsystem.booking.infrastructre.repository;

import org.example.bookingsystem.booking.domain.entity.Booking;
import org.example.bookingsystem.booking.domain.model.BookingSearchCriteria;
import org.example.bookingsystem.booking.domain.model.BookingType;
import org.example.bookingsystem.config.BaseIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class BookingRepositoryRelationDbTest extends BaseIntegrationTest {

    @Autowired
    BookingRepositoryRelationDb underTest;

    @Autowired
    JpaBooking jpaBooking;

    @BeforeEach
    void setup() {
        jpaBooking.deleteAll();
    }

    static List<Booking> createBookings() {
        List<Booking> bookings = new ArrayList<>();
        var booking1 = new Booking();
        booking1.setId(UUID.randomUUID());
        booking1.setAppointment(LocalDateTime.of(2025, 10, 5, 10, 0));
        booking1.setBookingType(BookingType.CONSULTANT);
        booking1.setDescription("First booking");
        booking1.setCreatedAt(LocalDateTime.now());
        bookings.add(booking1);

        var booking2 = new Booking();
        booking2.setId(UUID.randomUUID());
        booking2.setAppointment(LocalDateTime.of(2025, 9, 5, 10, 0));
        booking2.setBookingType(BookingType.EDUCATION);
        booking2.setDescription("Second booking");
        booking2.setCreatedAt(LocalDateTime.now());
        bookings.add(booking2);

        var booking3 = new Booking();
        booking3.setId(UUID.randomUUID());
        booking3.setAppointment(LocalDateTime.of(2025, 9, 5, 10, 0));
        booking3.setBookingType(BookingType.HOME);
        booking3.setDescription("Second booking");
        booking3.setCreatedAt(LocalDateTime.now());
        bookings.add(booking3);
        return bookings;
    }

    @Test
    void shouldReturnBooking_whenAppointmentAndTypeMatch() {
        var bookingsToSave = createBookings();
        jpaBooking.saveAll(bookingsToSave);

        final var appointment = LocalDateTime.of(2025, 10, 5, 10, 0);
        final var criteria = new BookingSearchCriteria(appointment, BookingType.CONSULTANT);

        var bookings = underTest.searchBooking(criteria);

        Assertions.assertNotNull(bookings);
        Assertions.assertEquals(1, bookings.size());
        Assertions.assertEquals(appointment, bookings.get(0).getAppointment());
        Assertions.assertNotNull(bookings.get(0).getId());
    }

    @Test
    void shouldReturnBooking_whenAppointmentIsNullButTypeMatches() {
        var bookingsToSave = createBookings();
        jpaBooking.saveAll(bookingsToSave);

        final var criteria = new BookingSearchCriteria(null, BookingType.CONSULTANT);

        var bookings = underTest.searchBooking(criteria);

        Assertions.assertNotNull(bookings);
        Assertions.assertEquals(1, bookings.size());
        Assertions.assertEquals(BookingType.CONSULTANT, bookings.get(0).getBookingType());
    }

    @Test
    void shouldReturnBooking_whenTypeIsNullButAppointmentMatches() {
        var bookingsToSave = createBookings();
        jpaBooking.saveAll(bookingsToSave);

        final var appointment = LocalDateTime.of(2025, 10, 5, 10, 0);
        final var criteria = new BookingSearchCriteria(appointment, null);

        var bookings = underTest.searchBooking(criteria);

        Assertions.assertNotNull(bookings);
        Assertions.assertEquals(1, bookings.size());
        Assertions.assertEquals(appointment, bookings.get(0).getAppointment());
    }

    @Test
    void shouldReturnAllBookings_whenBothAppointmentAndTypeAreNull() {
        var bookingsToSave = createBookings();
        jpaBooking.saveAll(bookingsToSave);

        final var criteria = new BookingSearchCriteria(null, null);

        var bookings = underTest.searchBooking(criteria);

        Assertions.assertNotNull(bookings);
        Assertions.assertEquals(3, bookings.size());
    }

    @Test
    void shouldReturnEmptyList_whenNoBookingMatches() {
        var bookingsToSave = createBookings();
        jpaBooking.saveAll(bookingsToSave);

        final var criteria = new BookingSearchCriteria(
                LocalDateTime.of(2030, 1, 1, 0, 0),
                BookingType.EDUCATION
        );

        var bookings = underTest.searchBooking(criteria);

        Assertions.assertNotNull(bookings);
        Assertions.assertTrue(bookings.isEmpty());
    }
}