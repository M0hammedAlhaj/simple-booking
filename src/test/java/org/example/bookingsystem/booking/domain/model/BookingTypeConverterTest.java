package org.example.bookingsystem.booking.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BookingTypeConverterTest {

    BookingTypeConverter bookingTypeConverter;

    @BeforeEach
    void setUp() {
        bookingTypeConverter = new BookingTypeConverter();
    }

    @ParameterizedTest
    @CsvSource({
            "1, HEALTHCARE",
            "2, EDUCATION",
            "3, HOME",
            "4, CONSULTANT",
            "-1, UNKNOWN",
            "1585, UNKNOWN"
    })
    void bookingTyp_should_convertToEntityAttribute_successfully(int value, BookingType bookingType) {
        Assertions.assertEquals(bookingType, bookingTypeConverter.convertToEntityAttribute(value));
    }

    @ParameterizedTest
    @CsvSource({
            "1, HEALTHCARE",
            "2, EDUCATION",
            "3, HOME",
            "4, CONSULTANT",
            "-1, UNKNOWN",
    })
    void bookingType_convertToDatabaseColumn_should_convert_successfully(Integer value, BookingType bookingType) {
        Assertions.assertEquals(value, bookingTypeConverter.convertToDatabaseColumn(bookingType));
    }

}