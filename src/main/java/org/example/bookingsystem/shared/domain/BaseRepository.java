package org.example.bookingsystem.shared.domain;

import java.util.Optional;

public interface BaseRepository<T, U> {

    Optional<T> findById(U id);

    void save(T t);
}
