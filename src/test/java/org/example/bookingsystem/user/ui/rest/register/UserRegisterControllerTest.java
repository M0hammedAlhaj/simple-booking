package org.example.bookingsystem.user.ui.rest.register;

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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserRegisterControllerTest extends BaseIntegrationTest {

    private static final String URL_REGISTRATION_PATH = "/api/v1/users/";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JpaUser jpaUser;

    @Autowired
    UserFactoryProvider userFactoryProvider;

    @Autowired
    EncryptionService encryptionService;

    @BeforeEach
    void setup() {
        jpaUser.deleteAll();

        var hashedPassword = encryptionService.encryptPassword("123456");
        final var user = userFactoryProvider.createUser("user@gmail.com", hashedPassword, UserType.PROVIDER);

        jpaUser.save(user);
    }

    @Test
    void provider_should_registeration_sucessfully() throws Exception {
        var request = new UserRegisterRequest(
                "newprovider@gmail.com",
                "strongPass123",
                UserType.PROVIDER
        );

        mockMvc.perform(post(URL_REGISTRATION_PATH)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.email").value("newprovider@gmail.com"))
                .andExpect(jsonPath("$.type").value(UserType.PROVIDER.name()));
    }

    @Test
    void customer_should_registeration_sucessfully() throws Exception {
        var request = new UserRegisterRequest(
                "newcustomer@gmail.com",
                "strongPass123",
                UserType.CUSTOMER
        );

        mockMvc.perform(post(URL_REGISTRATION_PATH)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.email").value("newcustomer@gmail.com"))
                .andExpect(jsonPath("$.type").value(UserType.CUSTOMER.name()));
    }

    @Test
    void user_registration_with_existing_email_should_succeed_without_saving_new_user() throws Exception {
        var existingEmail = "user@gmail.com";
        var request = new UserRegisterRequest(
                existingEmail,
                "anyPassword123",
                UserType.PROVIDER
        );

        mockMvc.perform(post(URL_REGISTRATION_PATH)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(existingEmail))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.type").value(UserType.PROVIDER.name()));
    }

    @Test
    void registration_should_fail_when_all_fields_invalid() throws Exception {
        var invalidRequest = new UserRegisterRequest(
                "invalid-email",
                "123",
                null
        );

        mockMvc.perform(post(URL_REGISTRATION_PATH)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    assertTrue(json.contains("email"), "Should contain 'email' error");
                    assertTrue(json.contains("password"), "Should contain 'password' error");
                    assertTrue(json.contains("userType"), "Should contain 'userType' error");
                });
    }

    @Test
    void registration_should_fail_when_password_invalid() throws Exception {
        var invalidRequest = new UserRegisterRequest(
                "user@example.com",
                "123", // too short
                UserType.PROVIDER
        );

        mockMvc.perform(post(URL_REGISTRATION_PATH)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    assertTrue(json.contains("password"), "Should contain 'password' error");
                });
    }

    @Test
    void registration_should_fail_when_user_type_null() throws Exception {
        var invalidRequest = new UserRegisterRequest(
                "user@example.com",
                "validPass123",
                null
        );

        mockMvc.perform(post(URL_REGISTRATION_PATH)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(result -> {
                    String json = result.getResponse().getContentAsString();
                    assertTrue(json.contains("userType"), "Should contain 'userType' error");
                });
    }
}
