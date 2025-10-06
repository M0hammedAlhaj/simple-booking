package org.example.bookingsystem.shared.infrastructre.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.example.bookingsystem.shared.infrastructre.jwt.key.KeyGeneration;
import org.example.bookingsystem.user.domain.entity.User;
import org.example.bookingsystem.user.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.KeyPair;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @Mock
    UserRepository userRepository;

    JwtService jwtService;

    KeyPair keyPair;

    @BeforeEach
    void setUp() {
        keyPair = KeyGeneration.generateKey();
        jwtService = new JwtService(userRepository, keyPair);
    }

    @Test
    void validateToken_should_return_user_when_token_valid() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("test@example.com");

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        String token = jwtService.generateToken(user);

        User result = jwtService.validateToken(token);

        assertEquals(user.getId(), result.getId());
        assertEquals(user.getEmail(), result.getEmail());
        verify(userRepository).findById(user.getId());
    }

    @Test
    void validateToken_should_throw_exception_when_user_not_found() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("test@example.com");

        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        String token = jwtService.generateToken(user);

        JwtException ex = assertThrows(JwtException.class, () -> jwtService.validateToken(token));
        assertTrue(ex.getMessage().contains("Invalid user"));
    }

    @Test
    void validateToken_should_throw_exception_when_token_expired() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("test@example.com");

        String expiredToken = Jwts.builder()
                .setId(user.getId().toString())
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis() - 3600000))
                .setExpiration(new Date(System.currentTimeMillis() - 1800000))
                .signWith(jwtService.getPrivateKey())
                .compact();

        JwtException ex = assertThrows(JwtException.class, () -> jwtService.validateToken(expiredToken));
        assertTrue(ex.getMessage().toLowerCase().contains("expired"));
    }


    @Test
    void validateToken_should_throw_exception_when_token_invalid() {
        String invalidToken = "this.is.an.invalid.token";

        JwtException ex = assertThrows(JwtException.class, () -> jwtService.validateToken(invalidToken));
        assertNotNull(ex.getMessage());
    }

    @Test
    void generateToken_shouldContainUserClaims() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("test@example.com");

        String token = jwtService.generateToken(user);

        assertNotNull(token);
        assertFalse(token.isEmpty());

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(keyPair.getPublic())
                .build()
                .parseClaimsJws(token)
                .getBody();

        assertEquals(user.getId().toString(), claims.getId());
        assertEquals(user.getEmail(), claims.getSubject());

        Date issuedAt = claims.getIssuedAt();
        Date expiration = claims.getExpiration();

        assertNotNull(issuedAt);
        assertNotNull(expiration);
        assertTrue(expiration.after(issuedAt), "Expiration must be after issuedAt");
    }
}
