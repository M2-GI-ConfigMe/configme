package com.configme.web.rest;

import com.configme.domain.ClientConfig;
import com.configme.domain.Order;
import com.configme.domain.User;
import com.configme.repository.ClientConfigRepository;
import com.configme.repository.OrderRepository;
import com.configme.repository.UserRepository;
import com.configme.service.UserService;
import com.configme.service.dto.AdminUserDTO;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
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

    private final OrderRepository orderRepository;

    public PublicUserResource(UserService userService, ClientConfigRepository clientConfigRepository, OrderRepository orderRepository) {
        this.userService = userService;
        this.clientConfigRepository = clientConfigRepository;
        this.orderRepository = orderRepository;
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
     * {@code GET  /users/{id}/client-configs : get the user's client configs only if the given user is authenticated
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clientConfigs in body.
     */
    @GetMapping("/users/{id}/client-configs")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<ClientConfig> getUserClientConfigs(HttpServletRequest request, @PathVariable Long id) {
        log.debug("REST request to get authenticated user's ClientConfigs");

        Optional<User> user = userService.getUserWithAuthorities();

        boolean isAdmin = user.get().getAuthorities().stream().anyMatch(a -> a.equals("ROLE_ADMIN"));

        if (!isAdmin && (!user.get().getId().equals(id))) {
            throw new AccessDeniedException("User is not authorized");
        }

        return clientConfigRepository.findByUser(user);
    }

    /**
     * {@code GET  /users/{id}/orders } : get the given user's orders only if he's authenticated
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clientConfigs in body.
     */
    @GetMapping("/users/{id}/orders")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<Order> getUserOrders(HttpServletRequest request, @PathVariable Long id) {
        log.debug("REST request to get authenticated user's orders");

        Optional<User> user = userService.getUserWithAuthorities();

        boolean isAdmin = user.get().getAuthorities().stream().anyMatch(a -> a.equals("ROLE_ADMIN"));

        if (!isAdmin && (!user.get().getId().equals(id))) {
            throw new AccessDeniedException("User is not authorized");
        }

        return orderRepository.findByBuyer(user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.debug("REST request to delete User-id: {}", id);
        log.debug("Is present : " + userService.getUserWithAuthorities().isPresent());
        Optional<User> connectedUser = userService.getUserWithAuthorities();

        Long userId = connectedUser.get().getId();

        if (!Objects.equals(id, userId)) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        String userEmail = connectedUser.get().getEmail();

        userService.deleteUser(userEmail);

        return ResponseEntity.noContent().headers(HeaderUtil.createAlert(applicationName, "userManagement.deleted", userEmail)).build();
    }
}
