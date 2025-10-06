package org.example.bookingsystem.provider.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.example.bookingsystem.provider.domain.entity.Provider;
import org.example.bookingsystem.provider.domain.repository.ProviderRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProviderRepositoryRelationDb implements ProviderRepository {

    private final JpaProvider jpaProvider;

    @Override
    public Optional<Provider> findById(UUID id) {
        return jpaProvider.findById(id);
    }

    @Override
    public void save(Provider provider) {
        jpaProvider.save(provider);
    }

    @Override
    public Optional<Provider> findProviderWithBookingById(UUID uuid) {
        return jpaProvider.findProviderWithBookingsById(uuid);
    }
}
