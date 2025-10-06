package org.example.bookingsystem.booking.infrastructre.repository;

import lombok.RequiredArgsConstructor;
import org.example.bookingsystem.booking.domain.entity.Booking;
import org.example.bookingsystem.booking.domain.model.BookingSearchCriteria;
import org.example.bookingsystem.booking.domain.repository.BookingRepository;
import org.example.bookingsystem.booking.infrastructre.repository.specification.criteria.BookingAppointmentCriteria;
import org.example.bookingsystem.booking.infrastructre.repository.specification.BookingSpecification;
import org.example.bookingsystem.booking.infrastructre.repository.specification.criteria.BookingTypeCriteria;
import org.example.bookingsystem.booking.infrastructre.repository.specification.criteria.ICriteria;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class BookingRepositoryRelationDb implements BookingRepository {

    private final JpaBooking jpaBooking;

    @Override
    public Optional<Booking> findById(UUID id) {
        return jpaBooking.findById(id);
    }

    @Override
    public void save(Booking booking) {
        jpaBooking.save(booking);
    }

    @Override
    public List<Booking> searchBooking(BookingSearchCriteria criteria) {

        final var iCriteria = Stream.<Optional<ICriteria>>of(
                        Optional.ofNullable(criteria.appointment()).map(BookingAppointmentCriteria::new),
                        Optional.ofNullable(criteria.type()).map(BookingTypeCriteria::new)
                )
                .flatMap(Optional::stream)
                .toList();

        final var bookingSpecification = new BookingSpecification(iCriteria);
        return jpaBooking.findAll(bookingSpecification.getSpecification());
    }
}