package com.configme.web.rest.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.configme.IntegrationTest;
import com.configme.domain.Ram;
import com.configme.domain.enumeration.RamType;
import com.configme.repository.RamRepository;
import com.configme.web.rest.ProductResourceIT;
import com.configme.web.rest.RamResource;
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
 * Integration tests for the {@link RamResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RamResourceITUser {

    private static final RamType DEFAULT_TYPE = RamType.DDR3;
    private static final RamType UPDATED_TYPE = RamType.DDR4;

    private static final Float DEFAULT_FREQUENCY = 1F;
    private static final Float UPDATED_FREQUENCY = 2F;

    private static final Integer DEFAULT_UNIT_SIZE = 1;
    private static final Integer UPDATED_UNIT_SIZE = 2;

    private static final Integer DEFAULT_QUANTITY = 2;
    private static final Integer UPDATED_QUANTITY = 3;

    private static final String DEFAULT_CAS = "AAAAAAAAAA";
    private static final String UPDATED_CAS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rams";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RamRepository ramRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRamMockMvc;

    private Ram ram;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ram createEntity(EntityManager em) {
        Ram ram = new Ram()
            .type(DEFAULT_TYPE)
            .frequency(DEFAULT_FREQUENCY)
            .unitSize(DEFAULT_UNIT_SIZE)
            .quantity(DEFAULT_QUANTITY)
            .cas(DEFAULT_CAS);
        return ram;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ram createUpdatedEntity(EntityManager em) {
        Ram ram = new Ram()
            .type(UPDATED_TYPE)
            .frequency(UPDATED_FREQUENCY)
            .unitSize(UPDATED_UNIT_SIZE)
            .quantity(UPDATED_QUANTITY)
            .cas(UPDATED_CAS);
        return ram;
    }

    @BeforeEach
    public void initTest() {
        ram = createEntity(em);
    }

    @Test
    @Transactional
    void createRam() throws Exception {
        int databaseSizeBeforeCreate = ramRepository.findAll().size();
        // Create the Ram
        restRamMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ram)))
            .andExpect(status().is4xxClientError());

        // Validate the Ram in the database
        List<Ram> ramList = ramRepository.findAll();
        assertThat(ramList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRams() throws Exception {
        // Initialize the database
        ramRepository.saveAndFlush(ram);

        // Get all the ramList
        restRamMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ram.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].frequency").value(hasItem(DEFAULT_FREQUENCY.doubleValue())))
            .andExpect(jsonPath("$.[*].unitSize").value(hasItem(DEFAULT_UNIT_SIZE)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].cas").value(hasItem(DEFAULT_CAS)));
    }

    @Test
    @Transactional
    void getRam() throws Exception {
        // Initialize the database
        ramRepository.saveAndFlush(ram);

        // Get the ram
        restRamMockMvc
            .perform(get(ENTITY_API_URL_ID, ram.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ram.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.frequency").value(DEFAULT_FREQUENCY.doubleValue()))
            .andExpect(jsonPath("$.unitSize").value(DEFAULT_UNIT_SIZE))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.cas").value(DEFAULT_CAS));
    }

    @Test
    @Transactional
    void getNonExistingRam() throws Exception {
        // Get the ram
        restRamMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRam() throws Exception {
        // Initialize the database
        ramRepository.saveAndFlush(ram);

        int databaseSizeBeforeUpdate = ramRepository.findAll().size();

        // Update the ram
        Ram updatedRam = ramRepository.findById(ram.getId()).get();
        // Disconnect from session so that the updates on updatedRam are not directly saved in db
        em.detach(updatedRam);
        updatedRam.type(UPDATED_TYPE).frequency(UPDATED_FREQUENCY).unitSize(UPDATED_UNIT_SIZE).quantity(UPDATED_QUANTITY).cas(UPDATED_CAS);

        restRamMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRam.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRam))
            )
            .andExpect(status().is4xxClientError());

        // Validate the Ram in the database
        List<Ram> ramList = ramRepository.findAll();
        assertThat(ramList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRamWithPatch() throws Exception {
        // Initialize the database
        ramRepository.saveAndFlush(ram);

        int databaseSizeBeforeUpdate = ramRepository.findAll().size();

        // Update the ram using partial update
        Ram partialUpdatedRam = new Ram();
        partialUpdatedRam.setId(ram.getId());

        partialUpdatedRam.frequency(UPDATED_FREQUENCY).cas(UPDATED_CAS);

        restRamMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRam.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRam))
            )
            .andExpect(status().is4xxClientError());

        // Validate the Ram in the database
        List<Ram> ramList = ramRepository.findAll();
        assertThat(ramList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRam() throws Exception {
        // Initialize the database
        ramRepository.saveAndFlush(ram);

        int databaseSizeBeforeDelete = ramRepository.findAll().size();

        // Delete the ram
        restRamMockMvc
            .perform(delete(ENTITY_API_URL_ID, ram.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError());

        // Validate the database contains one less item
        List<Ram> ramList = ramRepository.findAll();
        assertThat(ramList).hasSize(databaseSizeBeforeDelete);
    }
}
