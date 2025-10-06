package org.example.bookingsystem.provider.infrastructure.repository;

import org.example.bookingsystem.provider.domain.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaProvider extends JpaRepository<Provider, UUID> {

    @Query("SELECT p FROM Provider p LEFT JOIN FETCH p.bookings WHERE p.id =:id")
    Optional<Provider> findProviderWithBookingsById(@Param("id") UUID id);
}
