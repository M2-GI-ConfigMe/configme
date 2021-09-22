package com.configme.web.rest;

import com.configme.domain.*;
import com.configme.domain.ComputerCase;
import com.configme.repository.*;
import com.configme.repository.ComputerCaseRepository;
import com.configme.service.ImageService;
import com.configme.service.UserService;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.configme.domain.ComputerCase}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ComputerCaseResource {

    private final Logger log = LoggerFactory.getLogger(ComputerCaseResource.class);

    private static final String ENTITY_NAME = "computerCase";
    private final MbeRepository mbeRepository;
    private final GpuRepository gpuRepository;
    private final VentiradRepository ventiradRepository;
    private final UserService userService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComputerCaseRepository computerCaseRepository;

    public ComputerCaseResource(
        ComputerCaseRepository computerCaseRepository,
        MbeRepository mbeRepository,
        GpuRepository gpuRepository,
        VentiradRepository ventiradRepository,
        UserService userService
    ) {
        this.userService = userService;
        this.computerCaseRepository = computerCaseRepository;
        this.mbeRepository = mbeRepository;
        this.gpuRepository = gpuRepository;
        this.ventiradRepository = ventiradRepository;
    }

    @Autowired
    ImageService imageService;

    /**
     * {@code POST  /computer-cases} : Create a new computerCase.
     *
     * @param computerCase the computerCase to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new computerCase, or with status {@code 400 (Bad Request)} if the computerCase has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/computer-cases")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ComputerCase> createComputerCase(@Valid @RequestBody ComputerCase computerCase) throws URISyntaxException {
        log.debug("REST request to save ComputerCase : {}", computerCase);
        if (computerCase.getId() != null) {
            throw new BadRequestAlertException("A new computerCase cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ComputerCase result = computerCaseRepository.save(computerCase);
        return ResponseEntity
            .created(new URI("/api/computer-cases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /computer-cases/:id} : Updates an existing computerCase.
     *
     * @param id the id of the computerCase to save.
     * @param computerCase the computerCase to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated computerCase,
     * or with status {@code 400 (Bad Request)} if the computerCase is not valid,
     * or with status {@code 500 (Internal Server Error)} if the computerCase couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/computer-cases/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ComputerCase> updateComputerCase(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ComputerCase computerCase
    ) throws URISyntaxException {
        log.debug("REST request to update ComputerCase : {}, {}", id, computerCase);
        if (computerCase.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, computerCase.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!computerCaseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ComputerCase result = computerCaseRepository.save(computerCase);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, computerCase.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /computer-cases/:id} : Partial updates given fields of an existing computerCase, field will ignore if it is null
     *
     * @param id the id of the computerCase to save.
     * @param computerCase the computerCase to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated computerCase,
     * or with status {@code 400 (Bad Request)} if the computerCase is not valid,
     * or with status {@code 404 (Not Found)} if the computerCase is not found,
     * or with status {@code 500 (Internal Server Error)} if the computerCase couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/computer-cases/{id}", consumes = "application/merge-patch+json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ComputerCase> partialUpdateComputerCase(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ComputerCase computerCase
    ) throws URISyntaxException {
        log.debug("REST request to partial update ComputerCase partially : {}, {}", id, computerCase);
        if (computerCase.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, computerCase.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!computerCaseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ComputerCase> result = computerCaseRepository
            .findById(computerCase.getId())
            .map(
                existingComputerCase -> {
                    if (computerCase.getType() != null) {
                        existingComputerCase.setType(computerCase.getType());
                    }
                    if (computerCase.getFormats() != null) {
                        existingComputerCase.setFormats(computerCase.getFormats());
                    }
                    if (computerCase.getSizeMaxGpu() != null) {
                        existingComputerCase.setSizeMaxGpu(computerCase.getSizeMaxGpu());
                    }
                    if (computerCase.getSizeMaxVentirad() != null) {
                        existingComputerCase.setSizeMaxVentirad(computerCase.getSizeMaxVentirad());
                    }
                    if (computerCase.getSizeMaxPsu() != null) {
                        existingComputerCase.setSizeMaxPsu(computerCase.getSizeMaxPsu());
                    }
                    if (computerCase.getHardDriveSlots() != null) {
                        existingComputerCase.setHardDriveSlots(computerCase.getHardDriveSlots());
                    }
                    if (computerCase.getFrontPanelOutputs() != null) {
                        existingComputerCase.setFrontPanelOutputs(computerCase.getFrontPanelOutputs());
                    }
                    if (computerCase.getFanIncluded() != null) {
                        existingComputerCase.setFanIncluded(computerCase.getFanIncluded());
                    }
                    if (computerCase.getFanSlotsAvailable() != null) {
                        existingComputerCase.setFanSlotsAvailable(computerCase.getFanSlotsAvailable());
                    }
                    if (computerCase.getWatercoolingCompatibility() != null) {
                        existingComputerCase.setWatercoolingCompatibility(computerCase.getWatercoolingCompatibility());
                    }
                    if (computerCase.getDimension() != null) {
                        existingComputerCase.setDimension(computerCase.getDimension());
                    }
                    if (computerCase.getBrand() != null) {
                        existingComputerCase.setBrand(computerCase.getBrand());
                    }
                    if (computerCase.getDiscount() != null) {
                        existingComputerCase.setDiscount(computerCase.getDiscount());
                    }
                    if (computerCase.getPrice() != null) {
                        existingComputerCase.setPrice(computerCase.getPrice());
                    }
                    if (computerCase.getStock() != null) {
                        existingComputerCase.setStock(computerCase.getStock());
                    }
                    if (computerCase.getDesc() != null) {
                        existingComputerCase.setDesc(computerCase.getDesc());
                    }
                    if (computerCase.getImg() != null) {
                        existingComputerCase.setImg(computerCase.getImg());
                    }
                    if (computerCase.getName() != null) {
                        existingComputerCase.setName(computerCase.getName());
                    }
                    if (computerCase.getIsActive() != null) {
                        existingComputerCase.setIsActive(computerCase.getIsActive());
                    }

                    return existingComputerCase;
                }
            )
            .map(computerCaseRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, computerCase.getId().toString())
        );
    }

    /**
     * {@code GET  /computer-cases} : get all the computerCases.
     *
     * @param page number of the page to get
     * @param size number of n-uplets per page
     * @param sortBy column to sort by
     * @param sortDesc direction of sort
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of computerCases in body.
     */
    @GetMapping("/computer-cases")
    public Page<ComputerCase> getAllComputerCases(
        @RequestParam(name = "page", defaultValue = "1") int page,
        @RequestParam(name = "itemsPerPage", defaultValue = "15") int size,
        @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
        @RequestParam(name = "sortDesc", defaultValue = "true") boolean sortDesc,
        @RequestParam(name = "mbeId", required = false) Long mbeId,
        @RequestParam(name = "ventiradId", required = false) Long ventiradId,
        @RequestParam(name = "gpuId", required = false) Long gpuId,
        @RequestParam(name = "name", required = false, defaultValue = "") String name
    ) {
        User user = null;
        if (this.userService.getUserWithAuthorities().isPresent()) user = this.userService.getUserWithAuthorities().get();

        Mbe mbe = null;
        if (mbeId != null && this.mbeRepository.existsById(mbeId)) mbe = this.mbeRepository.findById(mbeId).get();

        Ventirad ventirad = null;
        if (ventiradId != null && this.ventiradRepository.existsById(ventiradId)) ventirad =
            this.ventiradRepository.findById(ventiradId).get();

        Gpu gpu = null;
        if (gpuId != null && this.gpuRepository.existsById(gpuId)) gpu = this.gpuRepository.findById(gpuId).get();

        log.debug("REST request to get all Mbes");
        return computerCaseRepository.findByCompatibility(
            user,
            mbe,
            gpu,
            ventirad,
            name,
            PageRequest.of(page - 1, size, Sort.by(sortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy))
        );
    }

    /**
     * {@code GET  /computer-cases/:id} : get the "id" computerCase.
     *
     * @param id the id of the computerCase to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the computerCase, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/computer-cases/{id}")
    public ResponseEntity<ComputerCase> getComputerCase(@PathVariable Long id) {
        log.debug("REST request to get ComputerCase : {}", id);
        Optional<ComputerCase> computerCase = computerCaseRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(computerCase);
    }

    /**
     * {@code DELETE  /computer-cases/:id} : delete the "id" computerCase.
     *
     * @param id the id of the computerCase to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/computer-cases/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteComputerCase(@PathVariable Long id) {
        log.debug("REST request to delete ComputerCase : {}", id);
        computerCaseRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
