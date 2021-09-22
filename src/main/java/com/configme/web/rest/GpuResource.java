package com.configme.web.rest;

import com.configme.domain.ComputerCase;
import com.configme.domain.Gpu;
import com.configme.domain.Mbe;
import com.configme.domain.User;
import com.configme.repository.ComputerCaseRepository;
import com.configme.repository.GpuRepository;
import com.configme.repository.MbeRepository;
import com.configme.service.ImageService;
import com.configme.service.UserService;
import com.configme.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.configme.domain.Gpu}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GpuResource {

    private final Logger log = LoggerFactory.getLogger(GpuResource.class);

    private static final String ENTITY_NAME = "gpu";
    private final ComputerCaseRepository computerCaseRepository;
    private final MbeRepository mbeRepository;
    private final UserService userService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GpuRepository gpuRepository;

    public GpuResource(
        GpuRepository gpuRepository,
        MbeRepository mbeRepository,
        ComputerCaseRepository computerCaseRepository,
        UserService userService
    ) {
        this.userService = userService;
        this.gpuRepository = gpuRepository;
        this.computerCaseRepository = computerCaseRepository;
        this.mbeRepository = mbeRepository;
    }

    @Autowired
    ImageService imageService;

    /**
     * {@code POST  /gpus} : Create a new gpu.
     *
     * @param gpu the gpu to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gpu, or with status {@code 400 (Bad Request)} if the gpu has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gpus")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Gpu> createGpu(@Valid @RequestBody Gpu gpu) throws URISyntaxException {
        log.debug("REST request to save Gpu : {}", gpu);
        if (gpu.getId() != null) {
            throw new BadRequestAlertException("A new gpu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Gpu result = gpuRepository.save(gpu);
        return ResponseEntity
            .created(new URI("/api/gpus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gpus/:id} : Updates an existing gpu.
     *
     * @param id the id of the gpu to save.
     * @param gpu the gpu to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gpu,
     * or with status {@code 400 (Bad Request)} if the gpu is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gpu couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gpus/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Gpu> updateGpu(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Gpu gpu)
        throws URISyntaxException {
        log.debug("REST request to update Gpu : {}, {}", id, gpu);
        if (gpu.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gpu.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gpuRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Gpu result = gpuRepository.save(gpu);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gpu.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /gpus/:id} : Partial updates given fields of an existing gpu, field will ignore if it is null
     *
     * @param id the id of the gpu to save.
     * @param gpu the gpu to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gpu,
     * or with status {@code 400 (Bad Request)} if the gpu is not valid,
     * or with status {@code 404 (Not Found)} if the gpu is not found,
     * or with status {@code 500 (Internal Server Error)} if the gpu couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/gpus/{id}", consumes = "application/merge-patch+json")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Gpu> partialUpdateGpu(@PathVariable(value = "id", required = false) final Long id, @NotNull @RequestBody Gpu gpu)
        throws URISyntaxException {
        log.debug("REST request to partial update Gpu partially : {}, {}", id, gpu);
        if (gpu.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gpu.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gpuRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Gpu> result = gpuRepository
            .findById(gpu.getId())
            .map(
                existingGpu -> {
                    if (gpu.getFrequency() != null) {
                        existingGpu.setFrequency(gpu.getFrequency());
                    }
                    if (gpu.getMemory() != null) {
                        existingGpu.setMemory(gpu.getMemory());
                    }
                    if (gpu.getConsumption() != null) {
                        existingGpu.setConsumption(gpu.getConsumption());
                    }
                    if (gpu.getClockSpeed() != null) {
                        existingGpu.setClockSpeed(gpu.getClockSpeed());
                    }
                    if (gpu.getLithography() != null) {
                        existingGpu.setLithography(gpu.getLithography());
                    }
                    if (gpu.getOutput() != null) {
                        existingGpu.setOutput(gpu.getOutput());
                    }
                    if (gpu.getInputPower() != null) {
                        existingGpu.setInputPower(gpu.getInputPower());
                    }
                    if (gpu.getBus() != null) {
                        existingGpu.setBus(gpu.getBus());
                    }
                    if (gpu.getDimension() != null) {
                        existingGpu.setDimension(gpu.getDimension());
                    }
                    if (gpu.getBrand() != null) {
                        existingGpu.setBrand(gpu.getBrand());
                    }
                    if (gpu.getDiscount() != null) {
                        existingGpu.setDiscount(gpu.getDiscount());
                    }
                    if (gpu.getPrice() != null) {
                        existingGpu.setPrice(gpu.getPrice());
                    }
                    if (gpu.getStock() != null) {
                        existingGpu.setStock(gpu.getStock());
                    }
                    if (gpu.getDesc() != null) {
                        existingGpu.setDesc(gpu.getDesc());
                    }
                    if (gpu.getImg() != null) {
                        existingGpu.setImg(gpu.getImg());
                    }
                    if (gpu.getName() != null) {
                        existingGpu.setName(gpu.getName());
                    }
                    if (gpu.getIsActive() != null) {
                        existingGpu.setIsActive(gpu.getIsActive());
                    }

                    return existingGpu;
                }
            )
            .map(gpuRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gpu.getId().toString())
        );
    }

    /**
     * {@code GET  /gpus} : get all the gpus.
     *
     * @param page number of the page to get
     * @param size number of n-uplets per page
     * @param sortBy column to sort by
     * @param sortDesc direction of sort
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gpus in body.
     */
    @GetMapping("/gpus")
    public Page<Gpu> getAllGpus(
        @RequestParam(name = "page", defaultValue = "1") int page,
        @RequestParam(name = "itemsPerPage", defaultValue = "15") int size,
        @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
        @RequestParam(name = "sortDesc", defaultValue = "true") boolean sortDesc,
        @RequestParam(name = "computerCaseId", required = false) Long computerCaseId,
        @RequestParam(name = "name", required = false, defaultValue = "") String name
    ) {
        log.debug("REST request to get all Mbes");

        User user = null;
        if (this.userService.getUserWithAuthorities().isPresent()) user = this.userService.getUserWithAuthorities().get();

        ComputerCase computerCase = null;
        if (computerCaseId != null && this.computerCaseRepository.existsById(computerCaseId)) computerCase =
            this.computerCaseRepository.getOne(computerCaseId);

        return gpuRepository.findByCompatibility(
            user,
            computerCase,
            name,
            PageRequest.of(page - 1, size, Sort.by(sortDesc ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy))
        );
    }

    /**
     * {@code GET  /gpus/:id} : get the "id" gpu.
     *
     * @param id the id of the gpu to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gpu, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gpus/{id}")
    public ResponseEntity<Gpu> getGpu(@PathVariable Long id) {
        log.debug("REST request to get Gpu : {}", id);

        Optional<Gpu> gpu = gpuRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gpu);
    }

    /**
     * {@code DELETE  /gpus/:id} : delete the "id" gpu.
     *
     * @param id the id of the gpu to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gpus/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteGpu(@PathVariable Long id) {
        log.debug("REST request to delete Gpu : {}", id);
        gpuRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
