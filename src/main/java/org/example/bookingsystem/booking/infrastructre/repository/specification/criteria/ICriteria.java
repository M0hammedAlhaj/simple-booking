package org.example.bookingsystem.booking.infrastructre.repository.specification.criteria;

import org.example.bookingsystem.booking.domain.entity.Booking;
import org.springframework.data.jpa.domain.Specification;

public interface ICriteria {

    Specification<Booking> toSpecification();
}