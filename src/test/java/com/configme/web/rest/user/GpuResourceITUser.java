package com.configme.web.rest.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.configme.IntegrationTest;
import com.configme.domain.Dimension;
import com.configme.domain.Gpu;
import com.configme.domain.enumeration.BusType;
import com.configme.repository.GpuRepository;
import com.configme.web.rest.GpuResource;
import com.configme.web.rest.ProductResourceIT;
import com.configme.web.rest.TestUtil;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GpuResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GpuResourceITUser implements ProductResourceIT {

    private static final Float DEFAULT_FREQUENCY = 1F;
    private static final Float UPDATED_FREQUENCY = 2F;

    private static final Integer DEFAULT_MEMORY = 1;
    private static final Integer UPDATED_MEMORY = 2;

    private static final Integer DEFAULT_CONSUMPTION = 1;
    private static final Integer UPDATED_CONSUMPTION = 2;

    private static final Integer DEFAULT_CLOCK_SPEED = 1;
    private static final Integer UPDATED_CLOCK_SPEED = 2;

    private static final Integer DEFAULT_LITHOGRAPHY = 1;
    private static final Integer UPDATED_LITHOGRAPHY = 2;

    private static final String DEFAULT_OUTPUT = "AAAAAAAAAA";
    private static final String UPDATED_OUTPUT = "BBBBBBBBBB";

    private static final String DEFAULT_INPUT_POWER = "AAAAAAAAAA";
    private static final String UPDATED_INPUT_POWER = "BBBBBBBBBB";

    private static final BusType DEFAULT_BUS = BusType.PCI;
    private static final BusType UPDATED_BUS = BusType.AGP;

    private static final Dimension DEFAULT_DIMENSION = new Dimension(51, 21, 26);
    private static final Dimension UPDATED_DIMENSION = new Dimension(75, 250, 3950);

    private static final String ENTITY_API_URL = "/api/gpus";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GpuRepository gpuRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGpuMockMvc;

    private Gpu gpu;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gpu createEntity(EntityManager em) {
        Gpu gpu = new Gpu()
            .frequency(DEFAULT_FREQUENCY)
            .memory(DEFAULT_MEMORY)
            .consumption(DEFAULT_CONSUMPTION)
            .clockSpeed(DEFAULT_CLOCK_SPEED)
            .lithography(DEFAULT_LITHOGRAPHY)
            .output(DEFAULT_OUTPUT)
            .inputPower(DEFAULT_INPUT_POWER)
            .bus(DEFAULT_BUS)
            .dimension(DEFAULT_DIMENSION);
        ProductResourceIT.createProductField(gpu);
        return gpu;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gpu createUpdatedEntity(EntityManager em) {
        Gpu gpu = new Gpu()
            .frequency(UPDATED_FREQUENCY)
            .memory(UPDATED_MEMORY)
            .consumption(UPDATED_CONSUMPTION)
            .clockSpeed(UPDATED_CLOCK_SPEED)
            .lithography(UPDATED_LITHOGRAPHY)
            .output(UPDATED_OUTPUT)
            .inputPower(UPDATED_INPUT_POWER)
            .bus(UPDATED_BUS)
            .dimension(UPDATED_DIMENSION);
        ProductResourceIT.updateProductField(gpu);
        return gpu;
    }

    @BeforeEach
    public void initTest() {
        gpu = createEntity(em);
    }

    @Test
    @Transactional
    void createGpu() throws Exception {
        int databaseSizeBeforeCreate = gpuRepository.findAll().size();
        // Create the Gpu
        restGpuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gpu)))
            .andExpect(status().is4xxClientError());

        // Validate the Gpu in the database
        List<Gpu> gpuList = gpuRepository.findAll();
        assertThat(gpuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGpus() throws Exception {
        // Initialize the database
        gpuRepository.saveAndFlush(gpu);

        // Get all the gpuList
        var action = restGpuMockMvc.perform(get(ENTITY_API_URL + "?sort=id,desc"));

        action
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gpu.getId().intValue())))
            .andExpect(jsonPath("$.[*].frequency").value(hasItem(DEFAULT_FREQUENCY.doubleValue())))
            .andExpect(jsonPath("$.[*].memory").value(hasItem(DEFAULT_MEMORY)))
            .andExpect(jsonPath("$.[*].consumption").value(hasItem(DEFAULT_CONSUMPTION)))
            .andExpect(jsonPath("$.[*].clockSpeed").value(hasItem(DEFAULT_CLOCK_SPEED)))
            .andExpect(jsonPath("$.[*].lithography").value(hasItem(DEFAULT_LITHOGRAPHY)))
            .andExpect(jsonPath("$.[*].output").value(hasItem(DEFAULT_OUTPUT)))
            .andExpect(jsonPath("$.[*].inputPower").value(hasItem(DEFAULT_INPUT_POWER)))
            .andExpect(jsonPath("$.[*].bus").value(hasItem(DEFAULT_BUS.toString())))
            .andExpect(jsonPath("$.[*].dimension.height").value(hasItem(DEFAULT_DIMENSION.getHeight())))
            .andExpect(jsonPath("$.[*].dimension.width").value(hasItem(DEFAULT_DIMENSION.getWidth())))
            .andExpect(jsonPath("$.[*].dimension.length").value(hasItem(DEFAULT_DIMENSION.getLength())));

        getAllProductAssertProductField(action);
    }

    @Test
    @Transactional
    void getGpu() throws Exception {
        // Initialize the database
        gpuRepository.saveAndFlush(gpu);

        // Get the gpu
        var action = restGpuMockMvc.perform(get(ENTITY_API_URL_ID, gpu.getId()));

        action
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gpu.getId().intValue()))
            .andExpect(jsonPath("$.frequency").value(DEFAULT_FREQUENCY.doubleValue()))
            .andExpect(jsonPath("$.memory").value(DEFAULT_MEMORY))
            .andExpect(jsonPath("$.consumption").value(DEFAULT_CONSUMPTION))
            .andExpect(jsonPath("$.clockSpeed").value(DEFAULT_CLOCK_SPEED))
            .andExpect(jsonPath("$.lithography").value(DEFAULT_LITHOGRAPHY))
            .andExpect(jsonPath("$.output").value(DEFAULT_OUTPUT))
            .andExpect(jsonPath("$.inputPower").value(DEFAULT_INPUT_POWER))
            .andExpect(jsonPath("$.bus").value(DEFAULT_BUS.toString()))
            .andExpect(jsonPath("$.dimension").value(DEFAULT_DIMENSION));

        getProductAssertProductField(action);
    }

    @Test
    @Transactional
    void getNonExistingGpu() throws Exception {
        // Get the gpu
        restGpuMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGpu() throws Exception {
        // Initialize the database
        gpuRepository.saveAndFlush(gpu);

        int databaseSizeBeforeUpdate = gpuRepository.findAll().size();

        // Update the gpu
        Gpu updatedGpu = gpuRepository.findById(gpu.getId()).get();
        // Disconnect from session so that the updates on updatedGpu are not directly saved in db
        em.detach(updatedGpu);
        updatedGpu
            .frequency(UPDATED_FREQUENCY)
            .memory(UPDATED_MEMORY)
            .consumption(UPDATED_CONSUMPTION)
            .clockSpeed(UPDATED_CLOCK_SPEED)
            .lithography(UPDATED_LITHOGRAPHY)
            .output(UPDATED_OUTPUT)
            .inputPower(UPDATED_INPUT_POWER)
            .bus(UPDATED_BUS)
            .dimension(UPDATED_DIMENSION);

        ProductResourceIT.updateProductField(updatedGpu);

        restGpuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGpu.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGpu))
            )
            .andExpect(status().is4xxClientError());

        // Validate the Gpu in the database
        List<Gpu> gpuList = gpuRepository.findAll();
        assertThat(gpuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGpuWithPatch() throws Exception {
        // Initialize the database
        gpuRepository.saveAndFlush(gpu);

        int databaseSizeBeforeUpdate = gpuRepository.findAll().size();

        // Update the gpu using partial update
        Gpu partialUpdatedGpu = new Gpu();
        partialUpdatedGpu.setId(gpu.getId());

        partialUpdatedGpu
            .frequency(UPDATED_FREQUENCY)
            .memory(UPDATED_MEMORY)
            .clockSpeed(UPDATED_CLOCK_SPEED)
            .lithography(UPDATED_LITHOGRAPHY)
            .inputPower(UPDATED_INPUT_POWER);

        partialUpdateField(partialUpdatedGpu);

        restGpuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGpu.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGpu))
            )
            .andExpect(status().is4xxClientError());

        // Validate the Gpu in the database
        List<Gpu> gpuList = gpuRepository.findAll();
        assertThat(gpuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGpu() throws Exception {
        // Initialize the database
        gpuRepository.saveAndFlush(gpu);

        int databaseSizeBeforeDelete = gpuRepository.findAll().size();

        // Delete the gpu
        restGpuMockMvc
            .perform(delete(ENTITY_API_URL_ID, gpu.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError());

        // Validate the database contains one less item
        List<Gpu> gpuList = gpuRepository.findAll();
        assertThat(gpuList).hasSize(databaseSizeBeforeDelete);
    }
}
