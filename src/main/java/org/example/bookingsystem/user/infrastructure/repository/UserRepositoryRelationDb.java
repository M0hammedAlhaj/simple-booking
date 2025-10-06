package org.example.bookingsystem.user.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.example.bookingsystem.user.domain.entity.User;
import org.example.bookingsystem.user.domain.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryRelationDb implements UserRepository {

    private final JpaUser jpaUser;

    @Override
    public Optional<User> findById(UUID id) {
        return jpaUser.findById(id);
    }

    @Override
    public void save(User user) {
        jpaUser.save(user);
    }

    @Override
    public boolean existByEmail(String email) {
        return jpaUser.existsByEmail(email);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUser.findByEmail(email);
    }
}
