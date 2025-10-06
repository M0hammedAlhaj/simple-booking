package org.example.bookingsystem.booking.domain.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class BookingTypeConverter implements AttributeConverter<BookingType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(BookingType bookingType) {
        return bookingType.value;
    }

    @Override
    public BookingType convertToEntityAttribute(Integer integer) {
        return BookingType.valueOf(integer);
    }
}
