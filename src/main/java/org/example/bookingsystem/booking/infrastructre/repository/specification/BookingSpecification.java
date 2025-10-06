package org.example.bookingsystem.booking.infrastructre.repository.specification;

import lombok.Getter;
import org.example.bookingsystem.booking.domain.entity.Booking;
import org.example.bookingsystem.booking.infrastructre.repository.specification.criteria.ICriteria;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@Getter
public class BookingSpecification {

    private final Specification<Booking> specification;

    public BookingSpecification(List<ICriteria> iCriteriaList) {
        Specification<Booking> spec = Specification.allOf();
        for (var criteria : iCriteriaList) {
            spec = spec.and(criteria.toSpecification());
        }
        this.specification = spec;
    }
}