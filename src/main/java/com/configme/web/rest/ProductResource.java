package com.configme.web.rest;

import com.configme.domain.Cpu;
import com.configme.domain.Product;
import com.configme.repository.CpuRepository;
import com.configme.repository.ProductRepository;
import com.configme.service.ImageService;
import com.configme.web.rest.errors.BadRequestAlertException;
import java.io.IOException;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.configme.domain.Cpu}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProductResource {

    private final Logger log = LoggerFactory.getLogger(CpuResource.class);

    private static final String ENTITY_NAME = "cpu";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductRepository productRepository;

    public ProductResource(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    ImageService imageService;

    /**
     * {@code PUT  /cpus/id/image} : Updates an existing cpu image
     * @param id the cpu id.
     * @param file the image file to set.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cpu,
     * or with status {@code 400 (Bad Request)} if the cpu is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cpu image couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     * @throws IOException if temp file creation failed in imageService.
     */
    @PutMapping(path = "/products/{id}/image", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Product> updateCpuImg(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestParam(value = "file") MultipartFile file
    ) throws URISyntaxException, IOException {
        log.debug("REST request to upload Product image : {}, {}", id, file);

        if (!productRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", Product.class.toString(), "idnotfound");
        }

        Product product = productRepository.getOne(id);
        imageService.uploadImage(product, file);
        Product result = productRepository.save(product);

        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, "filename")).body(result);
    }
}
