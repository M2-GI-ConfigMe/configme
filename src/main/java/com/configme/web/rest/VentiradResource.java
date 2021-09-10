package com.configme.web.rest;

import com.configme.domain.Ventirad;
import com.configme.repository.VentiradRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.configme.domain.Ventirad}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class VentiradResource {

    private final Logger log = LoggerFactory.getLogger(VentiradResource.class);

    private static final String ENTITY_NAME = "ventirad";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VentiradRepository ventiradRepository;

    public VentiradResource(VentiradRepository ventiradRepository) {
        this.ventiradRepository = ventiradRepository;
    }

    /**
     * {@code POST  /ventirads} : Create a new ventirad.
     *
     * @param ventirad the ventirad to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ventirad, or with status {@code 400 (Bad Request)} if the ventirad has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ventirads")
    public ResponseEntity<Ventirad> createVentirad(@Valid @RequestBody Ventirad ventirad) throws URISyntaxException {
        log.debug("REST request to save Ventirad : {}", ventirad);
        if (ventirad.getId() != null) {
            throw new BadRequestAlertException("A new ventirad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ventirad result = ventiradRepository.save(ventirad);
        return ResponseEntity
            .created(new URI("/api/ventirads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ventirads/:id} : Updates an existing ventirad.
     *
     * @param id the id of the ventirad to save.
     * @param ventirad the ventirad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ventirad,
     * or with status {@code 400 (Bad Request)} if the ventirad is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ventirad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ventirads/{id}")
    public ResponseEntity<Ventirad> updateVentirad(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Ventirad ventirad
    ) throws URISyntaxException {
        log.debug("REST request to update Ventirad : {}, {}", id, ventirad);
        if (ventirad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ventirad.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ventiradRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Ventirad result = ventiradRepository.save(ventirad);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ventirad.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ventirads/:id} : Partial updates given fields of an existing ventirad, field will ignore if it is null
     *
     * @param id the id of the ventirad to save.
     * @param ventirad the ventirad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ventirad,
     * or with status {@code 400 (Bad Request)} if the ventirad is not valid,
     * or with status {@code 404 (Not Found)} if the ventirad is not found,
     * or with status {@code 500 (Internal Server Error)} if the ventirad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ventirads/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Ventirad> partialUpdateVentirad(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Ventirad ventirad
    ) throws URISyntaxException {
        log.debug("REST request to partial update Ventirad partially : {}, {}", id, ventirad);
        if (ventirad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, ventirad.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!ventiradRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Ventirad> result = ventiradRepository
            .findById(ventirad.getId())
            .map(
                existingVentirad -> {
                    if (ventirad.getRangeFanSpeed() != null) {
                        existingVentirad.setRangeFanSpeed(ventirad.getRangeFanSpeed());
                    }
                    if (ventirad.getNoise() != null) {
                        existingVentirad.setNoise(ventirad.getNoise());
                    }
                    if (ventirad.getHasThermalPaste() != null) {
                        existingVentirad.setHasThermalPaste(ventirad.getHasThermalPaste());
                    }

                    return existingVentirad;
                }
            )
            .map(ventiradRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ventirad.getId().toString())
        );
    }

    /**
     * {@code GET  /ventirads} : get all the ventirads.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ventirads in body.
     */
    @GetMapping("/ventirads")
    public List<Ventirad> getAllVentirads() {
        log.debug("REST request to get all Ventirads");
        return ventiradRepository.findAll();
    }

    /**
     * {@code GET  /ventirads/:id} : get the "id" ventirad.
     *
     * @param id the id of the ventirad to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ventirad, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ventirads/{id}")
    public ResponseEntity<Ventirad> getVentirad(@PathVariable Long id) {
        log.debug("REST request to get Ventirad : {}", id);
        Optional<Ventirad> ventirad = ventiradRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ventirad);
    }

    /**
     * {@code DELETE  /ventirads/:id} : delete the "id" ventirad.
     *
     * @param id the id of the ventirad to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ventirads/{id}")
    public ResponseEntity<Void> deleteVentirad(@PathVariable Long id) {
        log.debug("REST request to delete Ventirad : {}", id);
        ventiradRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
