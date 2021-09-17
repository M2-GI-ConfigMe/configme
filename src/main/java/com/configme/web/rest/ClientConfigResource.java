package com.configme.web.rest;

import com.configme.domain.ClientConfig;
import com.configme.domain.User;
import com.configme.repository.ClientConfigRepository;
import com.configme.service.UserService;
import com.configme.service.dto.AdminUserDTO;
import com.configme.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.configme.domain.ClientConfig}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ClientConfigResource {

    private static class ClientConfigResourceException extends RuntimeException {

        private ClientConfigResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(ClientConfigResource.class);

    private static final String ENTITY_NAME = "clientConfig";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClientConfigRepository clientConfigRepository;

    private final UserService userService;

    public ClientConfigResource(ClientConfigRepository clientConfigRepository, UserService userService) {
        this.clientConfigRepository = clientConfigRepository;
        this.userService = userService;
    }

    /**
     * {@code POST  /client-configs} : Create a new clientConfig.
     *
     * @param clientConfig the clientConfig to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clientConfig, or with status {@code 400 (Bad Request)} if the clientConfig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/client-configs")
    public ResponseEntity<ClientConfig> createClientConfig(@RequestBody ClientConfig clientConfig) throws URISyntaxException {
        log.debug("REST request to save ClientConfig : {}", clientConfig);

        AdminUserDTO user = userService
            .getUserWithAuthorities()
            .map(AdminUserDTO::new)
            .orElseThrow(() -> new ClientConfigResourceException("User could not be found"));
        User owner = clientConfig.getUser();
        boolean isAdmin = user.getAuthorities().stream().anyMatch(a -> a.equals("ROLE_ADMIN"));
        if (!isAdmin && owner != null && user.getId() != owner.getId()) {
            throw new AccessDeniedException("User is not authorized");
        }

        if (clientConfig.getId() != null) {
            throw new BadRequestAlertException("A new clientConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClientConfig result = clientConfigRepository.save(clientConfig);
        return ResponseEntity
            .created(new URI("/api/client-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /client-configs/:id} : Updates an existing clientConfig.
     *
     * @param id the id of the clientConfig to save.
     * @param clientConfig the clientConfig to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientConfig,
     * or with status {@code 400 (Bad Request)} if the clientConfig is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clientConfig couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/client-configs/{id}")
    public ResponseEntity<ClientConfig> updateClientConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ClientConfig clientConfig
    ) throws URISyntaxException {
        log.debug("REST request to update ClientConfig : {}, {}", id, clientConfig);
        if (clientConfig.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clientConfig.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clientConfigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        clientConfigRepository.findAll();

        ClientConfig c = clientConfigRepository.findById(id).get();
        AdminUserDTO user = userService
            .getUserWithAuthorities()
            .map(AdminUserDTO::new)
            .orElseThrow(() -> new ClientConfigResourceException("User could not be found"));
        User owner = c.getUser();
        boolean isAdmin = user.getAuthorities().stream().anyMatch(a -> a.equals("ROLE_ADMIN"));
        if (!isAdmin && owner != null && user.getId() != owner.getId()) {
            throw new AccessDeniedException("User is not authorized");
        }

        ClientConfig result = clientConfigRepository.save(clientConfig);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clientConfig.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /client-configs/:id} : Partial updates given fields of an existing clientConfig, field will ignore if it is null
     *
     * @param id the id of the clientConfig to save.
     * @param clientConfig the clientConfig to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientConfig,
     * or with status {@code 400 (Bad Request)} if the clientConfig is not valid,
     * or with status {@code 404 (Not Found)} if the clientConfig is not found,
     * or with status {@code 500 (Internal Server Error)} if the clientConfig couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/client-configs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ClientConfig> partialUpdateClientConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ClientConfig clientConfig
    ) throws URISyntaxException {
        log.debug("REST request to partial update ClientConfig partially : {}, {}", id, clientConfig);
        if (clientConfig.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, clientConfig.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!clientConfigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClientConfig c = clientConfigRepository.findById(id).get();
        AdminUserDTO user = userService
            .getUserWithAuthorities()
            .map(AdminUserDTO::new)
            .orElseThrow(() -> new ClientConfigResourceException("User could not be found"));
        User owner = c.getUser();
        boolean isAdmin = user.getAuthorities().stream().anyMatch(a -> a.equals("ROLE_ADMIN"));
        if (!isAdmin && owner != null && user.getId() != owner.getId()) {
            throw new AccessDeniedException("User is not authorized");
        }

        Optional<ClientConfig> result = clientConfigRepository
            .findById(clientConfig.getId())
            .map(
                existingClientConfig -> {
                    if (clientConfig.getTags() != null) {
                        existingClientConfig.setTags(clientConfig.getTags());
                    }
                    if (clientConfig.getName() != null) {
                        existingClientConfig.setName(clientConfig.getName());
                    }
                    if (clientConfig.getDescription() != null) {
                        existingClientConfig.setDescription(clientConfig.getDescription());
                    }
                    if (clientConfig.getIsComlete() != null) {
                        existingClientConfig.setIsComlete(clientConfig.getIsComlete());
                    }
                    if (clientConfig.getResearchKey() != null) {
                        existingClientConfig.setResearchKey(clientConfig.getResearchKey());
                    }
                    if (clientConfig.getCpuPrice() != null) {
                        existingClientConfig.setCpuPrice(clientConfig.getCpuPrice());
                    }
                    if (clientConfig.getGpuPrice() != null) {
                        existingClientConfig.setGpuPrice(clientConfig.getGpuPrice());
                    }
                    if (clientConfig.getRam1Price() != null) {
                        existingClientConfig.setRam1Price(clientConfig.getRam1Price());
                    }
                    if (clientConfig.getRam2Price() != null) {
                        existingClientConfig.setRam2Price(clientConfig.getRam2Price());
                    }
                    if (clientConfig.getPsuPrice() != null) {
                        existingClientConfig.setPsuPrice(clientConfig.getPsuPrice());
                    }
                    if (clientConfig.getComputerCasePrice() != null) {
                        existingClientConfig.setComputerCasePrice(clientConfig.getComputerCasePrice());
                    }
                    if (clientConfig.getVentiradPrice() != null) {
                        existingClientConfig.setVentiradPrice(clientConfig.getVentiradPrice());
                    }
                    if (clientConfig.getHd1Price() != null) {
                        existingClientConfig.setHd1Price(clientConfig.getHd1Price());
                    }
                    if (clientConfig.getHd2Price() != null) {
                        existingClientConfig.setHd2Price(clientConfig.getHd2Price());
                    }

                    return existingClientConfig;
                }
            )
            .map(clientConfigRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clientConfig.getId().toString())
        );
    }

    /**
     * {@code GET  /client-configs} : get all the clientConfigs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clientConfigs in body.
     */
    @GetMapping("/client-configs")
    public List<ClientConfig> getAllClientConfigs() {
        log.debug("REST request to get all ClientConfigs");
        return clientConfigRepository.findAll();
    }

    /**
     * {@code GET  /client-configs/:id} : get the "id" clientConfig.
     *
     * @param id the id of the clientConfig to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clientConfig, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/client-configs/{id}")
    public ResponseEntity<ClientConfig> getClientConfig(@PathVariable Long id) {
        log.debug("REST request to get ClientConfig : {}", id);
        Optional<ClientConfig> clientConfig = clientConfigRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(clientConfig);
    }

    /**
     * {@code DELETE  /client-configs/:id} : delete the "id" clientConfig.
     *
     * @param id the id of the clientConfig to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/client-configs/{id}")
    public ResponseEntity<Void> deleteClientConfig(@PathVariable Long id) {
        log.debug("REST request to delete ClientConfig : {}", id);

        ClientConfig clientConfig = clientConfigRepository.findById(id).get();
        AdminUserDTO user = userService
            .getUserWithAuthorities()
            .map(AdminUserDTO::new)
            .orElseThrow(() -> new ClientConfigResourceException("User could not be found"));
        User owner = clientConfig.getUser();
        boolean isAdmin = user.getAuthorities().stream().anyMatch(a -> a.equals("ROLE_ADMIN"));
        if (!isAdmin && owner != null && user.getId() != owner.getId()) {
            throw new AccessDeniedException("User is not authorized");
        }

        clientConfigRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
