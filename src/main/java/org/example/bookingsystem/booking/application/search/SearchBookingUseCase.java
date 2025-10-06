package org.example.bookingsystem.booking.application.search;

import lombok.RequiredArgsConstructor;
import org.example.bookingsystem.booking.domain.entity.Booking;
import org.example.bookingsystem.booking.domain.model.BookingSearchCriteria;
import org.example.bookingsystem.booking.domain.repository.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchBookingUseCase {

    private final BookingRepository bookingRepository;

    @Transactional(readOnly = true)
    public List<Booking> execute(SearchBookingCommand command) {
        return bookingRepository.searchBooking(new BookingSearchCriteria(command.appointment(), command.type()));
    }
}
