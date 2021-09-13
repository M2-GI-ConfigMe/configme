package com.configme.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.configme.IntegrationTest;
import com.configme.domain.Cpu;
import com.configme.domain.enumeration.SocketType;
import com.configme.repository.CpuRepository;
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
 * Integration tests for the {@link CpuResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser(roles = { "ADMIN" })
class CpuResourceITAdmin {

    private static final Float DEFAULT_FREQUENCY = 1F;
    private static final Float UPDATED_FREQUENCY = 2F;

    private static final Integer DEFAULT_CACHE_L_1 = 1;
    private static final Integer UPDATED_CACHE_L_1 = 2;

    private static final Integer DEFAULT_CACHE_L_2 = 1;
    private static final Integer UPDATED_CACHE_L_2 = 2;

    private static final Integer DEFAULT_CACHE_L_3 = 1;
    private static final Integer UPDATED_CACHE_L_3 = 2;

    private static final Integer DEFAULT_NB_HEART = 1;
    private static final Integer UPDATED_NB_HEART = 2;

    private static final Integer DEFAULT_NB_THREAD = 1;
    private static final Integer UPDATED_NB_THREAD = 2;

    private static final Boolean DEFAULT_HAS_VENTIRAD = false;
    private static final Boolean UPDATED_HAS_VENTIRAD = true;

    private static final SocketType DEFAULT_SOCKET_TYPE = SocketType.AM4;
    private static final SocketType UPDATED_SOCKET_TYPE = SocketType.TR4;

    private static final Integer DEFAULT_LITHOGRAPHY = 1;
    private static final Integer UPDATED_LITHOGRAPHY = 2;

    private static final Float DEFAULT_RAM_FREQUENCY_MAX = 1F;
    private static final Float UPDATED_RAM_FREQUENCY_MAX = 2F;

    private static final Integer DEFAULT_CONSUMPTION = 1;
    private static final Integer UPDATED_CONSUMPTION = 2;

    private static final Boolean DEFAULT_HAS_GPU = false;
    private static final Boolean UPDATED_HAS_GPU = true;

    private static final String ENTITY_API_URL = "/api/cpus";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CpuRepository cpuRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCpuMockMvc;

    private Cpu cpu;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cpu createEntity(EntityManager em) {
        Cpu cpu = new Cpu()
            .frequency(DEFAULT_FREQUENCY)
            .cacheL1(DEFAULT_CACHE_L_1)
            .cacheL2(DEFAULT_CACHE_L_2)
            .cacheL3(DEFAULT_CACHE_L_3)
            .nbHeart(DEFAULT_NB_HEART)
            .nbThread(DEFAULT_NB_THREAD)
            .hasVentirad(DEFAULT_HAS_VENTIRAD)
            .socketType(DEFAULT_SOCKET_TYPE)
            .lithography(DEFAULT_LITHOGRAPHY)
            .ramFrequencyMax(DEFAULT_RAM_FREQUENCY_MAX)
            .consumption(DEFAULT_CONSUMPTION)
            .hasGpu(DEFAULT_HAS_GPU);
        return cpu;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cpu createUpdatedEntity(EntityManager em) {
        Cpu cpu = new Cpu()
            .frequency(UPDATED_FREQUENCY)
            .cacheL1(UPDATED_CACHE_L_1)
            .cacheL2(UPDATED_CACHE_L_2)
            .cacheL3(UPDATED_CACHE_L_3)
            .nbHeart(UPDATED_NB_HEART)
            .nbThread(UPDATED_NB_THREAD)
            .hasVentirad(UPDATED_HAS_VENTIRAD)
            .socketType(UPDATED_SOCKET_TYPE)
            .lithography(UPDATED_LITHOGRAPHY)
            .ramFrequencyMax(UPDATED_RAM_FREQUENCY_MAX)
            .consumption(UPDATED_CONSUMPTION)
            .hasGpu(UPDATED_HAS_GPU);
        return cpu;
    }

    @BeforeEach
    public void initTest() {
        cpu = createEntity(em);
    }

    @Test
    @Transactional
    void createCpu() throws Exception {
        int databaseSizeBeforeCreate = cpuRepository.findAll().size();
        // Create the Cpu
        restCpuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cpu)))
            .andExpect(status().isCreated());

        // Validate the Cpu in the database
        List<Cpu> cpuList = cpuRepository.findAll();
        assertThat(cpuList).hasSize(databaseSizeBeforeCreate + 1);
        Cpu testCpu = cpuList.get(cpuList.size() - 1);
        assertThat(testCpu.getFrequency()).isEqualTo(DEFAULT_FREQUENCY);
        assertThat(testCpu.getCacheL1()).isEqualTo(DEFAULT_CACHE_L_1);
        assertThat(testCpu.getCacheL2()).isEqualTo(DEFAULT_CACHE_L_2);
        assertThat(testCpu.getCacheL3()).isEqualTo(DEFAULT_CACHE_L_3);
        assertThat(testCpu.getNbHeart()).isEqualTo(DEFAULT_NB_HEART);
        assertThat(testCpu.getNbThread()).isEqualTo(DEFAULT_NB_THREAD);
        assertThat(testCpu.getHasVentirad()).isEqualTo(DEFAULT_HAS_VENTIRAD);
        assertThat(testCpu.getSocketType()).isEqualTo(DEFAULT_SOCKET_TYPE);
        assertThat(testCpu.getLithography()).isEqualTo(DEFAULT_LITHOGRAPHY);
        assertThat(testCpu.getRamFrequencyMax()).isEqualTo(DEFAULT_RAM_FREQUENCY_MAX);
        assertThat(testCpu.getConsumption()).isEqualTo(DEFAULT_CONSUMPTION);
        assertThat(testCpu.getHasGpu()).isEqualTo(DEFAULT_HAS_GPU);
    }

    @Test
    @Transactional
    void createCpuWithExistingId() throws Exception {
        // Create the Cpu with an existing ID
        cpu.setId(1L);

        int databaseSizeBeforeCreate = cpuRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCpuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cpu)))
            .andExpect(status().isBadRequest());

        // Validate the Cpu in the database
        List<Cpu> cpuList = cpuRepository.findAll();
        assertThat(cpuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFrequencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = cpuRepository.findAll().size();
        // set the field null
        cpu.setFrequency(null);

        // Create the Cpu, which fails.

        restCpuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cpu)))
            .andExpect(status().isBadRequest());

        List<Cpu> cpuList = cpuRepository.findAll();
        assertThat(cpuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNbHeartIsRequired() throws Exception {
        int databaseSizeBeforeTest = cpuRepository.findAll().size();
        // set the field null
        cpu.setNbHeart(null);

        // Create the Cpu, which fails.

        restCpuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cpu)))
            .andExpect(status().isBadRequest());

        List<Cpu> cpuList = cpuRepository.findAll();
        assertThat(cpuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNbThreadIsRequired() throws Exception {
        int databaseSizeBeforeTest = cpuRepository.findAll().size();
        // set the field null
        cpu.setNbThread(null);

        // Create the Cpu, which fails.

        restCpuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cpu)))
            .andExpect(status().isBadRequest());

        List<Cpu> cpuList = cpuRepository.findAll();
        assertThat(cpuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHasVentiradIsRequired() throws Exception {
        int databaseSizeBeforeTest = cpuRepository.findAll().size();
        // set the field null
        cpu.setHasVentirad(null);

        // Create the Cpu, which fails.

        restCpuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cpu)))
            .andExpect(status().isBadRequest());

        List<Cpu> cpuList = cpuRepository.findAll();
        assertThat(cpuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSocketTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cpuRepository.findAll().size();
        // set the field null
        cpu.setSocketType(null);

        // Create the Cpu, which fails.

        restCpuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cpu)))
            .andExpect(status().isBadRequest());

        List<Cpu> cpuList = cpuRepository.findAll();
        assertThat(cpuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLithographyIsRequired() throws Exception {
        int databaseSizeBeforeTest = cpuRepository.findAll().size();
        // set the field null
        cpu.setLithography(null);

        // Create the Cpu, which fails.

        restCpuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cpu)))
            .andExpect(status().isBadRequest());

        List<Cpu> cpuList = cpuRepository.findAll();
        assertThat(cpuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRamFrequencyMaxIsRequired() throws Exception {
        int databaseSizeBeforeTest = cpuRepository.findAll().size();
        // set the field null
        cpu.setRamFrequencyMax(null);

        // Create the Cpu, which fails.

        restCpuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cpu)))
            .andExpect(status().isBadRequest());

        List<Cpu> cpuList = cpuRepository.findAll();
        assertThat(cpuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConsumptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = cpuRepository.findAll().size();
        // set the field null
        cpu.setConsumption(null);

        // Create the Cpu, which fails.

        restCpuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cpu)))
            .andExpect(status().isBadRequest());

        List<Cpu> cpuList = cpuRepository.findAll();
        assertThat(cpuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHasGpuIsRequired() throws Exception {
        int databaseSizeBeforeTest = cpuRepository.findAll().size();
        // set the field null
        cpu.setHasGpu(null);

        // Create the Cpu, which fails.

        restCpuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cpu)))
            .andExpect(status().isBadRequest());

        List<Cpu> cpuList = cpuRepository.findAll();
        assertThat(cpuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCpus() throws Exception {
        // Initialize the database
        cpuRepository.saveAndFlush(cpu);

        // Get all the cpuList
        restCpuMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cpu.getId().intValue())))
            .andExpect(jsonPath("$.[*].frequency").value(hasItem(DEFAULT_FREQUENCY.doubleValue())))
            .andExpect(jsonPath("$.[*].cacheL1").value(hasItem(DEFAULT_CACHE_L_1)))
            .andExpect(jsonPath("$.[*].cacheL2").value(hasItem(DEFAULT_CACHE_L_2)))
            .andExpect(jsonPath("$.[*].cacheL3").value(hasItem(DEFAULT_CACHE_L_3)))
            .andExpect(jsonPath("$.[*].nbHeart").value(hasItem(DEFAULT_NB_HEART)))
            .andExpect(jsonPath("$.[*].nbThread").value(hasItem(DEFAULT_NB_THREAD)))
            .andExpect(jsonPath("$.[*].hasVentirad").value(hasItem(DEFAULT_HAS_VENTIRAD.booleanValue())))
            .andExpect(jsonPath("$.[*].socketType").value(hasItem(DEFAULT_SOCKET_TYPE.toString())))
            .andExpect(jsonPath("$.[*].lithography").value(hasItem(DEFAULT_LITHOGRAPHY)))
            .andExpect(jsonPath("$.[*].ramFrequencyMax").value(hasItem(DEFAULT_RAM_FREQUENCY_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].consumption").value(hasItem(DEFAULT_CONSUMPTION)))
            .andExpect(jsonPath("$.[*].hasGpu").value(hasItem(DEFAULT_HAS_GPU.booleanValue())));
    }

    @Test
    @Transactional
    void getCpu() throws Exception {
        // Initialize the database
        cpuRepository.saveAndFlush(cpu);

        // Get the cpu
        restCpuMockMvc
            .perform(get(ENTITY_API_URL_ID, cpu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cpu.getId().intValue()))
            .andExpect(jsonPath("$.frequency").value(DEFAULT_FREQUENCY.doubleValue()))
            .andExpect(jsonPath("$.cacheL1").value(DEFAULT_CACHE_L_1))
            .andExpect(jsonPath("$.cacheL2").value(DEFAULT_CACHE_L_2))
            .andExpect(jsonPath("$.cacheL3").value(DEFAULT_CACHE_L_3))
            .andExpect(jsonPath("$.nbHeart").value(DEFAULT_NB_HEART))
            .andExpect(jsonPath("$.nbThread").value(DEFAULT_NB_THREAD))
            .andExpect(jsonPath("$.hasVentirad").value(DEFAULT_HAS_VENTIRAD.booleanValue()))
            .andExpect(jsonPath("$.socketType").value(DEFAULT_SOCKET_TYPE.toString()))
            .andExpect(jsonPath("$.lithography").value(DEFAULT_LITHOGRAPHY))
            .andExpect(jsonPath("$.ramFrequencyMax").value(DEFAULT_RAM_FREQUENCY_MAX.doubleValue()))
            .andExpect(jsonPath("$.consumption").value(DEFAULT_CONSUMPTION))
            .andExpect(jsonPath("$.hasGpu").value(DEFAULT_HAS_GPU.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingCpu() throws Exception {
        // Get the cpu
        restCpuMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCpu() throws Exception {
        // Initialize the database
        cpuRepository.saveAndFlush(cpu);

        int databaseSizeBeforeUpdate = cpuRepository.findAll().size();

        // Update the cpu
        Cpu updatedCpu = cpuRepository.findById(cpu.getId()).get();
        // Disconnect from session so that the updates on updatedCpu are not directly saved in db
        em.detach(updatedCpu);
        updatedCpu
            .frequency(UPDATED_FREQUENCY)
            .cacheL1(UPDATED_CACHE_L_1)
            .cacheL2(UPDATED_CACHE_L_2)
            .cacheL3(UPDATED_CACHE_L_3)
            .nbHeart(UPDATED_NB_HEART)
            .nbThread(UPDATED_NB_THREAD)
            .hasVentirad(UPDATED_HAS_VENTIRAD)
            .socketType(UPDATED_SOCKET_TYPE)
            .lithography(UPDATED_LITHOGRAPHY)
            .ramFrequencyMax(UPDATED_RAM_FREQUENCY_MAX)
            .consumption(UPDATED_CONSUMPTION)
            .hasGpu(UPDATED_HAS_GPU);

        restCpuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCpu.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCpu))
            )
            .andExpect(status().isOk());

        // Validate the Cpu in the database
        List<Cpu> cpuList = cpuRepository.findAll();
        assertThat(cpuList).hasSize(databaseSizeBeforeUpdate);
        Cpu testCpu = cpuList.get(cpuList.size() - 1);
        assertThat(testCpu.getFrequency()).isEqualTo(UPDATED_FREQUENCY);
        assertThat(testCpu.getCacheL1()).isEqualTo(UPDATED_CACHE_L_1);
        assertThat(testCpu.getCacheL2()).isEqualTo(UPDATED_CACHE_L_2);
        assertThat(testCpu.getCacheL3()).isEqualTo(UPDATED_CACHE_L_3);
        assertThat(testCpu.getNbHeart()).isEqualTo(UPDATED_NB_HEART);
        assertThat(testCpu.getNbThread()).isEqualTo(UPDATED_NB_THREAD);
        assertThat(testCpu.getHasVentirad()).isEqualTo(UPDATED_HAS_VENTIRAD);
        assertThat(testCpu.getSocketType()).isEqualTo(UPDATED_SOCKET_TYPE);
        assertThat(testCpu.getLithography()).isEqualTo(UPDATED_LITHOGRAPHY);
        assertThat(testCpu.getRamFrequencyMax()).isEqualTo(UPDATED_RAM_FREQUENCY_MAX);
        assertThat(testCpu.getConsumption()).isEqualTo(UPDATED_CONSUMPTION);
        assertThat(testCpu.getHasGpu()).isEqualTo(UPDATED_HAS_GPU);
    }

    @Test
    @Transactional
    void putNonExistingCpu() throws Exception {
        int databaseSizeBeforeUpdate = cpuRepository.findAll().size();
        cpu.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCpuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cpu.getId()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cpu))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cpu in the database
        List<Cpu> cpuList = cpuRepository.findAll();
        assertThat(cpuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCpu() throws Exception {
        int databaseSizeBeforeUpdate = cpuRepository.findAll().size();
        cpu.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCpuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cpu))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cpu in the database
        List<Cpu> cpuList = cpuRepository.findAll();
        assertThat(cpuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCpu() throws Exception {
        int databaseSizeBeforeUpdate = cpuRepository.findAll().size();
        cpu.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCpuMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cpu)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cpu in the database
        List<Cpu> cpuList = cpuRepository.findAll();
        assertThat(cpuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCpuWithPatch() throws Exception {
        // Initialize the database
        cpuRepository.saveAndFlush(cpu);

        int databaseSizeBeforeUpdate = cpuRepository.findAll().size();

        // Update the cpu using partial update
        Cpu partialUpdatedCpu = new Cpu();
        partialUpdatedCpu.setId(cpu.getId());

        partialUpdatedCpu
            .frequency(UPDATED_FREQUENCY)
            .cacheL1(UPDATED_CACHE_L_1)
            .nbHeart(UPDATED_NB_HEART)
            .socketType(UPDATED_SOCKET_TYPE)
            .lithography(UPDATED_LITHOGRAPHY)
            .consumption(UPDATED_CONSUMPTION);

        restCpuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCpu.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCpu))
            )
            .andExpect(status().isOk());

        // Validate the Cpu in the database
        List<Cpu> cpuList = cpuRepository.findAll();
        assertThat(cpuList).hasSize(databaseSizeBeforeUpdate);
        Cpu testCpu = cpuList.get(cpuList.size() - 1);
        assertThat(testCpu.getFrequency()).isEqualTo(UPDATED_FREQUENCY);
        assertThat(testCpu.getCacheL1()).isEqualTo(UPDATED_CACHE_L_1);
        assertThat(testCpu.getCacheL2()).isEqualTo(DEFAULT_CACHE_L_2);
        assertThat(testCpu.getCacheL3()).isEqualTo(DEFAULT_CACHE_L_3);
        assertThat(testCpu.getNbHeart()).isEqualTo(UPDATED_NB_HEART);
        assertThat(testCpu.getNbThread()).isEqualTo(DEFAULT_NB_THREAD);
        assertThat(testCpu.getHasVentirad()).isEqualTo(DEFAULT_HAS_VENTIRAD);
        assertThat(testCpu.getSocketType()).isEqualTo(UPDATED_SOCKET_TYPE);
        assertThat(testCpu.getLithography()).isEqualTo(UPDATED_LITHOGRAPHY);
        assertThat(testCpu.getRamFrequencyMax()).isEqualTo(DEFAULT_RAM_FREQUENCY_MAX);
        assertThat(testCpu.getConsumption()).isEqualTo(UPDATED_CONSUMPTION);
        assertThat(testCpu.getHasGpu()).isEqualTo(DEFAULT_HAS_GPU);
    }

    @Test
    @Transactional
    void fullUpdateCpuWithPatch() throws Exception {
        // Initialize the database
        cpuRepository.saveAndFlush(cpu);

        int databaseSizeBeforeUpdate = cpuRepository.findAll().size();

        // Update the cpu using partial update
        Cpu partialUpdatedCpu = new Cpu();
        partialUpdatedCpu.setId(cpu.getId());

        partialUpdatedCpu
            .frequency(UPDATED_FREQUENCY)
            .cacheL1(UPDATED_CACHE_L_1)
            .cacheL2(UPDATED_CACHE_L_2)
            .cacheL3(UPDATED_CACHE_L_3)
            .nbHeart(UPDATED_NB_HEART)
            .nbThread(UPDATED_NB_THREAD)
            .hasVentirad(UPDATED_HAS_VENTIRAD)
            .socketType(UPDATED_SOCKET_TYPE)
            .lithography(UPDATED_LITHOGRAPHY)
            .ramFrequencyMax(UPDATED_RAM_FREQUENCY_MAX)
            .consumption(UPDATED_CONSUMPTION)
            .hasGpu(UPDATED_HAS_GPU);

        restCpuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCpu.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCpu))
            )
            .andExpect(status().isOk());

        // Validate the Cpu in the database
        List<Cpu> cpuList = cpuRepository.findAll();
        assertThat(cpuList).hasSize(databaseSizeBeforeUpdate);
        Cpu testCpu = cpuList.get(cpuList.size() - 1);
        assertThat(testCpu.getFrequency()).isEqualTo(UPDATED_FREQUENCY);
        assertThat(testCpu.getCacheL1()).isEqualTo(UPDATED_CACHE_L_1);
        assertThat(testCpu.getCacheL2()).isEqualTo(UPDATED_CACHE_L_2);
        assertThat(testCpu.getCacheL3()).isEqualTo(UPDATED_CACHE_L_3);
        assertThat(testCpu.getNbHeart()).isEqualTo(UPDATED_NB_HEART);
        assertThat(testCpu.getNbThread()).isEqualTo(UPDATED_NB_THREAD);
        assertThat(testCpu.getHasVentirad()).isEqualTo(UPDATED_HAS_VENTIRAD);
        assertThat(testCpu.getSocketType()).isEqualTo(UPDATED_SOCKET_TYPE);
        assertThat(testCpu.getLithography()).isEqualTo(UPDATED_LITHOGRAPHY);
        assertThat(testCpu.getRamFrequencyMax()).isEqualTo(UPDATED_RAM_FREQUENCY_MAX);
        assertThat(testCpu.getConsumption()).isEqualTo(UPDATED_CONSUMPTION);
        assertThat(testCpu.getHasGpu()).isEqualTo(UPDATED_HAS_GPU);
    }

    @Test
    @Transactional
    void patchNonExistingCpu() throws Exception {
        int databaseSizeBeforeUpdate = cpuRepository.findAll().size();
        cpu.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCpuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cpu.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cpu))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cpu in the database
        List<Cpu> cpuList = cpuRepository.findAll();
        assertThat(cpuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCpu() throws Exception {
        int databaseSizeBeforeUpdate = cpuRepository.findAll().size();
        cpu.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCpuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cpu))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cpu in the database
        List<Cpu> cpuList = cpuRepository.findAll();
        assertThat(cpuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCpu() throws Exception {
        int databaseSizeBeforeUpdate = cpuRepository.findAll().size();
        cpu.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCpuMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cpu)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cpu in the database
        List<Cpu> cpuList = cpuRepository.findAll();
        assertThat(cpuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCpu() throws Exception {
        // Initialize the database
        cpuRepository.saveAndFlush(cpu);

        int databaseSizeBeforeDelete = cpuRepository.findAll().size();

        // Delete the cpu
        restCpuMockMvc.perform(delete(ENTITY_API_URL_ID, cpu.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cpu> cpuList = cpuRepository.findAll();
        assertThat(cpuList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
