package com.configme.web.rest.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.configme.IntegrationTest;
import com.configme.domain.Cpu;
import com.configme.domain.enumeration.SocketType;
import com.configme.repository.CpuRepository;
import com.configme.web.rest.CpuResource;
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
 * Integration tests for the {@link CpuResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser(roles = { "USER" })
public class CpuResourceITUser implements ProductResourceIT {

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
    public static Cpu createEntity() {
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

        ProductResourceIT.createProductField(cpu);
        return cpu;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cpu createUpdatedEntity() {
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

        ProductResourceIT.updateProductField(cpu);
        return cpu;
    }

    @BeforeEach
    public void initTest() {
        cpu = createEntity();
    }

    @Test
    @Transactional
    void createCpu() throws Exception {
        int databaseSizeBeforeCreate = cpuRepository.findAll().size();

        // Create the Cpu
        restCpuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cpu)))
            .andExpect(status().is4xxClientError());

        // We expect the CPU creation to fail as CREATE is not authorized for an user
        List<Cpu> cpuList = cpuRepository.findAll();
        assertThat(cpuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCpus() throws Exception {
        // Initialize the database
        cpuRepository.saveAndFlush(cpu);

        // Get all the cpuList
        var action = restCpuMockMvc.perform(get(ENTITY_API_URL + "?sort=id,desc"));

        action
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.content.[*].id").value(hasItem(cpu.getId().intValue())))
            .andExpect(jsonPath("$.content.[*].frequency").value(hasItem(DEFAULT_FREQUENCY.doubleValue())))
            .andExpect(jsonPath("$.content.[*].cacheL1").value(hasItem(DEFAULT_CACHE_L_1)))
            .andExpect(jsonPath("$.content.[*].cacheL2").value(hasItem(DEFAULT_CACHE_L_2)))
            .andExpect(jsonPath("$.content.[*].cacheL3").value(hasItem(DEFAULT_CACHE_L_3)))
            .andExpect(jsonPath("$.content.[*].nbHeart").value(hasItem(DEFAULT_NB_HEART)))
            .andExpect(jsonPath("$.content.[*].nbThread").value(hasItem(DEFAULT_NB_THREAD)))
            .andExpect(jsonPath("$.content.[*].hasVentirad").value(hasItem(DEFAULT_HAS_VENTIRAD.booleanValue())))
            .andExpect(jsonPath("$.content.[*].socketType").value(hasItem(DEFAULT_SOCKET_TYPE.toString())))
            .andExpect(jsonPath("$.content.[*].lithography").value(hasItem(DEFAULT_LITHOGRAPHY)))
            .andExpect(jsonPath("$.content.[*].ramFrequencyMax").value(hasItem(DEFAULT_RAM_FREQUENCY_MAX.doubleValue())))
            .andExpect(jsonPath("$.content.[*].consumption").value(hasItem(DEFAULT_CONSUMPTION)))
            .andExpect(jsonPath("$.content.[*].hasGpu").value(hasItem(DEFAULT_HAS_GPU.booleanValue())));

        getAllProductAssertProductField(action);
    }

    @Test
    @Transactional
    void getCpu() throws Exception {
        // Initialize the database
        cpuRepository.saveAndFlush(cpu);

        // Get the cpu
        var action = restCpuMockMvc.perform(get(ENTITY_API_URL_ID, cpu.getId()));

        action
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

        getProductAssertProductField(action);
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

        ProductResourceIT.updateProductField(updatedCpu);

        restCpuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCpu.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCpu))
            )
            .andExpect(status().is4xxClientError());

        // We expect the CPU update to fail as UPDATE is not authorized for an user
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

        partialUpdateField((partialUpdatedCpu));

        restCpuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCpu.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCpu))
            )
            .andExpect(status().is4xxClientError());

        // We expect the CPU creation to fail as PATCH is not authorized for an user
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
        restCpuMockMvc
            .perform(delete(ENTITY_API_URL_ID, cpu.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError());

        // We expect the CPU deletion to fail as DELETE is not authorized for an user
        List<Cpu> cpuList = cpuRepository.findAll();
        assertThat(cpuList).hasSize(databaseSizeBeforeDelete);
    }
}
