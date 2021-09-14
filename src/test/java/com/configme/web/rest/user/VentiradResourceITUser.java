package com.configme.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.configme.IntegrationTest;
import com.configme.domain.Dimension;
import com.configme.domain.Ventirad;
import com.configme.repository.VentiradRepository;
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
 * Integration tests for the {@link VentiradResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VentiradResourceITUser {

    private static final String DEFAULT_RANGE_FAN_SPEED = "AAAAAAAAAA";
    private static final String UPDATED_RANGE_FAN_SPEED = "BBBBBBBBBB";

    private static final Integer DEFAULT_NOISE = 1;
    private static final Integer UPDATED_NOISE = 2;

    private static final Boolean DEFAULT_HAS_THERMAL_PASTE = false;
    private static final Boolean UPDATED_HAS_THERMAL_PASTE = true;

    private static final String ENTITY_API_URL = "/api/ventirads";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static final Dimension DEFAULT_DIMENSION = new Dimension(51, 21, 26);
    private static final Dimension UPDATED_DIMENSION = new Dimension(75, 250, 3950);

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VentiradRepository ventiradRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVentiradMockMvc;

    private Ventirad ventirad;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ventirad createEntity(EntityManager em) {
        Ventirad ventirad = new Ventirad()
            .rangeFanSpeed(DEFAULT_RANGE_FAN_SPEED)
            .noise(DEFAULT_NOISE)
            .hasThermalPaste(DEFAULT_HAS_THERMAL_PASTE)
            .dimension(DEFAULT_DIMENSION);
        return ventirad;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ventirad createUpdatedEntity(EntityManager em) {
        Ventirad ventirad = new Ventirad()
            .rangeFanSpeed(UPDATED_RANGE_FAN_SPEED)
            .noise(UPDATED_NOISE)
            .hasThermalPaste(UPDATED_HAS_THERMAL_PASTE)
            .dimension(UPDATED_DIMENSION);
        return ventirad;
    }

    @BeforeEach
    public void initTest() {
        ventirad = createEntity(em);
    }

    @Test
    @Transactional
    void createVentirad() throws Exception {
        int databaseSizeBeforeCreate = ventiradRepository.findAll().size();
        // Create the Ventirad
        restVentiradMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ventirad)))
            .andExpect(status().is4xxClientError());

        // Validate the Ventirad in the database
        List<Ventirad> ventiradList = ventiradRepository.findAll();
        assertThat(ventiradList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllVentirads() throws Exception {
        // Initialize the database
        ventiradRepository.saveAndFlush(ventirad);

        // Get all the ventiradList
        restVentiradMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ventirad.getId().intValue())))
            .andExpect(jsonPath("$.[*].rangeFanSpeed").value(hasItem(DEFAULT_RANGE_FAN_SPEED)))
            .andExpect(jsonPath("$.[*].noise").value(hasItem(DEFAULT_NOISE)))
            .andExpect(jsonPath("$.[*].hasThermalPaste").value(hasItem(DEFAULT_HAS_THERMAL_PASTE.booleanValue())))
            .andExpect(jsonPath("$.[*].dimension.height").value(hasItem(DEFAULT_DIMENSION.getHeight())))
            .andExpect(jsonPath("$.[*].dimension.width").value(hasItem(DEFAULT_DIMENSION.getWidth())))
            .andExpect(jsonPath("$.[*].dimension.length").value(hasItem(DEFAULT_DIMENSION.getLength())));
    }

    @Test
    @Transactional
    void getVentirad() throws Exception {
        // Initialize the database
        ventiradRepository.saveAndFlush(ventirad);

        // Get the ventirad
        restVentiradMockMvc
            .perform(get(ENTITY_API_URL_ID, ventirad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ventirad.getId().intValue()))
            .andExpect(jsonPath("$.rangeFanSpeed").value(DEFAULT_RANGE_FAN_SPEED))
            .andExpect(jsonPath("$.noise").value(DEFAULT_NOISE))
            .andExpect(jsonPath("$.hasThermalPaste").value(DEFAULT_HAS_THERMAL_PASTE.booleanValue()))
            .andExpect(jsonPath("$.dimension").value(DEFAULT_DIMENSION));
    }

    @Test
    @Transactional
    void getNonExistingVentirad() throws Exception {
        // Get the ventirad
        restVentiradMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVentirad() throws Exception {
        // Initialize the database
        ventiradRepository.saveAndFlush(ventirad);

        int databaseSizeBeforeUpdate = ventiradRepository.findAll().size();

        // Update the ventirad
        Ventirad updatedVentirad = ventiradRepository.findById(ventirad.getId()).get();
        // Disconnect from session so that the updates on updatedVentirad are not directly saved in db
        em.detach(updatedVentirad);
        updatedVentirad
            .rangeFanSpeed(UPDATED_RANGE_FAN_SPEED)
            .noise(UPDATED_NOISE)
            .hasThermalPaste(UPDATED_HAS_THERMAL_PASTE)
            .dimension(UPDATED_DIMENSION);

        restVentiradMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVentirad.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedVentirad))
            )
            .andExpect(status().is4xxClientError());

        // Validate the Ventirad in the database
        List<Ventirad> ventiradList = ventiradRepository.findAll();
        assertThat(ventiradList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVentiradWithPatch() throws Exception {
        // Initialize the database
        ventiradRepository.saveAndFlush(ventirad);

        int databaseSizeBeforeUpdate = ventiradRepository.findAll().size();

        // Update the ventirad using partial update
        Ventirad partialUpdatedVentirad = new Ventirad();
        partialUpdatedVentirad.setId(ventirad.getId());

        restVentiradMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVentirad.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVentirad))
            )
            .andExpect(status().is4xxClientError());

        // Validate the Ventirad in the database
        List<Ventirad> ventiradList = ventiradRepository.findAll();
        assertThat(ventiradList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVentirad() throws Exception {
        // Initialize the database
        ventiradRepository.saveAndFlush(ventirad);

        int databaseSizeBeforeDelete = ventiradRepository.findAll().size();

        // Delete the ventirad
        restVentiradMockMvc
            .perform(delete(ENTITY_API_URL_ID, ventirad.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError());

        // Validate the database contains one less item
        List<Ventirad> ventiradList = ventiradRepository.findAll();
        assertThat(ventiradList).hasSize(databaseSizeBeforeDelete);
    }
}