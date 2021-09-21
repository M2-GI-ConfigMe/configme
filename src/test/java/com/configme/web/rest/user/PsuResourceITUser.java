package com.configme.web.rest.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.configme.IntegrationTest;
import com.configme.domain.Psu;
import com.configme.domain.enumeration.ModularityType;
import com.configme.repository.PsuRepository;
import com.configme.web.rest.ProductResourceIT;
import com.configme.web.rest.PsuResource;
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
 * Integration tests for the {@link PsuResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
public class PsuResourceITUser implements ProductResourceIT {

    private static final Integer DEFAULT_POWER = 1;
    private static final Integer UPDATED_POWER = 2;

    private static final String DEFAULT_CERTIFICATION = "AAAAAAAAAA";
    private static final String UPDATED_CERTIFICATION = "BBBBBBBBBB";

    private static final ModularityType DEFAULT_MODULARITY = ModularityType.NON_MODULAIRE;
    private static final ModularityType UPDATED_MODULARITY = ModularityType.SEMI_MODULAIRE;

    private static final Integer DEFAULT_NB_SATA = 1;
    private static final Integer UPDATED_NB_SATA = 2;

    private static final Integer DEFAULT_NB_PCI_E = 1;
    private static final Integer UPDATED_NB_PCI_E = 2;

    private static final String DEFAULT_OUTPUTS = "AAAAAAAAAA";
    private static final String UPDATED_OUTPUTS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/psus";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PsuRepository psuRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPsuMockMvc;

    private Psu psu;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Psu createEntity() {
        Psu psu = new Psu()
            .power(DEFAULT_POWER)
            .certification(DEFAULT_CERTIFICATION)
            .modularity(DEFAULT_MODULARITY)
            .nbSata(DEFAULT_NB_SATA)
            .nbPciE(DEFAULT_NB_PCI_E)
            .outputs(DEFAULT_OUTPUTS);

        ProductResourceIT.createProductField(psu);
        return psu;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Psu createUpdatedEntity() {
        Psu psu = new Psu()
            .power(UPDATED_POWER)
            .certification(UPDATED_CERTIFICATION)
            .modularity(UPDATED_MODULARITY)
            .nbSata(UPDATED_NB_SATA)
            .nbPciE(UPDATED_NB_PCI_E)
            .outputs(UPDATED_OUTPUTS);

        ProductResourceIT.updateProductField(psu);
        return psu;
    }

    @BeforeEach
    public void initTest() {
        psu = createEntity();
    }

    @Test
    @Transactional
    void createPsu() throws Exception {
        int databaseSizeBeforeCreate = psuRepository.findAll().size();
        // Create the Psu
        restPsuMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(psu)))
            .andExpect(status().is4xxClientError());

        // Validate the Psu in the database
        List<Psu> psuList = psuRepository.findAll();
        assertThat(psuList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPsus() throws Exception {
        // Initialize the database
        psuRepository.saveAndFlush(psu);

        // Get all the psuList
        var action = restPsuMockMvc.perform(get(ENTITY_API_URL + "?sort=id,desc"));

        action
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.content.[*].id").value(hasItem(psu.getId().intValue())))
            .andExpect(jsonPath("$.content.[*].power").value(hasItem(DEFAULT_POWER)))
            .andExpect(jsonPath("$.content.[*].certification").value(hasItem(DEFAULT_CERTIFICATION)))
            .andExpect(jsonPath("$.content.[*].modularity").value(hasItem(DEFAULT_MODULARITY.toString())))
            .andExpect(jsonPath("$.content.[*].nbSata").value(hasItem(DEFAULT_NB_SATA)))
            .andExpect(jsonPath("$.content.[*].nbPciE").value(hasItem(DEFAULT_NB_PCI_E)))
            .andExpect(jsonPath("$.content.[*].outputs").value(hasItem(DEFAULT_OUTPUTS)));

        getAllProductAssertProductField(action);
    }

    @Test
    @Transactional
    void getPsu() throws Exception {
        // Initialize the database
        psuRepository.saveAndFlush(psu);

        // Get the psu
        var action = restPsuMockMvc.perform(get(ENTITY_API_URL_ID, psu.getId()));

        action
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(psu.getId().intValue()))
            .andExpect(jsonPath("$.power").value(DEFAULT_POWER))
            .andExpect(jsonPath("$.certification").value(DEFAULT_CERTIFICATION))
            .andExpect(jsonPath("$.modularity").value(DEFAULT_MODULARITY.toString()))
            .andExpect(jsonPath("$.nbSata").value(DEFAULT_NB_SATA))
            .andExpect(jsonPath("$.nbPciE").value(DEFAULT_NB_PCI_E))
            .andExpect(jsonPath("$.outputs").value(DEFAULT_OUTPUTS));

        getProductAssertProductField(action);
    }

    @Test
    @Transactional
    void getNonExistingPsu() throws Exception {
        // Get the psu
        restPsuMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPsu() throws Exception {
        // Initialize the database
        psuRepository.saveAndFlush(psu);

        int databaseSizeBeforeUpdate = psuRepository.findAll().size();

        // Update the psu
        Psu updatedPsu = psuRepository.findById(psu.getId()).get();
        // Disconnect from session so that the updates on updatedPsu are not directly saved in db
        em.detach(updatedPsu);
        updatedPsu
            .power(UPDATED_POWER)
            .certification(UPDATED_CERTIFICATION)
            .modularity(UPDATED_MODULARITY)
            .nbSata(UPDATED_NB_SATA)
            .nbPciE(UPDATED_NB_PCI_E)
            .outputs(UPDATED_OUTPUTS);

        ProductResourceIT.updateProductField(updatedPsu);

        restPsuMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPsu.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPsu))
            )
            .andExpect(status().is4xxClientError());

        // Validate the Psu in the database
        List<Psu> psuList = psuRepository.findAll();
        assertThat(psuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void fullUpdatePsuWithPatch() throws Exception {
        // Initialize the database
        psuRepository.saveAndFlush(psu);

        int databaseSizeBeforeUpdate = psuRepository.findAll().size();

        // Update the psu using partial update
        Psu partialUpdatedPsu = new Psu();
        partialUpdatedPsu.setId(psu.getId());

        partialUpdatedPsu
            .power(UPDATED_POWER)
            .certification(UPDATED_CERTIFICATION)
            .modularity(UPDATED_MODULARITY)
            .nbSata(UPDATED_NB_SATA)
            .nbPciE(UPDATED_NB_PCI_E)
            .outputs(UPDATED_OUTPUTS);

        partialUpdateField(partialUpdatedPsu);

        restPsuMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPsu.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPsu))
            )
            .andExpect(status().is4xxClientError());

        // Validate the Psu in the database
        List<Psu> psuList = psuRepository.findAll();
        assertThat(psuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePsu() throws Exception {
        // Initialize the database
        psuRepository.saveAndFlush(psu);

        int databaseSizeBeforeDelete = psuRepository.findAll().size();

        // Delete the psu
        restPsuMockMvc
            .perform(delete(ENTITY_API_URL_ID, psu.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError());

        // Validate the database contains one less item
        List<Psu> psuList = psuRepository.findAll();
        assertThat(psuList).hasSize(databaseSizeBeforeDelete);
    }
}
