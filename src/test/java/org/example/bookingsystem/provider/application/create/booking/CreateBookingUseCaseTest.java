package org.example.bookingsystem.provider.application.create.booking;

import org.example.bookingsystem.booking.domain.model.BookingType;
import org.example.bookingsystem.customer.domain.entity.Customer;
import org.example.bookingsystem.provider.domain.entity.Provider;
import org.example.bookingsystem.provider.domain.repository.ProviderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateBookingUseCaseTest {

    @InjectMocks
    CreateBookingUseCase underTest;

    @Mock
    ProviderRepository providerRepository;

    @Test
    void provider_should_creation_booking_successfully() {

        final var id = UUID.randomUUID();
        final var time = LocalDateTime.now();
        final var type = BookingType.CONSULTANT;

        var provider = new Provider();
        provider.setId(id);

        final var command = new CreateBookingCommand(time, type, provider, null);

        when(providerRepository.findProviderWithBookingById(id)).thenReturn(Optional.of(provider));

        var booking = Assertions.assertDoesNotThrow(() -> underTest.create(command));
        Assertions.assertNotNull(booking.getId());
        Assertions.assertNotNull(booking.getCreatedAt());
        Assertions.assertNotNull(booking.getUpdatedAt());

        Assertions.assertEquals(time, booking.getAppointment());
        Assertions.assertEquals(type, booking.getBookingType());
        Assertions.assertNull(booking.getDescription());
        Assertions.assertEquals(provider, booking.getOwner());

        verify(providerRepository).save(provider);
        verify(providerRepository).findProviderWithBookingById(id);
    }

    @Test
    void provider_should_creation_booking_successfully_with_all_field() {

        final var id = UUID.randomUUID();
        final var time = LocalDateTime.now();
        final var type = BookingType.CONSULTANT;
        final var description = "This is consulting ";
        var provider = new Provider();
        provider.setId(id);

        final var command = new CreateBookingCommand(time, type, provider, description);

        when(providerRepository.findProviderWithBookingById(id)).thenReturn(Optional.of(provider));

        var booking = Assertions.assertDoesNotThrow(() -> underTest.create(command));
        Assertions.assertNotNull(booking.getId());
        Assertions.assertNotNull(booking.getCreatedAt());
        Assertions.assertNotNull(booking.getUpdatedAt());

        Assertions.assertEquals(time, booking.getAppointment());
        Assertions.assertEquals(type, booking.getBookingType());
        Assertions.assertEquals(provider, booking.getOwner());
        Assertions.assertEquals(description, booking.getDescription());

        verify(providerRepository).save(provider);
        verify(providerRepository).findProviderWithBookingById(id);
    }


    @Test
    void user_should_not_creation_booking_successfully() {
        var user = new Customer();
        final var command = new CreateBookingCommand(LocalDateTime.now(), BookingType.EDUCATION, user, null);

        Assertions.assertThrows(UnsupportedOperationException.class, () -> underTest.create(command));
    }

}