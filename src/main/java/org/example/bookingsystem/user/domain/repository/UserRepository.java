package org.example.bookingsystem.user.domain.repository;

import org.example.bookingsystem.shared.domain.BaseRepository;
import org.example.bookingsystem.user.domain.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends BaseRepository<User, UUID> {

    boolean existByEmail(String email);

    Optional<User> findByEmail(String email);
}
