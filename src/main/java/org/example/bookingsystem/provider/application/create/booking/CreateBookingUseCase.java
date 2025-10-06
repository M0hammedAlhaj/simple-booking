package org.example.bookingsystem.provider.application.create.booking;

import lombok.RequiredArgsConstructor;
import org.example.bookingsystem.booking.domain.entity.Booking;
import org.example.bookingsystem.provider.domain.entity.Provider;
import org.example.bookingsystem.provider.domain.repository.ProviderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateBookingUseCase {

    private final ProviderRepository providerRepository;

    @Transactional
    public Booking create(CreateBookingCommand command) {

        if (command.owner() instanceof Provider provider) {
            var providerWithBooking = providerRepository.findProviderWithBookingById(provider.getId()).orElseThrow();

            Booking booking = new Booking();
            booking.setId(UUID.randomUUID());
            booking.setCreatedAt(LocalDateTime.now());
            booking.setUpdatedAt(LocalDateTime.now());
            booking.setOwner(providerWithBooking);
            booking.setBookingType(command.bookingType());
            booking.setAppointment(command.time());
            booking.setDescription(command.description());
            providerWithBooking.addBooking(booking);

            providerRepository.save(providerWithBooking);
            return booking;
        }
        throw new UnsupportedOperationException("user can't make action");
    }
}
