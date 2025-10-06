package org.example.bookingsystem.provider.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.bookingsystem.booking.domain.entity.Booking;
import org.example.bookingsystem.user.domain.entity.User;

import java.util.HashSet;
import java.util.Set;

@Table(name = "providers")
@Entity
@DiscriminatorValue("PROVIDER")
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Provider extends User {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "provider_id")
    private Set<Booking> bookings;

    public void addBooking(Booking booking) {
        if (bookings == null) {
            bookings = new HashSet<>();
        }
        bookings.add(booking);
    }
}
