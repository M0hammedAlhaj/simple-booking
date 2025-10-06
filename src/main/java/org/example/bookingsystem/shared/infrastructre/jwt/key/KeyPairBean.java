package org.example.bookingsystem.shared.infrastructre.jwt.key;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeyPairBean {

    @Bean
    public java.security.KeyPair keyPair() {
        return KeyGeneration.generateKey();
    }
}
