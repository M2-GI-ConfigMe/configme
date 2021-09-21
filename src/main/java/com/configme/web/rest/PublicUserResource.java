package com.configme.web.rest;

import com.configme.domain.ClientConfig;
import com.configme.domain.User;
import com.configme.repository.ClientConfigRepository;
import com.configme.service.UserService;
import com.configme.service.dto.UserDTO;
import com.configme.web.rest.errors.BadRequestAlertException;
import java.util.*;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;

@RestController
@RequestMapping("/api")
public class PublicUserResource {

    private static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections.unmodifiableList(
        Arrays.asList("id", "login", "firstName", "lastName", "email", "activated", "langKey")
    );

    private final Logger log = LoggerFactory.getLogger(PublicUserResource.class);

    private static final String ENTITY_NAME = "publicUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserService userService;

    private final ClientConfigRepository clientConfigRepository;

    public PublicUserResource(UserService userService, ClientConfigRepository clientConfigRepository) {
        this.userService = userService;
        this.clientConfigRepository = clientConfigRepository;
    }

    /**
     * {@code GET /users} : get all users with only the public informations - calling this are allowed for anyone.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all users.
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllPublicUsers(Pageable pageable) {
        log.debug("REST request to get all public User names");
        if (!onlyContainsAllowedProperties(pageable)) {
            return ResponseEntity.badRequest().build();
        }

        final Page<UserDTO> page = userService.getAllPublicUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    private boolean onlyContainsAllowedProperties(Pageable pageable) {
        return pageable.getSort().stream().map(Sort.Order::getProperty).allMatch(ALLOWED_ORDERED_PROPERTIES::contains);
    }

    /**
     * Gets a list of all roles.
     * @return a string list of all roles.
     */
    @GetMapping("/authorities")
    public List<String> getAuthorities() {
        return userService.getAuthorities();
    }

    /**
     * {@code GET  /users/client-configs} : get the authenticated user's client configs
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clientConfigs in body.
     */
    @GetMapping("/users/client-configs")
    public List<ClientConfig> getUserClientConfigs(HttpServletRequest request) {
        log.debug("REST request to get authenticated user's ClientConfigs");
        log.debug("Is present : " + userService.getUserWithAuthorities().isPresent());
        return clientConfigRepository.findByUser(userService.getUserWithAuthorities());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.debug("REST request to delete User-id: {}", id);
        log.debug("Is present : " + userService.getUserWithAuthorities().isPresent());

        Optional<User> optionalUser = userService.getUserWithAuthorities();

        if (!optionalUser.isPresent()) {
            throw new BadRequestAlertException("User not found", ENTITY_NAME, "usernotfound");
        }

        User user = optionalUser.get();

        Long userId = user.getId();

        if (!Objects.equals(id, userId)) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        String userEmail = user.getEmail();

        userService.deleteUser(userEmail);

        return ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName, "userManagement.deleted", userEmail)).build();
    }
}
