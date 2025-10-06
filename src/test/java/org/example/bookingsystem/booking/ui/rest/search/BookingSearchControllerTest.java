package org.example.bookingsystem.booking.ui.rest.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bookingsystem.booking.domain.model.BookingType;
import org.example.bookingsystem.config.BaseIntegrationTest;
import org.example.bookingsystem.provider.application.create.booking.CreateBookingCommand;
import org.example.bookingsystem.provider.application.create.booking.CreateBookingUseCase;
import org.example.bookingsystem.user.application.register.RegisterUserCommand;
import org.example.bookingsystem.user.application.register.RegisterUserUseCase;
import org.example.bookingsystem.user.domain.model.UserType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookingSearchControllerTest extends BaseIntegrationTest {

    private static final String URL_PATH = "/api/v1/bookings";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeAll
    static void setUp(@Autowired CreateBookingUseCase createBookingUseCase,
                      @Autowired RegisterUserUseCase userRegisterUserUseCase) {
        var owner = userRegisterUserUseCase.registerUser(
                new RegisterUserCommand("mohamedalhaj900@gmail.com", "123456", UserType.PROVIDER)
        );

        for (int i = 1; i <= 10; i++) {
            createBookingUseCase.create(new CreateBookingCommand(
                    LocalDateTime.of(2026, 11, 2, 12, 30).plusDays(5 + i),
                    BookingType.CONSULTANT,
                    owner,
                    "Booking " + i
            ));
        }

        createBookingUseCase.create(new CreateBookingCommand(
                LocalDateTime.of(2026, 11, 2, 12, 30).plusDays(6),
                BookingType.HEALTHCARE,
                owner,
                "Booking "
        ));
    }

    @Test
    void shouldFilterBookingsByTypeAndAppointment() throws Exception {
        LocalDateTime filterDate = LocalDateTime.of(2026, 11, 2, 12, 30).plusDays(6);
        String filterDateString = filterDate.withSecond(0).toString();

        mockMvc.perform(get(URL_PATH)
                        .param("type", "CONSULTANT")
                        .param("appointment", filterDateString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookings").isArray())
                .andExpect(jsonPath("$.bookings.length()").value(1))
                .andExpect(jsonPath("$.bookings[0].bookingType").value("CONSULTANT"))
                .andExpect(jsonPath("$.bookings[0].appointment").value(startsWith("2026-11-08T12:30")));
    }

    @Test
    void shouldFilterBookingsByAppointment() throws Exception {

        mockMvc.perform(get(URL_PATH)
                        .param("type", "CONSULTANT")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookings").isArray())
                .andExpect(jsonPath("$.bookings.length()").value(10))
                .andExpect(jsonPath("$.bookings[0].bookingType").value("CONSULTANT"));
    }

    @Test
    void shouldFilterBookingsByApp() throws Exception {
        LocalDateTime filterDate = LocalDateTime.of(2026, 11, 2, 12, 30).plusDays(6);
        String filterDateString = filterDate.withSecond(0).toString();

        mockMvc.perform(get(URL_PATH)
                        .param("appointment", filterDateString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookings").isArray())
                .andExpect(jsonPath("$.bookings.length()").value(2))
                .andExpect(jsonPath("$.bookings[0].appointment").value(startsWith("2026-11-08T12:30")));
    }
}