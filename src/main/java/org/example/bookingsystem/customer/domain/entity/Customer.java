package org.example.bookingsystem.customer.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.bookingsystem.booking.domain.entity.Booking;
import org.example.bookingsystem.user.domain.entity.User;

import java.util.Set;

@Table(name = "customers")
@Entity
@DiscriminatorValue("CUSTOMER")
@Data
@EqualsAndHashCode(callSuper = true)
public class Customer extends User {

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Set<Booking> bookings;
}
