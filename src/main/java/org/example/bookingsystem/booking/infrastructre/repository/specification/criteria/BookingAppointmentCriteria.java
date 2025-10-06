package org.example.bookingsystem.booking.infrastructre.repository.specification.criteria;

import org.example.bookingsystem.booking.domain.entity.Booking;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public record BookingAppointmentCriteria(LocalDateTime time) implements ICriteria {

    @Override
    public Specification<Booking> toSpecification() {
        if (time == null) {
            return null;
        }
        return (root, query, cb) -> cb.equal(root.get("appointment"), time);
    }
}
