package org.example.bookingsystem.booking.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.example.bookingsystem.booking.domain.model.BookingType;
import org.example.bookingsystem.customer.domain.entity.Customer;
import org.example.bookingsystem.provider.domain.entity.Provider;
import org.example.bookingsystem.shared.domain.BaseEntity;

import java.time.LocalDateTime;

@Table(name = "bookings")
@Entity
@EqualsAndHashCode(callSuper = true,onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking extends BaseEntity {

    private LocalDateTime appointment;

    private BookingType bookingType;

    private String description;

    @ManyToOne
    @JoinColumn(name = "reservation_id", insertable = false, updatable = false)
    private Customer reservation;

    @ManyToOne
    @JoinColumn(name = "provider_id", insertable = false, updatable = false)
    private Provider owner;
}
