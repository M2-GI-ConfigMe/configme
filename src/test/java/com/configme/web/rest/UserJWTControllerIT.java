package com.configme.web.rest;

import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.configme.IntegrationTest;
import com.configme.domain.Address;
import com.configme.domain.User;
import com.configme.repository.UserRepository;
import com.configme.web.rest.vm.LoginVM;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link UserJWTController} REST controller.
 */
@AutoConfigureMockMvc
@IntegrationTest
class UserJWTControllerIT {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    void testAuthorize() throws Exception {
        User user = new User();
        user.setEmail("user-jwt-controller@example.com");
        user.setFirstName("user-jwt-controller");
        user.setLastName("lastname");
        user.setBirthdate(LocalDate.of(2000, 02, 02));
        user.setAddress(Address.of("user-jwt-controller", "lastname", "5", "Rue de l'oublie", "Grenoblle", "39000"));

        user.setActivated(true);
        user.setPassword(passwordEncoder.encode("testtest9"));

        userRepository.saveAndFlush(user);

        LoginVM login = new LoginVM();
        login.setUsername("user-jwt-controller@example.com");
        login.setPassword("testtest9");
        mockMvc
            .perform(post("/api/authenticate").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(login)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id_token").isString())
            .andExpect(jsonPath("$.id_token").isNotEmpty())
            .andExpect(header().string("Authorization", not(nullValue())))
            .andExpect(header().string("Authorization", not(is(emptyString()))));
    }

    //On a supprimé le remember me pour l'instant
    // @Test
    // @Transactional
    // void testAuthorizeWithRememberMe() throws Exception {
    //     User user = new User();
    //     user.setEmail("user-jwt-controller-remember-me@example.com");
    //     user.setActivated(true);
    //     user.setPassword(passwordEncoder.encode("test"));

    //     userRepository.saveAndFlush(user);

    //     LoginVM login = new LoginVM();
    //     login.setUsername("user-jwt-controller-remember-me@example.com");
    //     login.setPassword("test");
    //     login.setRememberMe(true);
    //     mockMvc
    //         .perform(post("/api/authenticate").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(login)))
    //         .andExpect(status().isOk())
    //         .andExpect(jsonPath("$.id_token").isString())
    //         .andExpect(jsonPath("$.id_token").isNotEmpty())
    //         .andExpect(header().string("Authorization", not(nullValue())))
    //         .andExpect(header().string("Authorization", not(is(emptyString()))));
    // }

    @Test
    void testAuthorizeFails() throws Exception {
        LoginVM login = new LoginVM();
        login.setUsername("wrong-user");
        login.setPassword("wrong password");
        mockMvc
            .perform(post("/api/authenticate").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(login)))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.id_token").doesNotExist())
            .andExpect(header().doesNotExist("Authorization"));
    }
}
