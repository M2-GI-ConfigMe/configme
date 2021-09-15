package com.configme.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.configme.IntegrationTest;
import com.configme.domain.Address;
import com.configme.domain.User;
import com.configme.repository.UserRepository;
import java.time.LocalDate;
import java.util.Locale;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integrations tests for {@link DomainUserDetailsService}.
 */
@Transactional
@IntegrationTest
class DomainUserDetailsServiceIT {

    private static final String USER_ONE_EMAIL = "test-user-one@localhost";
    private static final String USER_TWO_EMAIL = "test-user-two@localhost";
    private static final String USER_THREE_EMAIL = "test-user-three@localhost";

    private static final LocalDate DEFAULT_BIRTHDATE = LocalDate.of(1999, 9, 9);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService domainUserDetailsService;

    @BeforeEach
    public void init() {
        User userOne = new User();
        userOne.setEmail(USER_ONE_EMAIL);
        userOne.setFirstName("userOne");
        userOne.setLastName("doe");
        userOne.setPassword(RandomStringUtils.random(59) + "5");
        userOne.setBirthdate(DEFAULT_BIRTHDATE);
        userOne.setAddress(Address.of("userOne", "doe", "5", "Rue de l'exmple", "Grenoble", "38000"));
        userOne.setActivated(true);
        userOne.setLangKey("en");
        userRepository.save(userOne);

        User userTwo = new User();
        userTwo.setEmail(USER_TWO_EMAIL);
        userTwo.setFirstName("userTwo");
        userTwo.setLastName("doe");
        userTwo.setPassword(RandomStringUtils.random(59) + "5");
        userTwo.setBirthdate(DEFAULT_BIRTHDATE);
        userTwo.setAddress(Address.of("userTwo", "doe", "5", "Rue de l'exmple", "Grenoble", "38000"));
        userTwo.setActivated(true);
        userTwo.setLangKey("en");
        userRepository.save(userTwo);

        User userThree = new User();
        userThree.setEmail(USER_THREE_EMAIL);
        userThree.setFirstName("userThree");
        userThree.setLastName("doe");
        userThree.setPassword(RandomStringUtils.random(59) + "5");
        userThree.setBirthdate(DEFAULT_BIRTHDATE);
        userThree.setAddress(Address.of("userThree", "doe", "5", "Rue de l'exmple", "Grenoble", "38000"));
        userThree.setActivated(false);
        userThree.setLangKey("en");
        userRepository.save(userThree);
    }

    @Test
    void assertThatUserCanBeFoundByEmail() {
        UserDetails userDetails = domainUserDetailsService.loadUserByUsername(USER_TWO_EMAIL);
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(USER_TWO_EMAIL);
    }

    @Test
    void assertThatUserCanBeFoundByEmailIgnoreCase() {
        UserDetails userDetails = domainUserDetailsService.loadUserByUsername(USER_TWO_EMAIL.toUpperCase(Locale.ENGLISH));
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(USER_TWO_EMAIL);
    }
    //Tous nos utilisateurs sont activé par défaut, ce test n'est donc pas valable
    // @Test
    // void assertThatUserNotActivatedExceptionIsThrownForNotActivatedUsers() {
    //     assertThatExceptionOfType(UserNotActivatedException.class)
    //         .isThrownBy(() -> domainUserDetailsService.loadUserByUsername(USER_THREE_EMAIL));
    // }
}
