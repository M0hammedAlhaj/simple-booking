package org.example.bookingsystem.shared.infrastructre.jwt;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.bookingsystem.user.domain.entity.User;
import org.example.bookingsystem.user.domain.model.UserAuthentication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtFilterTest {

    @Mock
    JwtService jwtService;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    FilterChain filterChain;

    @InjectMocks
    JwtFilter underTest;

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void should_call_filterChain_when_no_authorization_header() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);

        underTest.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void should_callFilterChain_when_not_start_with_BearerToken() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("NotBearerToken");

        underTest.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void should_set_authentication_when_token_valid() throws Exception {
        String token = "valid-token";
        User user = new User();
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtService.validateToken(token)).thenReturn(user);

        underTest.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());

        assertEquals(new UserAuthentication(user), (SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()));

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void should_return_401_when_token_invalid() throws Exception {
        String token = "invalid-token";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        doThrow(new JwtException("Invalid")).when(jwtService).validateToken(token);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        underTest.doFilterInternal(request, response, filterChain);

        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        assertTrue(stringWriter.toString().contains("Invalid or expired token"));
        verify(filterChain, never()).doFilter(request, response);
    }
}