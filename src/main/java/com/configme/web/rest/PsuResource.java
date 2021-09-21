package com.configme.web.rest;

import com.configme.domain.Psu;
import com.configme.repository.PsuRepository;
import com.configme.service.ImageService;
import com.configme.web.rest.errors.BadRequestAlertException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.configme.domain.Psu}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PsuResource {

    private final Logger log = LoggerFactory.getLogger(PsuResource.class);

    private static final String ENTITY_NAME = "psu";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PsuRepository psuRepository;

    public PsuResource(PsuRepository psuRepository) {
        this.psuRepository = psuRepository;
    }

    @Autowired
    ImageService imageService;

    /**
     * {@code POST  /psus} : Create a new psu.
     *
     * @param psu the psu to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new psu, or with status {@code 400 (Bad Request)} if the psu has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/psus")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Psu> createPsu(@Valid @RequestBody Psu psu) throws URISyntaxException {
        log.debug("REST request to save Psu : {}", psu);
        if (psu.getId() != null) {
            throw new BadRequestAlertException("A new psu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Psu result = psuRepository.save(psu);
        return ResponseEntity
            .created(new URI("/api/psus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /psus/:id} : Updates an existing psu.
     *
     * @param id the id of the psu to save.
     * @param psu the psu to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated psu,
     * or with status {@code 400 (Bad Request)} if the psu is not valid,
     * or with status {@code 500 (Internal Server Error)} if the psu couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/psus/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Psu> updatePsu(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Psu psu)
        throws URISyntaxException {
        log.debug("REST request to update Psu : {}, {}", id, psu);
        if (psu.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, psu.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!psuRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Psu result = psuRepository.save(psu);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, psu.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /psus/:id} : Partial updates given fields of an existing psu, field will ignore if it is null
     *
     * @param id the id of the psu to save.
     * @param psu the psu to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated psu,
     * or with status {@code 400 (Bad Request)} if the psu is not valid,
     * or with status {@code 404 (Not Found)} if the psu is not found,
     * or with status {@code 500 (Internal Server Error)} if the psu couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/psus/{id}", consumes = "application/merge-patch+json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Psu> partialUpdatePsu(@PathVariable(value = "id", required = false) final Long id, @NotNull @RequestBody Psu psu)
        throws URISyntaxException {
        log.debug("REST request to partial update Psu partially : {}, {}", id, psu);
        if (psu.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, psu.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!psuRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Psu> result = psuRepository
            .findById(psu.getId())
            .map(
                existingPsu -> {
                    if (psu.getPower() != null) {
                        existingPsu.setPower(psu.getPower());
                    }
                    if (psu.getCertification() != null) {
                        existingPsu.setCertification(psu.getCertification());
                    }
                    if (psu.getModularity() != null) {
                        existingPsu.setModularity(psu.getModularity());
                    }
                    if (psu.getNbSata() != null) {
                        existingPsu.setNbSata(psu.getNbSata());
                    }
                    if (psu.getNbPciE() != null) {
                        existingPsu.setNbPciE(psu.getNbPciE());
                    }
                    if (psu.getOutputs() != null) {
                        existingPsu.setOutputs(psu.getOutputs());
                    }
                    if (psu.getBrand() != null) {
                        existingPsu.setBrand(psu.getBrand());
                    }
                    if (psu.getDiscount() != null) {
                        existingPsu.setDiscount(psu.getDiscount());
                    }
                    if (psu.getPrice() != null) {
                        existingPsu.setPrice(psu.getPrice());
                    }
                    if (psu.getStock() != null) {
                        existingPsu.setStock(psu.getStock());
                    }
                    if (psu.getDesc() != null) {
                        existingPsu.setDesc(psu.getDesc());
                    }
                    if (psu.getImg() != null) {
                        existingPsu.setImg(psu.getImg());
                    }
                    if (psu.getName() != null) {
                        existingPsu.setName(psu.getName());
                    }
                    if (psu.getIsActive() != null) {
                        existingPsu.setIsActive(psu.getIsActive());
                    }

                    return existingPsu;
                }
            )
            .map(psuRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, psu.getId().toString())
        );
    }

    /**
     * {@code GET  /psus} : get all the psus.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of psus in body.
     */
    @GetMapping("/psus")
    public List<Psu> getAllPsus() {
        log.debug("REST request to get all Psus");
        return psuRepository.findAll();
    }

    /**
     * {@code GET  /psus/:id} : get the "id" psu.
     *
     * @param id the id of the psu to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the psu, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/psus/{id}")
    public ResponseEntity<Psu> getPsu(@PathVariable Long id) {
        log.debug("REST request to get Psu : {}", id);
        Optional<Psu> psu = psuRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(psu);
    }

    /**
     * {@code DELETE  /psus/:id} : delete the "id" psu.
     *
     * @param id the id of the psu to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/psus/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deletePsu(@PathVariable Long id) {
        log.debug("REST request to delete Psu : {}", id);
        psuRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
