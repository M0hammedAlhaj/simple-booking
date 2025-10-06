package org.example.bookingsystem.user.domain.factory;

import org.example.bookingsystem.customer.domain.factory.CustomerFactory;
import org.example.bookingsystem.provider.domain.entity.Provider;
import org.example.bookingsystem.provider.domain.factory.ProviderFactory;
import org.example.bookingsystem.user.domain.model.UserType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserFactoryProviderTest {

    @Test
    void should_return_customer_factory() {
        UserFactoryProvider userFactoryProvider = new UserFactoryProvider();
        var factory = userFactoryProvider.getUserFactory(UserType.CUSTOMER);

        Assertions.assertEquals(CustomerFactory.class, factory.getClass());
    }

    @Test
    void should_return_provider_factory() {
        UserFactoryProvider userFactoryProvider = new UserFactoryProvider();
        var factory = userFactoryProvider.getUserFactory(UserType.PROVIDER);

        Assertions.assertEquals(ProviderFactory.class, factory.getClass());
    }

    @Test
    void should_throw_exception() {
        UserFactoryProvider userFactoryProvider = new UserFactoryProvider();
        Assertions.assertThrows(UnsupportedOperationException.class, () -> userFactoryProvider.getUserFactory(UserType.UNKNOWN));
    }

    @Test
    void should_create_customer() {
        UserFactoryProvider userFactoryProvider = new UserFactoryProvider();
        var customer = userFactoryProvider.createUser("email", "123", UserType.PROVIDER);
        Assertions.assertEquals(Provider.class, customer.getClass());
    }


    @Test
    void should_create_provider() {
        UserFactoryProvider userFactoryProvider = new UserFactoryProvider();
        var provider = userFactoryProvider.createUser("email", "123", UserType.PROVIDER);
        Assertions.assertEquals(Provider.class, provider.getClass());
    }
}