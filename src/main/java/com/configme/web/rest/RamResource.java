package com.configme.web.rest;

import com.configme.domain.Ram;
import com.configme.repository.RamRepository;
import com.configme.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.configme.domain.Ram}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class RamResource {

    private final Logger log = LoggerFactory.getLogger(RamResource.class);

    private static final String ENTITY_NAME = "ram";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RamRepository ramRepository;

    public RamResource(RamRepository ramRepository) {
        this.ramRepository = ramRepository;
    }

    /**
     * {@code POST  /rams} : Create a new ram.
     *
     * @param ram the ram to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ram, or with status {@code 400 (Bad Request)} if the ram has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rams")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Ram> createRam(@Valid @RequestBody Ram ram) throws URISyntaxException {
        log.debug("REST request to save Ram : {}", ram);
        if (ram.getId() != null) {
            throw new BadRequestAlertException("A new ram cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ram result = ramRepository.save(ram);
        return ResponseEntity
            .created(new URI("/api/rams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rams/:id} : Updates an existing ram.
     *
     * @param id the id of the ram to save.
     * @param ram the ram to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ram,
     * or with status {@code 400 (Bad Request)} if the ram is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ram couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rams/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Ram> updateRam(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Ram ram)
        throws URISyntaxException {
        log.debug("REST request to update Ram : {}, {}", id, ram);
        if (ram.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ram.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ramRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Ram result = ramRepository.save(ram);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ram.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rams/:id} : Partial updates given fields of an existing ram, field will ignore if it is null
     *
     * @param id the id of the ram to save.
     * @param ram the ram to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ram,
     * or with status {@code 400 (Bad Request)} if the ram is not valid,
     * or with status {@code 404 (Not Found)} if the ram is not found,
     * or with status {@code 500 (Internal Server Error)} if the ram couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rams/{id}", consumes = "application/merge-patch+json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Ram> partialUpdateRam(@PathVariable(value = "id", required = false) final Long id, @NotNull @RequestBody Ram ram)
        throws URISyntaxException {
        log.debug("REST request to partial update Ram partially : {}, {}", id, ram);
        if (ram.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ram.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ramRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Ram> result = ramRepository
            .findById(ram.getId())
            .map(
                existingRam -> {
                    if (ram.getType() != null) {
                        existingRam.setType(ram.getType());
                    }
                    if (ram.getFrequency() != null) {
                        existingRam.setFrequency(ram.getFrequency());
                    }
                    if (ram.getUnitSize() != null) {
                        existingRam.setUnitSize(ram.getUnitSize());
                    }
                    if (ram.getQuantity() != null) {
                        existingRam.setQuantity(ram.getQuantity());
                    }
                    if (ram.getCas() != null) {
                        existingRam.setCas(ram.getCas());
                    }
                    if (ram.getBrand() != null) {
                        existingRam.setBrand(ram.getBrand());
                    }
                    if (ram.getDiscount() != null) {
                        existingRam.setDiscount(ram.getDiscount());
                    }
                    if (ram.getPrice() != null) {
                        existingRam.setPrice(ram.getPrice());
                    }
                    if (ram.getStock() != null) {
                        existingRam.setStock(ram.getStock());
                    }
                    if (ram.getDesc() != null) {
                        existingRam.setDesc(ram.getDesc());
                    }
                    if (ram.getImg() != null) {
                        existingRam.setImg(ram.getImg());
                    }
                    if (ram.getName() != null) {
                        existingRam.setName(ram.getName());
                    }
                    if (ram.getIsActive() != null) {
                        existingRam.setIsActive(ram.getIsActive());
                    }

                    return existingRam;
                }
            )
            .map(ramRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ram.getId().toString())
        );
    }

    /**
     * {@code GET  /rams} : get all the rams.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rams in body.
     */
    @GetMapping("/rams")
    public List<Ram> getAllRams() {
        log.debug("REST request to get all Rams");
        return ramRepository.findAll();
    }

    /**
     * {@code GET  /rams/:id} : get the "id" ram.
     *
     * @param id the id of the ram to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ram, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rams/{id}")
    public ResponseEntity<Ram> getRam(@PathVariable Long id) {
        log.debug("REST request to get Ram : {}", id);
        Optional<Ram> ram = ramRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ram);
    }

    /**
     * {@code DELETE  /rams/:id} : delete the "id" ram.
     *
     * @param id the id of the ram to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rams/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteRam(@PathVariable Long id) {
        log.debug("REST request to delete Ram : {}", id);
        ramRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
