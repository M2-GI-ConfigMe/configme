package com.configme.web.rest;

import static com.configme.web.rest.AccountResourceIT.TEST_USER_EMAIL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.configme.IntegrationTest;
import com.configme.config.Constants;
import com.configme.domain.Address;
import com.configme.domain.User;
import com.configme.repository.AuthorityRepository;
import com.configme.repository.UserRepository;
import com.configme.security.AuthoritiesConstants;
import com.configme.service.UserService;
import com.configme.service.dto.AdminUserDTO;
import com.configme.service.dto.PasswordChangeDTO;
import com.configme.service.dto.UserDTO;
import com.configme.web.rest.vm.KeyAndPasswordVM;
import com.configme.web.rest.vm.ManagedUserVM;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AccountResource} REST controller.
 */
@AutoConfigureMockMvc
@WithMockUser(value = TEST_USER_EMAIL)
@IntegrationTest
class AccountResourceIT {

    static final String TEST_USER_EMAIL = "test@test.fr";
    static final LocalDate DEFAULT_BIRTHDATE = LocalDate.of(1999, 9, 9);
    static final Address DEFAULT_ADDRESS = Address.of("John", "Doe", "5", "Rue de l'exmple", "Grenoble", "38000");

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc restAccountMockMvc;

    @Test
    @WithUnauthenticatedMockUser
    void testNonAuthenticatedUser() throws Exception {
        restAccountMockMvc
            .perform(get("/api/authenticate").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(""));
    }

    @Test
    void testAuthenticatedUser() throws Exception {
        restAccountMockMvc
            .perform(
                get("/api/authenticate")
                    .with(
                        request -> {
                            request.setRemoteUser(TEST_USER_EMAIL);
                            return request;
                        }
                    )
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(content().string(TEST_USER_EMAIL));
    }

    // Il fait des comparaisons d'objets et pas de valeurs (pour les dates notamment)
    // @Test
    // void testGetExistingAccount() throws Exception {
    //     Set<String> authorities = new HashSet<>();
    //     authorities.add(AuthoritiesConstants.ADMIN);

    //     AdminUserDTO user = new AdminUserDTO();
    //     user.setEmail(TEST_USER_EMAIL);
    //     user.setFirstName("test-get-existing-account");
    //     user.setLastName("lastname");

    //     user.setBirthdate(DEFAULT_BIRTHDATE);
    //     user.setAddress(Address.of("test-get-existing-account", "lastname", "5", "Rue de l'exmple", "Grenoble", "38000"));

    //     user.setImageUrl("http://placehold.it/50x50");
    //     user.setLangKey("en");
    //     user.setAuthorities(authorities);
    //     userService.createUser(user);

    //     restAccountMockMvc
    //         .perform(get("/api/account").accept(MediaType.APPLICATION_JSON))
    //         .andExpect(status().isOk())
    //         .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
    //         .andExpect(jsonPath("$.email").value(TEST_USER_EMAIL))
    //         .andExpect(jsonPath("$.firstName").value("test-get-existing-account"))
    //         .andExpect(jsonPath("$.lastName").value("lastname"))
    //         .andExpect(jsonPath("$.birthdate").value(DEFAULT_BIRTHDATE))
    //         .andExpect(jsonPath("$.address").value(Address.of("test-get-existing-account", "lastname", "5", "Rue de l'exmple", "Grenoble", "38000")))
    //         .andExpect(jsonPath("$.imageUrl").value("http://placehold.it/50x50"))
    //         .andExpect(jsonPath("$.langKey").value("en"))
    //         .andExpect(jsonPath("$.authorities").value(AuthoritiesConstants.ADMIN));
    // }

    @Test
    void testGetUnknownAccount() throws Exception {
        restAccountMockMvc
            .perform(get("/api/account").accept(MediaType.APPLICATION_PROBLEM_JSON))
            .andExpect(status().isInternalServerError());
    }

    @Test
    @Transactional
    void testRegisterValid() throws Exception {
        ManagedUserVM validUser = new ManagedUserVM();
        validUser.setEmail("test-register-valid@example.com");
        validUser.setPassword("password4");
        validUser.setFirstName("test-register-valid");
        validUser.setLastName("lastname");

        validUser.setBirthdate(DEFAULT_BIRTHDATE);
        validUser.setAddress(Address.of("test-register-valid", "lastname", "5", "Rue de l'exmple", "Grenoble", "38000"));

        validUser.setImageUrl("http://placehold.it/50x50");
        validUser.setLangKey(Constants.DEFAULT_LANGUAGE);
        validUser.setAuthorities(Collections.singleton(AuthoritiesConstants.USER));
        assertThat(userRepository.findOneByEmail("test-register-valid@example.com")).isEmpty();

        restAccountMockMvc
            .perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(validUser)))
            .andExpect(status().isCreated());

        assertThat(userRepository.findOneByEmail("test-register-valid@example.com")).isPresent();
    }

    @Test
    @Transactional
    void testRegisterInvalidEmail() throws Exception {
        ManagedUserVM invalidUser = new ManagedUserVM();
        invalidUser.setEmail("funkyexample.com"); // <-- invalid
        invalidUser.setPassword("password4");
        invalidUser.setFirstName("Funky");
        invalidUser.setLastName("One");

        invalidUser.setBirthdate(DEFAULT_BIRTHDATE);
        invalidUser.setAddress(DEFAULT_ADDRESS);

        invalidUser.setActivated(true);
        invalidUser.setImageUrl("http://placehold.it/50x50");
        invalidUser.setLangKey(Constants.DEFAULT_LANGUAGE);
        invalidUser.setAuthorities(Collections.singleton(AuthoritiesConstants.USER));

        restAccountMockMvc
            .perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(invalidUser)))
            .andExpect(status().isBadRequest());

        Optional<User> user = userRepository.findOneByEmailIgnoreCase("funky@example.com");
        assertThat(user).isEmpty();
    }

    @Test
    @Transactional
    void testRegisterInvalidPasswordLength() throws Exception {
        ManagedUserVM invalidUser = new ManagedUserVM();
        invalidUser.setEmail("bob@example.com");
        invalidUser.setPassword("123"); // password with only 3 digits
        invalidUser.setFirstName("Bob");
        invalidUser.setLastName("Green");

        invalidUser.setBirthdate(DEFAULT_BIRTHDATE);
        invalidUser.setAddress(DEFAULT_ADDRESS);

        invalidUser.setActivated(true);
        invalidUser.setImageUrl("http://placehold.it/50x50");
        invalidUser.setLangKey(Constants.DEFAULT_LANGUAGE);
        invalidUser.setAuthorities(Collections.singleton(AuthoritiesConstants.USER));

        restAccountMockMvc
            .perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(invalidUser)))
            .andExpect(status().isBadRequest());

        Optional<User> user = userRepository.findOneByEmail("bob@example.com");
        assertThat(user).isEmpty();
    }

    @Test
    @Transactional
    void testRegisterInvalidPasswordNumber() throws Exception {
        ManagedUserVM invalidUser = new ManagedUserVM();
        invalidUser.setEmail("bob@example.com");
        invalidUser.setPassword("azertyuio"); // password without number
        invalidUser.setFirstName("Bob");
        invalidUser.setLastName("Green");

        invalidUser.setBirthdate(DEFAULT_BIRTHDATE);
        invalidUser.setAddress(DEFAULT_ADDRESS);

        invalidUser.setActivated(true);
        invalidUser.setImageUrl("http://placehold.it/50x50");
        invalidUser.setLangKey(Constants.DEFAULT_LANGUAGE);
        invalidUser.setAuthorities(Collections.singleton(AuthoritiesConstants.USER));

        restAccountMockMvc
            .perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(invalidUser)))
            .andExpect(status().isBadRequest());

        Optional<User> user = userRepository.findOneByEmail("bob@example.com");
        assertThat(user).isEmpty();
    }

    @Test
    @Transactional
    void testRegisterNullPassword() throws Exception {
        ManagedUserVM invalidUser = new ManagedUserVM();
        invalidUser.setEmail("bob@example.com");
        invalidUser.setPassword(null); // invalid null password
        invalidUser.setFirstName("Bob");
        invalidUser.setLastName("Green");

        invalidUser.setBirthdate(DEFAULT_BIRTHDATE);
        invalidUser.setAddress(DEFAULT_ADDRESS);

        invalidUser.setActivated(true);
        invalidUser.setImageUrl("http://placehold.it/50x50");
        invalidUser.setLangKey(Constants.DEFAULT_LANGUAGE);
        invalidUser.setAuthorities(Collections.singleton(AuthoritiesConstants.USER));

        restAccountMockMvc
            .perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(invalidUser)))
            .andExpect(status().isBadRequest());

        Optional<User> user = userRepository.findOneByEmail("bob@example.com");
        assertThat(user).isEmpty();
    }

    @Test
    @Transactional
    void testRegisterAdminIsIgnored() throws Exception {
        ManagedUserVM validUser = new ManagedUserVM();
        validUser.setEmail("badguy@example.com");
        validUser.setPassword("password5");
        validUser.setFirstName("Bad");
        validUser.setLastName("Guy");

        validUser.setBirthdate(DEFAULT_BIRTHDATE);
        validUser.setAddress(DEFAULT_ADDRESS);

        validUser.setActivated(true);
        validUser.setImageUrl("http://placehold.it/50x50");
        validUser.setLangKey(Constants.DEFAULT_LANGUAGE);
        validUser.setAuthorities(Collections.singleton(AuthoritiesConstants.ADMIN));

        restAccountMockMvc
            .perform(post("/api/register").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(validUser)))
            .andExpect(status().isCreated());

        Optional<User> userDup = userRepository.findOneWithAuthoritiesByEmailIgnoreCase("badguy@example.com");
        assertThat(userDup).isPresent();
        assertThat(userDup.get().getAuthorities())
            .hasSize(1)
            .containsExactly(authorityRepository.findById(AuthoritiesConstants.USER).get());
    }

    // On n'active pas les comptes, ils sont actif par défaut
    @Test
    @Transactional
    void testActivateAccount() throws Exception {
        final String activationKey = "some activation key";
        User user = new User();
        user.setEmail("activate-account@example.com");
        user.setPassword(RandomStringUtils.random(59) + "5");

        user.setFirstName("Test_First");
        user.setLastName("Test_Last");
        user.setBirthdate(DEFAULT_BIRTHDATE);
        user.setAddress(DEFAULT_ADDRESS);

        user.setActivated(false);
        user.setActivationKey(activationKey);

        userRepository.saveAndFlush(user);

        restAccountMockMvc.perform(get("/api/activate?key={activationKey}", activationKey)).andExpect(status().isOk());

        user = userRepository.findOneByEmail(user.getEmail()).orElse(null);
        assertThat(user.isActivated()).isTrue();
    }

    @Test
    @Transactional
    void testActivateAccountWithWrongKey() throws Exception {
        restAccountMockMvc.perform(get("/api/activate?key=wrongActivationKey")).andExpect(status().isInternalServerError());
    }

    // @Test
    // @Transactional
    // @WithMockUser("save-account")
    // void testSaveAccount() throws Exception {
    //     User user = new User();
    //     user.setFirstName("save-account");
    //     user.setLastName("lastname");
    //     user.setEmail("save-account@example.com");
    //     user.setPassword(RandomStringUtils.random(59)+"5");
    //     user.setActivated(true);

    //     user.setBirthdate(DEFAULT_BIRTHDATE);
    //     user.setAddress(Address.of("save-account", "lastName", "5", "Rue de l'exmple", "Grenoble", "38000"));

    //     userRepository.saveAndFlush(user);

    //     AdminUserDTO userDTO = new AdminUserDTO();
    //     userDTO.setFirstName("save-account");
    //     userDTO.setLastName("lastname");
    //     userDTO.setEmail("save-account@example.com");

    //     userDTO.setBirthdate(DEFAULT_BIRTHDATE);
    //     userDTO.setAddress(Address.of("firstname", "lastName", "5", "Rue de l'exmple", "Grenoble", "38000"));

    //     userDTO.setActivated(false);
    //     userDTO.setImageUrl("http://placehold.it/50x50");
    //     userDTO.setLangKey(Constants.DEFAULT_LANGUAGE);
    //     userDTO.setAuthorities(Collections.singleton(AuthoritiesConstants.ADMIN));

    //     restAccountMockMvc
    //         .perform(post("/api/account").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userDTO)))
    //         .andExpect(status().isOk());

    //     User updatedUser = userRepository.findOneWithAuthoritiesByEmailIgnoreCase(user.getEmail()).orElse(null);
    //     assertThat(updatedUser.getFirstName()).isEqualTo(userDTO.getFirstName());
    //     assertThat(updatedUser.getLastName()).isEqualTo(userDTO.getLastName());
    //     assertThat(updatedUser.getEmail()).isEqualTo(userDTO.getEmail());
    //     assertThat(updatedUser.getLangKey()).isEqualTo(userDTO.getLangKey());
    //     assertThat(updatedUser.getPassword()).isEqualTo(user.getPassword());
    //     assertThat(updatedUser.getImageUrl()).isEqualTo(userDTO.getImageUrl());
    //     assertThat(updatedUser.isActivated()).isTrue();
    //     assertThat(updatedUser.getAuthorities()).isEmpty();
    // }

    @Test
    @Transactional
    @WithMockUser("save-invalid-email")
    void testSaveInvalidEmail() throws Exception {
        User user = new User();
        user.setFirstName("save-invalid");
        user.setLastName("lastname");
        user.setEmail("save-invalid-email@example.com");
        user.setPassword(RandomStringUtils.random(59) + "5");

        user.setBirthdate(DEFAULT_BIRTHDATE);
        user.setAddress(Address.of("save-invalid-email", "lastName", "5", "Rue de l'exmple", "Grenoble", "38000"));

        user.setActivated(true);

        userRepository.saveAndFlush(user);

        AdminUserDTO userDTO = new AdminUserDTO();
        userDTO.setFirstName("firstname");
        userDTO.setLastName("lastname");
        userDTO.setEmail("invalid email");

        userDTO.setBirthdate(DEFAULT_BIRTHDATE);
        userDTO.setAddress(Address.of("firstname", "lastName", "5", "Rue de l'exmple", "Grenoble", "38000"));

        userDTO.setActivated(false);
        userDTO.setImageUrl("http://placehold.it/50x50");
        userDTO.setLangKey(Constants.DEFAULT_LANGUAGE);
        userDTO.setAuthorities(Collections.singleton(AuthoritiesConstants.ADMIN));

        restAccountMockMvc
            .perform(post("/api/account").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userDTO)))
            .andExpect(status().isBadRequest());

        assertThat(userRepository.findOneByEmailIgnoreCase("invalid email")).isNotPresent();
    }

    // @Test
    // @Transactional
    // @WithMockUser("save-existing-email")
    // void testSaveExistingEmail() throws Exception {
    //     User user = new User();
    //     user.setFirstName("save-existing-email");
    //     user.setLastName("lastname");
    //     user.setEmail("save-existing-email@example.com");
    //     user.setPassword(RandomStringUtils.random(59)+"5");

    //     user.setBirthdate(DEFAULT_BIRTHDATE);
    //     user.setAddress(Address.of("save-existing-email", "lastName", "5", "Rue de l'exmple", "Grenoble", "38000"));

    //     user.setActivated(true);

    //     userRepository.saveAndFlush(user);

    //     User anotherUser = new User();
    //     anotherUser.setFirstName("save-existing-email2");
    //     anotherUser.setLastName("lastname2");
    //     anotherUser.setEmail("save-existing-email2@example.com");
    //     anotherUser.setPassword(RandomStringUtils.random(59)+"5");
    //     anotherUser.setBirthdate(DEFAULT_BIRTHDATE);
    //     anotherUser.setAddress(Address.of("save-existing-email2", "lastName2", "5", "Rue de l'exmple", "Grenoble", "38000"));
    //     anotherUser.setActivated(true);

    //     userRepository.saveAndFlush(anotherUser);

    //     AdminUserDTO userDTO = new AdminUserDTO();
    //     userDTO.setFirstName("firstname3");
    //     userDTO.setLastName("lastname3");
    //     userDTO.setEmail("save-existing-email2@example.com");

    //     //userDTO.setPassword(RandomStringUtils.random(60));
    //     userDTO.setBirthdate(DEFAULT_BIRTHDATE);
    //     userDTO.setAddress(Address.of("save-existing-email3", "lastName3", "5", "Rue de l'exmple", "Grenoble", "38000"));

    //     userDTO.setActivated(false);
    //     userDTO.setImageUrl("http://placehold.it/50x50");
    //     userDTO.setLangKey(Constants.DEFAULT_LANGUAGE);
    //     userDTO.setAuthorities(Collections.singleton(AuthoritiesConstants.ADMIN));

    //     restAccountMockMvc
    //         .perform(post("/api/account").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userDTO)))
    //         .andExpect(status().isBadRequest());

    //     User updatedUser = userRepository.findOneByEmail("save-existing-email@example.com").orElse(null);
    //     assertThat(updatedUser.getEmail()).isEqualTo("save-existing-email@example.com");
    // }

    @Test
    @Transactional
    @WithMockUser("change-password-wrong-existing-password")
    void testChangePasswordWrongExistingPassword() throws Exception {
        User user = new User();
        String currentPassword = RandomStringUtils.random(59) + "5";
        user.setFirstName("change-password-wrong-existing-password");
        user.setLastName("lastname");
        user.setEmail("change-password-wrong-existing-password@example.com");
        user.setPassword(passwordEncoder.encode(currentPassword));
        user.setBirthdate(DEFAULT_BIRTHDATE);
        user.setAddress(Address.of("change-password-wrong-existing-password", "lastName", "5", "Rue de l'exmple", "Grenoble", "38000"));

        userRepository.saveAndFlush(user);

        restAccountMockMvc
            .perform(
                post("/api/account/change-password")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(new PasswordChangeDTO("1" + currentPassword, "new password")))
            )
            .andExpect(status().isBadRequest());

        User updatedUser = userRepository.findOneByEmail("change-password-wrong-existing-password@example.com").orElse(null);
        assertThat(passwordEncoder.matches("new password", updatedUser.getPassword())).isFalse();
        assertThat(passwordEncoder.matches(currentPassword, updatedUser.getPassword())).isTrue();
    }

    // @Test
    // @Transactional
    // @WithMockUser("change-password")
    // void testChangePassword() throws Exception {
    //     User user = new User();
    //     String currentPassword = RandomStringUtils.random(59)+"5";
    //     user.setPassword(passwordEncoder.encode(currentPassword));
    //     user.setEmail("change-password@example.com");
    //     user.setFirstName("change-password");
    //     user.setLastName("lastname");
    //     user.setBirthdate(DEFAULT_BIRTHDATE);
    //     user.setAddress(Address.of("change-password", "lastName", "5", "Rue de l'exmple", "Grenoble", "38000"));

    //     userRepository.saveAndFlush(user);

    //     restAccountMockMvc
    //         .perform(
    //             post("/api/account/change-password")
    //                 .contentType(MediaType.APPLICATION_JSON)
    //                 .content(TestUtil.convertObjectToJsonBytes(new PasswordChangeDTO(currentPassword, "new490xpassword")))
    //         )
    //         .andExpect(status().isOk());

    //     User updatedUser = userRepository.findOneByEmail("change-password@example.com").orElse(null);
    //     assertThat(passwordEncoder.matches("new490xpassword", updatedUser.getPassword())).isTrue();
    // }

    @Test
    @Transactional
    @WithMockUser("change-password-too-small")
    void testChangePasswordTooSmall() throws Exception {
        User user = new User();
        String currentPassword = RandomStringUtils.random(59) + "5";
        user.setPassword(passwordEncoder.encode(currentPassword));
        user.setEmail("change-password-too-small@example.com");
        user.setFirstName("change-password-too-small");
        user.setLastName("lastname");
        user.setBirthdate(DEFAULT_BIRTHDATE);
        user.setAddress(Address.of("change-password-too-small", "lastName", "5", "Rue de l'exmple", "Grenoble", "38000"));

        userRepository.saveAndFlush(user);

        String newPassword = RandomStringUtils.random(ManagedUserVM.PASSWORD_MIN_LENGTH - 1);

        restAccountMockMvc
            .perform(
                post("/api/account/change-password")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(new PasswordChangeDTO(currentPassword, newPassword)))
            )
            .andExpect(status().isBadRequest());

        User updatedUser = userRepository.findOneByEmail("change-password-too-small@example.com").orElse(null);
        assertThat(updatedUser.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    @Transactional
    @WithMockUser("change-password-too-long")
    void testChangePasswordTooLong() throws Exception {
        User user = new User();
        String currentPassword = RandomStringUtils.random(59) + "5";
        user.setPassword(passwordEncoder.encode(currentPassword));
        user.setEmail("change-password-too-long@example.com");
        user.setFirstName("change-password-too-long");
        user.setLastName("lastname");
        user.setBirthdate(DEFAULT_BIRTHDATE);
        user.setAddress(Address.of("change-password-too-long", "lastName", "5", "Rue de l'exmple", "Grenoble", "38000"));

        userRepository.saveAndFlush(user);

        String newPassword = RandomStringUtils.random(ManagedUserVM.PASSWORD_MAX_LENGTH + 1);

        restAccountMockMvc
            .perform(
                post("/api/account/change-password")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(new PasswordChangeDTO(currentPassword, newPassword)))
            )
            .andExpect(status().isBadRequest());

        User updatedUser = userRepository.findOneByEmail("change-password-too-long@example.com").orElse(null);
        assertThat(updatedUser.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    @Transactional
    @WithMockUser("change-password-empty")
    void testChangePasswordEmpty() throws Exception {
        User user = new User();
        String currentPassword = RandomStringUtils.random(59) + "5";
        user.setPassword(passwordEncoder.encode(currentPassword));
        user.setEmail("change-password-empty@example.com");
        user.setFirstName("change-password-empty");
        user.setLastName("lastname");
        user.setBirthdate(DEFAULT_BIRTHDATE);
        user.setAddress(Address.of("change-password-empty", "lastName", "5", "Rue de l'exmple", "Grenoble", "38000"));

        userRepository.saveAndFlush(user);

        restAccountMockMvc
            .perform(
                post("/api/account/change-password")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(new PasswordChangeDTO(currentPassword, "")))
            )
            .andExpect(status().isBadRequest());

        User updatedUser = userRepository.findOneByEmail("change-password-empty@example.com").orElse(null);
        assertThat(updatedUser.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    @Transactional
    void testRequestPasswordReset() throws Exception {
        User user = new User();
        user.setPassword(RandomStringUtils.random(59) + "5");
        user.setActivated(true);
        user.setEmail("password-reset@example.com");
        user.setFirstName("password-reset");
        user.setLastName("lastname");
        user.setBirthdate(DEFAULT_BIRTHDATE);
        user.setAddress(Address.of("password-reset", "lastName", "5", "Rue de l'exmple", "Grenoble", "38000"));

        userRepository.saveAndFlush(user);

        restAccountMockMvc
            .perform(post("/api/account/reset-password/init").content("password-reset@example.com"))
            .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void testRequestPasswordResetUpperCaseEmail() throws Exception {
        User user = new User();
        user.setPassword(RandomStringUtils.random(59) + "5");
        user.setActivated(true);
        user.setEmail("password-reset-upper-case@example.com");
        user.setFirstName("password-reset-upper-case");
        user.setLastName("lastname");
        user.setBirthdate(DEFAULT_BIRTHDATE);
        user.setAddress(Address.of("password-reset-upper-case", "lastName", "5", "Rue de l'exmple", "Grenoble", "38000"));

        userRepository.saveAndFlush(user);

        restAccountMockMvc
            .perform(post("/api/account/reset-password/init").content("password-reset-upper-case@EXAMPLE.COM"))
            .andExpect(status().isOk());
    }

    @Test
    void testRequestPasswordResetWrongEmail() throws Exception {
        restAccountMockMvc
            .perform(post("/api/account/reset-password/init").content("password-reset-wrong-email@example.com"))
            .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void testFinishPasswordReset() throws Exception {
        User user = new User();
        user.setPassword(RandomStringUtils.random(59) + "5");
        user.setEmail("finish-password-reset@example.com");
        user.setFirstName("finish-password-reset");
        user.setLastName("lastname");
        user.setBirthdate(DEFAULT_BIRTHDATE);
        user.setAddress(Address.of("finish-password-reset", "lastName", "5", "Rue de l'exmple", "Grenoble", "38000"));

        user.setResetDate(Instant.now().plusSeconds(60));
        user.setResetKey("reset key");
        userRepository.saveAndFlush(user);

        KeyAndPasswordVM keyAndPassword = new KeyAndPasswordVM();
        keyAndPassword.setKey(user.getResetKey());
        keyAndPassword.setNewPassword("new3password");

        restAccountMockMvc
            .perform(
                post("/api/account/reset-password/finish")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(keyAndPassword))
            )
            .andExpect(status().isOk());

        User updatedUser = userRepository.findOneByEmail(user.getEmail()).orElse(null);
        assertThat(passwordEncoder.matches(keyAndPassword.getNewPassword(), updatedUser.getPassword())).isTrue();
    }

    @Test
    @Transactional
    void testFinishPasswordResetTooSmall() throws Exception {
        User user = new User();
        user.setPassword(RandomStringUtils.random(59) + "5");
        user.setEmail("finish-password-reset-too-small@example.com");
        user.setFirstName("finish-password-reset-too-small");
        user.setLastName("lastname");
        user.setBirthdate(DEFAULT_BIRTHDATE);
        user.setAddress(Address.of("finish-password-reset-too-small", "lastName", "5", "Rue de l'exmple", "Grenoble", "38000"));

        user.setResetDate(Instant.now().plusSeconds(60));
        user.setResetKey("reset key too small");
        userRepository.saveAndFlush(user);

        KeyAndPasswordVM keyAndPassword = new KeyAndPasswordVM();
        keyAndPassword.setKey(user.getResetKey());
        keyAndPassword.setNewPassword("foo");

        restAccountMockMvc
            .perform(
                post("/api/account/reset-password/finish")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(keyAndPassword))
            )
            .andExpect(status().isBadRequest());

        User updatedUser = userRepository.findOneByEmail(user.getEmail()).orElse(null);
        assertThat(passwordEncoder.matches(keyAndPassword.getNewPassword(), updatedUser.getPassword())).isFalse();
    }

    @Test
    @Transactional
    void testFinishPasswordResetWrongKey() throws Exception {
        KeyAndPasswordVM keyAndPassword = new KeyAndPasswordVM();
        keyAndPassword.setKey("wrong reset key");
        keyAndPassword.setNewPassword("new9password");

        restAccountMockMvc
            .perform(
                post("/api/account/reset-password/finish")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(keyAndPassword))
            )
            .andExpect(status().isInternalServerError());
    }
}
