package org.example.bookingsystem.provider.controller.rest.create.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bookingsystem.booking.domain.model.BookingType;
import org.example.bookingsystem.config.BaseIntegrationTest;
import org.example.bookingsystem.customer.domain.entity.Customer;
import org.example.bookingsystem.provider.domain.entity.Provider;
import org.example.bookingsystem.shared.infrastructre.jwt.JwtService;
import org.example.bookingsystem.user.application.register.RegisterUserCommand;
import org.example.bookingsystem.user.application.register.RegisterUserUseCase;
import org.example.bookingsystem.user.domain.model.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class CreateBookingControllerTest extends BaseIntegrationTest {

    private static final String URL_PATH = "/api/v1/providers/";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RegisterUserUseCase registerUser;

    @Autowired
    JwtService jwtService;

    String tokenProvider;

    String tokenCustomer;

    Provider provider;

    @BeforeEach
    void setUp() {
        final var command = new RegisterUserCommand("provider@gmail.com", "123456", UserType.PROVIDER);
        provider = (Provider) registerUser.registerUser(command);

        final var commandCustomer = new RegisterUserCommand("customer@gmail.com", "123456", UserType.CUSTOMER);
        var customer = (Customer) registerUser.registerUser(commandCustomer);


        tokenProvider = jwtService.generateToken(provider);
        tokenCustomer = jwtService.generateToken(customer);
    }

    @Test
    void provider_should_create_booking() throws Exception {
        CreateBookingRequest request = new CreateBookingRequest(
                LocalDateTime.now().plusDays(1),
                BookingType.CONSULTANT,
                "Initial consultation"
        );

        mockMvc.perform(post(URL_PATH + "bookings/")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenProvider)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.owner").value(provider.getId().toString()))
                .andExpect(jsonPath("$.type").value("CONSULTANT"))
                .andExpect(jsonPath("$.description").value("Initial consultation"))
                .andExpect(jsonPath("$.appointment").exists());
    }

    @Test
    void users_should_cant_create_booking() throws Exception {
        CreateBookingRequest request = new CreateBookingRequest(
                LocalDateTime.now().plusDays(1),
                BookingType.CONSULTANT,
                "Initial consultation"
        );

        mockMvc.perform(post(URL_PATH + "bookings/")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenCustomer)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }
}
