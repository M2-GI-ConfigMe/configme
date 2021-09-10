package com.configme.web.rest;

import com.configme.domain.Dimension;
import com.configme.repository.DimensionRepository;
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
 * REST controller for managing {@link com.configme.domain.Dimension}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DimensionResource {

    private final Logger log = LoggerFactory.getLogger(DimensionResource.class);

    private static final String ENTITY_NAME = "dimension";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DimensionRepository dimensionRepository;

    public DimensionResource(DimensionRepository dimensionRepository) {
        this.dimensionRepository = dimensionRepository;
    }

    /**
     * {@code POST  /dimensions} : Create a new dimension.
     *
     * @param dimension the dimension to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dimension, or with status {@code 400 (Bad Request)} if the dimension has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dimensions")
    public ResponseEntity<Dimension> createDimension(@Valid @RequestBody Dimension dimension) throws URISyntaxException {
        log.debug("REST request to save Dimension : {}", dimension);
        if (dimension.getId() != null) {
            throw new BadRequestAlertException("A new dimension cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Dimension result = dimensionRepository.save(dimension);
        return ResponseEntity
            .created(new URI("/api/dimensions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dimensions/:id} : Updates an existing dimension.
     *
     * @param id the id of the dimension to save.
     * @param dimension the dimension to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dimension,
     * or with status {@code 400 (Bad Request)} if the dimension is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dimension couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dimensions/{id}")
    public ResponseEntity<Dimension> updateDimension(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Dimension dimension
    ) throws URISyntaxException {
        log.debug("REST request to update Dimension : {}, {}", id, dimension);
        if (dimension.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dimension.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dimensionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Dimension result = dimensionRepository.save(dimension);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dimension.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dimensions/:id} : Partial updates given fields of an existing dimension, field will ignore if it is null
     *
     * @param id the id of the dimension to save.
     * @param dimension the dimension to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dimension,
     * or with status {@code 400 (Bad Request)} if the dimension is not valid,
     * or with status {@code 404 (Not Found)} if the dimension is not found,
     * or with status {@code 500 (Internal Server Error)} if the dimension couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dimensions/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Dimension> partialUpdateDimension(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Dimension dimension
    ) throws URISyntaxException {
        log.debug("REST request to partial update Dimension partially : {}, {}", id, dimension);
        if (dimension.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dimension.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dimensionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Dimension> result = dimensionRepository
            .findById(dimension.getId())
            .map(
                existingDimension -> {
                    if (dimension.getHeight() != null) {
                        existingDimension.setHeight(dimension.getHeight());
                    }
                    if (dimension.getWidth() != null) {
                        existingDimension.setWidth(dimension.getWidth());
                    }
                    if (dimension.getLength() != null) {
                        existingDimension.setLength(dimension.getLength());
                    }

                    return existingDimension;
                }
            )
            .map(dimensionRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dimension.getId().toString())
        );
    }

    /**
     * {@code GET  /dimensions} : get all the dimensions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dimensions in body.
     */
    @GetMapping("/dimensions")
    public List<Dimension> getAllDimensions() {
        log.debug("REST request to get all Dimensions");
        return dimensionRepository.findAll();
    }

    /**
     * {@code GET  /dimensions/:id} : get the "id" dimension.
     *
     * @param id the id of the dimension to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dimension, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dimensions/{id}")
    public ResponseEntity<Dimension> getDimension(@PathVariable Long id) {
        log.debug("REST request to get Dimension : {}", id);
        Optional<Dimension> dimension = dimensionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dimension);
    }

    /**
     * {@code DELETE  /dimensions/:id} : delete the "id" dimension.
     *
     * @param id the id of the dimension to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dimensions/{id}")
    public ResponseEntity<Void> deleteDimension(@PathVariable Long id) {
        log.debug("REST request to delete Dimension : {}", id);
        dimensionRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
