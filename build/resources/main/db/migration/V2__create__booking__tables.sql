CREATE TABLE bookings
(
    id             UUID NOT NULL,
    created_at     TIMESTAMP ,
    updated_at     TIMESTAMP ,
    appointment    TIMESTAMP ,
    booking_type   INTEGER,
    description    VARCHAR(255),
    reservation_id UUID,
    provider_id    UUID,
    CONSTRAINT pk_bookings PRIMARY KEY (id)
);

ALTER TABLE bookings
    ADD CONSTRAINT FK_BOOKINGS_ON_PROVIDER FOREIGN KEY (provider_id) REFERENCES providers (id);

ALTER TABLE bookings
    ADD CONSTRAINT FK_BOOKINGS_ON_RESERVATION FOREIGN KEY (reservation_id) REFERENCES customers (id);