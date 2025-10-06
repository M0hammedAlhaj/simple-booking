package org.example.bookingsystem.booking.domain.model;

public enum BookingType {

    UNKNOWN(-1),
    HEALTHCARE(1),
    EDUCATION(2),
    HOME(3),
    CONSULTANT(4);

    public final int value;

    BookingType(int value) {
        this.value = value;
    }

    public static BookingType valueOf(int value) {
        for (BookingType bookingType : BookingType.values()) {
            if (bookingType.value == value) {
                return bookingType;
            }
        }
        return UNKNOWN;
    }
}
