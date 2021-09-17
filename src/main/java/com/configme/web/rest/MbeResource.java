package com.configme.web.rest;

import com.configme.domain.Mbe;
import com.configme.repository.MbeRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.configme.domain.Mbe}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MbeResource {

    private final Logger log = LoggerFactory.getLogger(MbeResource.class);

    private static final String ENTITY_NAME = "mbe";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MbeRepository mbeRepository;

    public MbeResource(MbeRepository mbeRepository) {
        this.mbeRepository = mbeRepository;
    }

    /**
     * {@code POST  /mbes} : Create a new mbe.
     *
     * @param mbe the mbe to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mbe, or with status {@code 400 (Bad Request)} if the mbe has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mbes")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Mbe> createMbe(@Valid @RequestBody Mbe mbe) throws URISyntaxException {
        log.debug("REST request to save Mbe : {}", mbe);
        if (mbe.getId() != null) {
            throw new BadRequestAlertException("A new mbe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Mbe result = mbeRepository.save(mbe);
        return ResponseEntity
            .created(new URI("/api/mbes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mbes/:id} : Updates an existing mbe.
     *
     * @param id the id of the mbe to save.
     * @param mbe the mbe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mbe,
     * or with status {@code 400 (Bad Request)} if the mbe is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mbe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mbes/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Mbe> updateMbe(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Mbe mbe)
        throws URISyntaxException {
        log.debug("REST request to update Mbe : {}, {}", id, mbe);
        if (mbe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mbe.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mbeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Mbe result = mbeRepository.save(mbe);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mbe.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mbes/:id} : Partial updates given fields of an existing mbe, field will ignore if it is null
     *
     * @param id the id of the mbe to save.
     * @param mbe the mbe to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mbe,
     * or with status {@code 400 (Bad Request)} if the mbe is not valid,
     * or with status {@code 404 (Not Found)} if the mbe is not found,
     * or with status {@code 500 (Internal Server Error)} if the mbe couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/mbes/{id}", consumes = "application/merge-patch+json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Mbe> partialUpdateMbe(@PathVariable(value = "id", required = false) final Long id, @NotNull @RequestBody Mbe mbe)
        throws URISyntaxException {
        log.debug("REST request to partial update Mbe partially : {}, {}", id, mbe);
        if (mbe.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mbe.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mbeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Mbe> result = mbeRepository
            .findById(mbe.getId())
            .map(
                existingMbe -> {
                    if (mbe.getSocketCpu() != null) {
                        existingMbe.setSocketCpu(mbe.getSocketCpu());
                    }
                    if (mbe.getRamType() != null) {
                        existingMbe.setRamType(mbe.getRamType());
                    }
                    if (mbe.getRamFrequencyMax() != null) {
                        existingMbe.setRamFrequencyMax(mbe.getRamFrequencyMax());
                    }
                    if (mbe.getRamSizeMax() != null) {
                        existingMbe.setRamSizeMax(mbe.getRamSizeMax());
                    }
                    if (mbe.getPciOutputs() != null) {
                        existingMbe.setPciOutputs(mbe.getPciOutputs());
                    }
                    if (mbe.getDisplayOutput() != null) {
                        existingMbe.setDisplayOutput(mbe.getDisplayOutput());
                    }
                    if (mbe.getStorageOutput() != null) {
                        existingMbe.setStorageOutput(mbe.getStorageOutput());
                    }
                    if (mbe.getInsideIO() != null) {
                        existingMbe.setInsideIO(mbe.getInsideIO());
                    }
                    if (mbe.getBackPanelOutput() != null) {
                        existingMbe.setBackPanelOutput(mbe.getBackPanelOutput());
                    }
                    if (mbe.getBios() != null) {
                        existingMbe.setBios(mbe.getBios());
                    }
                    if (mbe.getFormat() != null) {
                        existingMbe.setFormat(mbe.getFormat());
                    }
                    if (mbe.getBrand() != null) {
                        existingMbe.setBrand(mbe.getBrand());
                    }
                    if (mbe.getDiscount() != null) {
                        existingMbe.setDiscount(mbe.getDiscount());
                    }
                    if (mbe.getPrice() != null) {
                        existingMbe.setPrice(mbe.getPrice());
                    }
                    if (mbe.getStock() != null) {
                        existingMbe.setStock(mbe.getStock());
                    }
                    if (mbe.getDesc() != null) {
                        existingMbe.setDesc(mbe.getDesc());
                    }
                    if (mbe.getImg() != null) {
                        existingMbe.setImg(mbe.getImg());
                    }
                    if (mbe.getName() != null) {
                        existingMbe.setName(mbe.getName());
                    }
                    if (mbe.getIsActive() != null) {
                        existingMbe.setIsActive(mbe.getIsActive());
                    }

                    return existingMbe;
                }
            )
            .map(mbeRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mbe.getId().toString())
        );
    }

    /**
     * {@code GET  /mbes} : get all the mbes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mbes in body.
     */
    @GetMapping("/mbes")
    public Page<Mbe> getAllMbes(
        @RequestParam(name = "page", defaultValue = "1") int page,
        @RequestParam(name = "itemsPerPage", defaultValue = "15") int size,
        @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
        @RequestParam(name = "sortDesc", defaultValue = "true") boolean sortDesc
    ) {
        log.debug("REST request to get all Mbes");
        return mbeRepository.findAll(PageRequest.of(page - 1, size, Sort.by(sortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy)));
    }

    /**
     * {@code GET  /mbes/:id} : get the "id" mbe.
     *
     * @param id the id of the mbe to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mbe, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mbes/{id}")
    public ResponseEntity<Mbe> getMbe(@PathVariable Long id) {
        log.debug("REST request to get Mbe : {}", id);
        Optional<Mbe> mbe = mbeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mbe);
    }

    /**
     * {@code DELETE  /mbes/:id} : delete the "id" mbe.
     *
     * @param id the id of the mbe to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mbes/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteMbe(@PathVariable Long id) {
        log.debug("REST request to delete Mbe : {}", id);
        mbeRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
