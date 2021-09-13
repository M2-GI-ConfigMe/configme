package com.configme.web.rest;

import com.configme.domain.HardDrive;
import com.configme.repository.HardDriveRepository;
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
 * REST controller for managing {@link com.configme.domain.HardDrive}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class HardDriveResource {

    private final Logger log = LoggerFactory.getLogger(HardDriveResource.class);

    private static final String ENTITY_NAME = "hardDrive";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final HardDriveRepository hardDriveRepository;

    public HardDriveResource(HardDriveRepository hardDriveRepository) {
        this.hardDriveRepository = hardDriveRepository;
    }

    /**
     * {@code POST  /hard-drives} : Create a new hardDrive.
     *
     * @param hardDrive the hardDrive to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new hardDrive, or with status {@code 400 (Bad Request)} if the hardDrive has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/hard-drives")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<HardDrive> createHardDrive(@Valid @RequestBody HardDrive hardDrive) throws URISyntaxException {
        log.debug("REST request to save HardDrive : {}", hardDrive);
        if (hardDrive.getId() != null) {
            throw new BadRequestAlertException("A new hardDrive cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HardDrive result = hardDriveRepository.save(hardDrive);
        return ResponseEntity
            .created(new URI("/api/hard-drives/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /hard-drives/:id} : Updates an existing hardDrive.
     *
     * @param id the id of the hardDrive to save.
     * @param hardDrive the hardDrive to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hardDrive,
     * or with status {@code 400 (Bad Request)} if the hardDrive is not valid,
     * or with status {@code 500 (Internal Server Error)} if the hardDrive couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/hard-drives/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<HardDrive> updateHardDrive(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody HardDrive hardDrive
    ) throws URISyntaxException {
        log.debug("REST request to update HardDrive : {}, {}", id, hardDrive);
        if (hardDrive.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hardDrive.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hardDriveRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        HardDrive result = hardDriveRepository.save(hardDrive);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hardDrive.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /hard-drives/:id} : Partial updates given fields of an existing hardDrive, field will ignore if it is null
     *
     * @param id the id of the hardDrive to save.
     * @param hardDrive the hardDrive to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated hardDrive,
     * or with status {@code 400 (Bad Request)} if the hardDrive is not valid,
     * or with status {@code 404 (Not Found)} if the hardDrive is not found,
     * or with status {@code 500 (Internal Server Error)} if the hardDrive couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/hard-drives/{id}", consumes = "application/merge-patch+json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<HardDrive> partialUpdateHardDrive(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody HardDrive hardDrive
    ) throws URISyntaxException {
        log.debug("REST request to partial update HardDrive partially : {}, {}", id, hardDrive);
        if (hardDrive.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, hardDrive.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!hardDriveRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<HardDrive> result = hardDriveRepository
            .findById(hardDrive.getId())
            .map(
                existingHardDrive -> {
                    if (hardDrive.getCapacity() != null) {
                        existingHardDrive.setCapacity(hardDrive.getCapacity());
                    }
                    if (hardDrive.getSpeedWrite() != null) {
                        existingHardDrive.setSpeedWrite(hardDrive.getSpeedWrite());
                    }
                    if (hardDrive.getSpeedRead() != null) {
                        existingHardDrive.setSpeedRead(hardDrive.getSpeedRead());
                    }
                    if (hardDrive.getType() != null) {
                        existingHardDrive.setType(hardDrive.getType());
                    }

                    return existingHardDrive;
                }
            )
            .map(hardDriveRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, hardDrive.getId().toString())
        );
    }

    /**
     * {@code GET  /hard-drives} : get all the hardDrives.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of hardDrives in body.
     */
    @GetMapping("/hard-drives")
    public List<HardDrive> getAllHardDrives() {
        log.debug("REST request to get all HardDrives");
        return hardDriveRepository.findAll();
    }

    /**
     * {@code GET  /hard-drives/:id} : get the "id" hardDrive.
     *
     * @param id the id of the hardDrive to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hardDrive, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hard-drives/{id}")
    public ResponseEntity<HardDrive> getHardDrive(@PathVariable Long id) {
        log.debug("REST request to get HardDrive : {}", id);
        Optional<HardDrive> hardDrive = hardDriveRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(hardDrive);
    }

    /**
     * {@code DELETE  /hard-drives/:id} : delete the "id" hardDrive.
     *
     * @param id the id of the hardDrive to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/hard-drives/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteHardDrive(@PathVariable Long id) {
        log.debug("REST request to delete HardDrive : {}", id);
        hardDriveRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
