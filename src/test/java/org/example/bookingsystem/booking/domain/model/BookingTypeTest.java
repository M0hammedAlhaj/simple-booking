package org.example.bookingsystem.booking.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BookingTypeTest {

    @ParameterizedTest
    @CsvSource({
            "1, HEALTHCARE",
            "2, EDUCATION",
            "3, HOME",
            "4, CONSULTANT",
            "-1, UNKNOWN",
            "1585, UNKNOWN"
    })
    void bookingType_valueOf_should_return_correct_enum(int value, BookingType expected) {
        Assertions.assertEquals(expected, BookingType.valueOf(value));
    }
}