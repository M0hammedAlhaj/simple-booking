package org.example.bookingsystem.provider.domain.repository;

import org.example.bookingsystem.provider.domain.entity.Provider;
import org.example.bookingsystem.shared.domain.BaseRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProviderRepository extends BaseRepository<Provider, UUID> {

    Optional<Provider> findProviderWithBookingById(UUID uuid);
}
