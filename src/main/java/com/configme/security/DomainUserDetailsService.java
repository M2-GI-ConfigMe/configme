package com.configme.security;

import com.configme.domain.User;
import com.configme.repository.UserRepository;
import java.util.*;
import java.util.stream.Collectors;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UserRepository userRepository;

    public DomainUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String email) {
        log.debug("Authentication {}", email);
        return userRepository
            .findOneWithAuthoritiesByEmailIgnoreCase(email)
            .map(user -> createSpringSecurityUser(email, user))
            .orElseThrow(() -> new UsernameNotFoundException("User " + email + " was not found in the database"));
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String email, User user) {
        if (!user.isActivated()) {
            throw new UserNotActivatedException("User " + email + " was not activated");
        }
        List<GrantedAuthority> grantedAuthorities = user
            .getAuthorities()
            .stream()
            .map(authority -> new SimpleGrantedAuthority(authority.getName()))
            .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
    }
}
