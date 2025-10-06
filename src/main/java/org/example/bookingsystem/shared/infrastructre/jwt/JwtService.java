package org.example.bookingsystem.shared.infrastructre.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import org.example.bookingsystem.user.domain.entity.User;
import org.example.bookingsystem.user.domain.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.UUID;

@Component
@Getter
public class JwtService {

    private static final long EXPIRATION_MS = 3600000;
    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    private final UserRepository userRepository;

    public JwtService(UserRepository userRepository, KeyPair keyPair) {
        this.userRepository = userRepository;
        this.privateKey = keyPair.getPrivate();
        this.publicKey = keyPair.getPublic();
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .setId(user.getId().toString())
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(privateKey)
                .compact();
    }

    public User validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            UUID id = UUID.fromString(claims.getId());

            return userRepository.findById(id)
                    .orElseThrow(() -> new JwtException("Invalid user"));
        } catch (Exception e) {
            throw new JwtException(e.getMessage());
        }
    }
}
