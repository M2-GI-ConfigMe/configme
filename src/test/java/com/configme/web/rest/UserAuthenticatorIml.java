package com.configme.web.rest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.configme.domain.Address;
import com.configme.domain.Authority;
import com.configme.domain.User;
import com.configme.repository.UserRepository;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@Service
public class UserAuthenticatorIml implements UserAuthenticator {

    @Autowired
    private MockMvc mock;

    @Autowired
    private UserRepository userRepository;

    private User user;

    private User createUser(String username) {
        Address adress = new Address();
        adress.setCity("fsdf");
        adress.setFirstName("fsdf");
        adress.setLastName("fsdf");
        adress.setStreetName("fsdf");
        adress.setStreetNumber("fsdf");
        adress.setZipCode("fsdf");
        adress.setComplementary("fsdf");

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User user = new User();
        user.setBirthdate(LocalDate.now());
        user.setActivated(true);
        user.setEmail(username);
        user.setPassword(passwordEncoder.encode("password"));
        user.setImageUrl("fsddf");
        user.setAddress(adress);
        user.setFirstName("dfsdf");
        user.setLastName("dfsdf");

        Set<Authority> authorityList = new HashSet<>() {
            {
                Authority aut = new Authority();
                aut.setName("ROLE_USER");
                add(aut);
            }
        };
        user.setAuthorities(authorityList);

        return user;
    }

    public String getBearer(String username, String password) throws Exception {
        user = createUser(username);
        userRepository.saveAndFlush(user);

        String json = "{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}";

        MvcResult result = mock
            .perform(MockMvcRequestBuilders.post("/api/authenticate").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isOk())
            .andReturn();

        String response = result.getResponse().getContentAsString();
        response = response.replace("{\"id_token\":\"", "");
        String token = response.replace("\"}", "");

        return "Bearer " + token;
    }

    public String getBearerForAdmin(String username, String password) throws Exception {
        user = createUser(username);
        Set<Authority> authorityList = user.getAuthorities();
        Authority aut = new Authority();
        aut.setName("ROLE_ADMIN");
        authorityList.add(aut);
        user.setAuthorities(authorityList);
        userRepository.saveAndFlush(user);

        String json = "{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}";

        MvcResult result = mock
            .perform(MockMvcRequestBuilders.post("/api/authenticate").contentType(MediaType.APPLICATION_JSON).content(json))
            .andExpect(status().isOk())
            .andReturn();

        String response = result.getResponse().getContentAsString();
        response = response.replace("{\"id_token\":\"", "");
        String token = response.replace("\"}", "");

        return "Bearer " + token;
    }
}
