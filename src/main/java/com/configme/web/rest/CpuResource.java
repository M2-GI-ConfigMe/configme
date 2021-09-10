package com.configme.web.rest;

import com.configme.domain.Cpu;
import com.configme.repository.CpuRepository;
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
 * REST controller for managing {@link com.configme.domain.Cpu}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class CpuResource {

    private final Logger log = LoggerFactory.getLogger(CpuResource.class);

    private static final String ENTITY_NAME = "cpu";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CpuRepository cpuRepository;

    public CpuResource(CpuRepository cpuRepository) {
        this.cpuRepository = cpuRepository;
    }

    /**
     * {@code POST  /cpus} : Create a new cpu.
     *
     * @param cpu the cpu to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cpu, or with status {@code 400 (Bad Request)} if the cpu has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cpus")
    public ResponseEntity<Cpu> createCpu(@Valid @RequestBody Cpu cpu) throws URISyntaxException {
        log.debug("REST request to save Cpu : {}", cpu);
        if (cpu.getId() != null) {
            throw new BadRequestAlertException("A new cpu cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Cpu result = cpuRepository.save(cpu);
        return ResponseEntity
            .created(new URI("/api/cpus/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cpus/:id} : Updates an existing cpu.
     *
     * @param id the id of the cpu to save.
     * @param cpu the cpu to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cpu,
     * or with status {@code 400 (Bad Request)} if the cpu is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cpu couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cpus/{id}")
    public ResponseEntity<Cpu> updateCpu(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Cpu cpu)
        throws URISyntaxException {
        log.debug("REST request to update Cpu : {}, {}", id, cpu);
        if (cpu.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cpu.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cpuRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Cpu result = cpuRepository.save(cpu);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cpu.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cpus/:id} : Partial updates given fields of an existing cpu, field will ignore if it is null
     *
     * @param id the id of the cpu to save.
     * @param cpu the cpu to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cpu,
     * or with status {@code 400 (Bad Request)} if the cpu is not valid,
     * or with status {@code 404 (Not Found)} if the cpu is not found,
     * or with status {@code 500 (Internal Server Error)} if the cpu couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cpus/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Cpu> partialUpdateCpu(@PathVariable(value = "id", required = false) final Long id, @NotNull @RequestBody Cpu cpu)
        throws URISyntaxException {
        log.debug("REST request to partial update Cpu partially : {}, {}", id, cpu);
        if (cpu.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cpu.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cpuRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Cpu> result = cpuRepository
            .findById(cpu.getId())
            .map(
                existingCpu -> {
                    if (cpu.getFrequency() != null) {
                        existingCpu.setFrequency(cpu.getFrequency());
                    }
                    if (cpu.getCacheL1() != null) {
                        existingCpu.setCacheL1(cpu.getCacheL1());
                    }
                    if (cpu.getCacheL2() != null) {
                        existingCpu.setCacheL2(cpu.getCacheL2());
                    }
                    if (cpu.getCacheL3() != null) {
                        existingCpu.setCacheL3(cpu.getCacheL3());
                    }
                    if (cpu.getNbHeart() != null) {
                        existingCpu.setNbHeart(cpu.getNbHeart());
                    }
                    if (cpu.getNbThread() != null) {
                        existingCpu.setNbThread(cpu.getNbThread());
                    }
                    if (cpu.getHasVentirad() != null) {
                        existingCpu.setHasVentirad(cpu.getHasVentirad());
                    }
                    if (cpu.getSocketType() != null) {
                        existingCpu.setSocketType(cpu.getSocketType());
                    }
                    if (cpu.getLithography() != null) {
                        existingCpu.setLithography(cpu.getLithography());
                    }
                    if (cpu.getRamFrequencyMax() != null) {
                        existingCpu.setRamFrequencyMax(cpu.getRamFrequencyMax());
                    }
                    if (cpu.getConsumption() != null) {
                        existingCpu.setConsumption(cpu.getConsumption());
                    }
                    if (cpu.getHasGpu() != null) {
                        existingCpu.setHasGpu(cpu.getHasGpu());
                    }

                    return existingCpu;
                }
            )
            .map(cpuRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cpu.getId().toString())
        );
    }

    /**
     * {@code GET  /cpus} : get all the cpus.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cpus in body.
     */
    @GetMapping("/cpus")
    public List<Cpu> getAllCpus() {
        log.debug("REST request to get all Cpus");
        return cpuRepository.findAll();
    }

    /**
     * {@code GET  /cpus/:id} : get the "id" cpu.
     *
     * @param id the id of the cpu to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cpu, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cpus/{id}")
    public ResponseEntity<Cpu> getCpu(@PathVariable Long id) {
        log.debug("REST request to get Cpu : {}", id);
        Optional<Cpu> cpu = cpuRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(cpu);
    }

    /**
     * {@code DELETE  /cpus/:id} : delete the "id" cpu.
     *
     * @param id the id of the cpu to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cpus/{id}")
    public ResponseEntity<Void> deleteCpu(@PathVariable Long id) {
        log.debug("REST request to delete Cpu : {}", id);
        cpuRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
