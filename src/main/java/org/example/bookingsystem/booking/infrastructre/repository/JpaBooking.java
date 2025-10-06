package org.example.bookingsystem.booking.infrastructre.repository;

import org.example.bookingsystem.booking.domain.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

interface JpaBooking extends JpaRepository<Booking, UUID>, JpaSpecificationExecutor<Booking> {
}
