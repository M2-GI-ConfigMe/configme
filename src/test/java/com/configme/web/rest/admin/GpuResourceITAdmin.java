package com.configme.web.rest.admin;

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
@WithMockUser(roles = { "ADMIN" })
class GpuResourceITAdmin {

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
            .andExpect(status().isCreated());

        // Validate the Gpu in the database
        List<Gpu> gpuList = gpuRepository.findAll();
        assertThat(gpuList).hasSize(databaseSizeBeforeCreate + 1);
        Gpu testGpu = gpuList.get(gpuList.size() - 1);
        assertThat(testGpu.getFrequency()).isEqualTo(DEFAULT_FREQUENCY);
        assertThat(testGpu.getMemory()).isEqualTo(DEFAULT_MEMORY);
        assertThat(testGpu.getConsumption()).isEqualTo(DEFAULT_CONSUMPTION);
        assertThat(testGpu.getClockSpeed()).isEqualTo(DEFAULT_CLOCK_SPEED);
        assertThat(testGpu.getLithography()).isEqualTo(DEFAULT_LITHOGRAPHY);
        assertThat(testGpu.getOutput()).isEqualTo(DEFAULT_OUTPUT);
        assertThat(testGpu.getInputPower()).isEqualTo(DEFAULT_INPUT_POWER);
        assertThat(testGpu.getBus()).isEqualTo(DEFAULT_BUS);
        assertThat(testGpu.getDimension()).isEqualTo(DEFAULT_DIMENSION);

        ProductResourceIT.assertProductCreation(testGpu);
    }

    @Test
    @Transactional
    void createGpuWithExistingId() throws Exception {
        // Create the Gpu with an existing ID
        gpu.setId(1L);

        int databaseSizeBeforeCreate = gpuRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGpuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gpu)))
            .andExpect(status().isBadRequest());

        // Validate the Gpu in the database
        List<Gpu> gpuList = gpuRepository.findAll();
        assertThat(gpuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFrequencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = gpuRepository.findAll().size();
        // set the field null
        gpu.setFrequency(null);

        // Create the Gpu, which fails.

        restGpuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gpu)))
            .andExpect(status().isBadRequest());

        List<Gpu> gpuList = gpuRepository.findAll();
        assertThat(gpuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMemoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = gpuRepository.findAll().size();
        // set the field null
        gpu.setMemory(null);

        // Create the Gpu, which fails.

        restGpuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gpu)))
            .andExpect(status().isBadRequest());

        List<Gpu> gpuList = gpuRepository.findAll();
        assertThat(gpuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConsumptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = gpuRepository.findAll().size();
        // set the field null
        gpu.setConsumption(null);

        // Create the Gpu, which fails.

        restGpuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gpu)))
            .andExpect(status().isBadRequest());

        List<Gpu> gpuList = gpuRepository.findAll();
        assertThat(gpuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkClockSpeedIsRequired() throws Exception {
        int databaseSizeBeforeTest = gpuRepository.findAll().size();
        // set the field null
        gpu.setClockSpeed(null);

        // Create the Gpu, which fails.

        restGpuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gpu)))
            .andExpect(status().isBadRequest());

        List<Gpu> gpuList = gpuRepository.findAll();
        assertThat(gpuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLithographyIsRequired() throws Exception {
        int databaseSizeBeforeTest = gpuRepository.findAll().size();
        // set the field null
        gpu.setLithography(null);

        // Create the Gpu, which fails.

        restGpuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gpu)))
            .andExpect(status().isBadRequest());

        List<Gpu> gpuList = gpuRepository.findAll();
        assertThat(gpuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOutputIsRequired() throws Exception {
        int databaseSizeBeforeTest = gpuRepository.findAll().size();
        // set the field null
        gpu.setOutput(null);

        // Create the Gpu, which fails.

        restGpuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gpu)))
            .andExpect(status().isBadRequest());

        List<Gpu> gpuList = gpuRepository.findAll();
        assertThat(gpuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkInputPowerIsRequired() throws Exception {
        int databaseSizeBeforeTest = gpuRepository.findAll().size();
        // set the field null
        gpu.setInputPower(null);

        // Create the Gpu, which fails.

        restGpuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gpu)))
            .andExpect(status().isBadRequest());

        List<Gpu> gpuList = gpuRepository.findAll();
        assertThat(gpuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBusIsRequired() throws Exception {
        int databaseSizeBeforeTest = gpuRepository.findAll().size();
        // set the field null
        gpu.setBus(null);

        // Create the Gpu, which fails.

        restGpuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gpu)))
            .andExpect(status().isBadRequest());

        List<Gpu> gpuList = gpuRepository.findAll();
        assertThat(gpuList).hasSize(databaseSizeBeforeTest);
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

        ProductResourceIT.getAllProductAssertProductField(action);
    }

    @Test
    @Transactional
    void getGpu() throws Exception {
        // Initialize the database
        gpuRepository.saveAndFlush(gpu);

        // Get the gpu
        var actions = restGpuMockMvc.perform(get(ENTITY_API_URL_ID, gpu.getId()));

        actions
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

        ProductResourceIT.getProductAssertProductField(actions);
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
            .andExpect(status().isOk());

        // Validate the Gpu in the database
        List<Gpu> gpuList = gpuRepository.findAll();
        assertThat(gpuList).hasSize(databaseSizeBeforeUpdate);
        Gpu testGpu = gpuList.get(gpuList.size() - 1);
        assertThat(testGpu.getFrequency()).isEqualTo(UPDATED_FREQUENCY);
        assertThat(testGpu.getMemory()).isEqualTo(UPDATED_MEMORY);
        assertThat(testGpu.getConsumption()).isEqualTo(UPDATED_CONSUMPTION);
        assertThat(testGpu.getClockSpeed()).isEqualTo(UPDATED_CLOCK_SPEED);
        assertThat(testGpu.getLithography()).isEqualTo(UPDATED_LITHOGRAPHY);
        assertThat(testGpu.getOutput()).isEqualTo(UPDATED_OUTPUT);
        assertThat(testGpu.getInputPower()).isEqualTo(UPDATED_INPUT_POWER);
        assertThat(testGpu.getBus()).isEqualTo(UPDATED_BUS);
        assertThat(testGpu.getDimension()).usingRecursiveComparison().isEqualTo(UPDATED_DIMENSION);

        ProductResourceIT.assertProductUpdate(testGpu);
    }

    @Test
    @Transactional
    void putNonExistingGpu() throws Exception {
        int databaseSizeBeforeUpdate = gpuRepository.findAll().size();
        gpu.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGpuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gpu.getId()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gpu))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gpu in the database
        List<Gpu> gpuList = gpuRepository.findAll();
        assertThat(gpuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGpu() throws Exception {
        int databaseSizeBeforeUpdate = gpuRepository.findAll().size();
        gpu.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGpuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gpu))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gpu in the database
        List<Gpu> gpuList = gpuRepository.findAll();
        assertThat(gpuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGpu() throws Exception {
        int databaseSizeBeforeUpdate = gpuRepository.findAll().size();
        gpu.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGpuMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gpu)))
            .andExpect(status().isMethodNotAllowed());

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

        ProductResourceIT.partialUpdateField(partialUpdatedGpu);

        restGpuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGpu.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGpu))
            )
            .andExpect(status().isOk());

        // Validate the Gpu in the database
        List<Gpu> gpuList = gpuRepository.findAll();
        assertThat(gpuList).hasSize(databaseSizeBeforeUpdate);
        Gpu testGpu = gpuList.get(gpuList.size() - 1);
        assertThat(testGpu.getFrequency()).isEqualTo(UPDATED_FREQUENCY);
        assertThat(testGpu.getMemory()).isEqualTo(UPDATED_MEMORY);
        assertThat(testGpu.getConsumption()).isEqualTo(DEFAULT_CONSUMPTION);
        assertThat(testGpu.getClockSpeed()).isEqualTo(UPDATED_CLOCK_SPEED);
        assertThat(testGpu.getLithography()).isEqualTo(UPDATED_LITHOGRAPHY);
        assertThat(testGpu.getOutput()).isEqualTo(DEFAULT_OUTPUT);
        assertThat(testGpu.getInputPower()).isEqualTo(UPDATED_INPUT_POWER);
        assertThat(testGpu.getBus()).isEqualTo(DEFAULT_BUS);
        assertThat(testGpu.getDimension()).isEqualTo(DEFAULT_DIMENSION);

        ProductResourceIT.assertPartialUpdateField(testGpu);
    }

    @Test
    @Transactional
    void fullUpdateGpuWithPatch() throws Exception {
        // Initialize the database
        gpuRepository.saveAndFlush(gpu);

        int databaseSizeBeforeUpdate = gpuRepository.findAll().size();

        // Update the gpu using partial update
        Gpu partialUpdatedGpu = new Gpu();
        partialUpdatedGpu.setId(gpu.getId());

        partialUpdatedGpu
            .frequency(UPDATED_FREQUENCY)
            .memory(UPDATED_MEMORY)
            .consumption(UPDATED_CONSUMPTION)
            .clockSpeed(UPDATED_CLOCK_SPEED)
            .lithography(UPDATED_LITHOGRAPHY)
            .output(UPDATED_OUTPUT)
            .inputPower(UPDATED_INPUT_POWER)
            .bus(UPDATED_BUS)
            .dimension(UPDATED_DIMENSION);

        ProductResourceIT.updateProductField(partialUpdatedGpu);

        restGpuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGpu.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGpu))
            )
            .andExpect(status().isOk());

        // Validate the Gpu in the database
        List<Gpu> gpuList = gpuRepository.findAll();
        assertThat(gpuList).hasSize(databaseSizeBeforeUpdate);
        Gpu testGpu = gpuList.get(gpuList.size() - 1);
        assertThat(testGpu.getFrequency()).isEqualTo(UPDATED_FREQUENCY);
        assertThat(testGpu.getMemory()).isEqualTo(UPDATED_MEMORY);
        assertThat(testGpu.getConsumption()).isEqualTo(UPDATED_CONSUMPTION);
        assertThat(testGpu.getClockSpeed()).isEqualTo(UPDATED_CLOCK_SPEED);
        assertThat(testGpu.getLithography()).isEqualTo(UPDATED_LITHOGRAPHY);
        assertThat(testGpu.getOutput()).isEqualTo(UPDATED_OUTPUT);
        assertThat(testGpu.getInputPower()).isEqualTo(UPDATED_INPUT_POWER);
        assertThat(testGpu.getBus()).isEqualTo(UPDATED_BUS);
        assertThat(testGpu.getDimension()).isEqualTo(UPDATED_DIMENSION);

        ProductResourceIT.assertProductUpdate(testGpu);
    }

    @Test
    @Transactional
    void patchNonExistingGpu() throws Exception {
        int databaseSizeBeforeUpdate = gpuRepository.findAll().size();
        gpu.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGpuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gpu.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gpu))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gpu in the database
        List<Gpu> gpuList = gpuRepository.findAll();
        assertThat(gpuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGpu() throws Exception {
        int databaseSizeBeforeUpdate = gpuRepository.findAll().size();
        gpu.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGpuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gpu))
            )
            .andExpect(status().isBadRequest());

        // Validate the Gpu in the database
        List<Gpu> gpuList = gpuRepository.findAll();
        assertThat(gpuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGpu() throws Exception {
        int databaseSizeBeforeUpdate = gpuRepository.findAll().size();
        gpu.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGpuMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(gpu)))
            .andExpect(status().isMethodNotAllowed());

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
        restGpuMockMvc.perform(delete(ENTITY_API_URL_ID, gpu.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Gpu> gpuList = gpuRepository.findAll();
        assertThat(gpuList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
