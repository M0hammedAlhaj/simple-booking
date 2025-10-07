package org.example.bookingsystem.user.ui.rest.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bookingsystem.config.BaseIntegrationTest;
import org.example.bookingsystem.user.domain.factory.UserFactoryProvider;
import org.example.bookingsystem.user.domain.model.UserType;
import org.example.bookingsystem.user.domain.service.EncryptionService;
import org.example.bookingsystem.user.infrastructure.repository.JpaUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserLoginControllerTest extends BaseIntegrationTest {

    private static final String URL_LOGIN_PATH = "/api/v1/auth/login";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JpaUser jpaUser;

    @Autowired
    EncryptionService encryptionService;

    @Autowired
    UserFactoryProvider userFactoryProvider;

    @BeforeEach
    @Transactional
    void setup() {
        jpaUser.deleteAll();

        final var hashedPassword = encryptionService.encryptPassword("123456");
        final var user = userFactoryProvider.createUser("user@gmail.com", hashedPassword, UserType.PROVIDER);

        jpaUser.save(user);
    }

    @Test
    void user_should_login_sucessfully() throws Exception {
        final var request = new UserLoginRequest("user@gmail.com", "123456");

        mockMvc.perform(
                        post(URL_LOGIN_PATH)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void user_should_not_login_with_invalid_login() throws Exception {
        final var request = new UserLoginRequest("user@gmail.com", "wrong-password");

        mockMvc.perform(
                        post(URL_LOGIN_PATH)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Email or password is invalid"))
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void user_should_not_login_with_invalid_email() throws Exception {
        final var request = new UserLoginRequest("user1@gmail.com", "password");

        mockMvc.perform(
                        post(URL_LOGIN_PATH)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Email or password is invalid"))
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void user_should_not_login_with_invalid_email_format() throws Exception {
        final var request = new UserLoginRequest("not-an-email", "123456");

        mockMvc.perform(
                        post(URL_LOGIN_PATH)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(org.hamcrest.Matchers.containsString("email")))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void user_should_not_login_with_empty_password() throws Exception {
        final var request = new UserLoginRequest("user@gmail.com", "");

        mockMvc.perform(
                        post(URL_LOGIN_PATH)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(org.hamcrest.Matchers.containsString("password")))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}