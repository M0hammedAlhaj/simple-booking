package org.example.bookingsystem.booking.infrastructre.repository.specification.criteria;

import org.example.bookingsystem.booking.domain.entity.Booking;
import org.example.bookingsystem.booking.domain.model.BookingType;
import org.springframework.data.jpa.domain.Specification;

public record BookingTypeCriteria(BookingType type) implements ICriteria {

    @Override
    public Specification<Booking> toSpecification() {
        if (type == null) {
            return null;
        }
        return (root, query, cb) -> cb.equal(root.get("bookingType"), type);
    }
}
