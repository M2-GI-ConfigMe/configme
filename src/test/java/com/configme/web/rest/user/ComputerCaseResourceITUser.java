package com.configme.web.rest.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.configme.IntegrationTest;
import com.configme.domain.ComputerCase;
import com.configme.domain.Dimension;
import com.configme.domain.enumeration.CaseType;
import com.configme.domain.enumeration.FormatType;
import com.configme.repository.ComputerCaseRepository;
import com.configme.web.rest.ComputerCaseResource;
import com.configme.web.rest.ProductResourceIT;
import com.configme.web.rest.ProductResourceIT;
import com.configme.web.rest.TestUtil;
import com.configme.web.rest.TestUtil;
import java.util.ArrayList;
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
 * Integration tests for the {@link ComputerCaseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
public class ComputerCaseResourceITUser implements ProductResourceIT {

    private static final CaseType DEFAULT_TYPE = CaseType.PETITE;
    private static final CaseType UPDATED_TYPE = CaseType.MOYENNE;

    private static final List<FormatType> DEFAULT_FORMATS = new ArrayList<>() {
        {
            add(FormatType.ATX);
            add(FormatType.FLEX_ATX);
        }
    };
    private static final List<FormatType> UPDATED_FORMATS = new ArrayList<>() {
        {
            add(FormatType.FLEX_ATX);
            add(FormatType.MICRO_ATX);
        }
    };

    private static final Integer DEFAULT_SIZE_MAX_GPU = 1;
    private static final Integer UPDATED_SIZE_MAX_GPU = 2;

    private static final Integer DEFAULT_SIZE_MAX_VENTIRAD = 1;
    private static final Integer UPDATED_SIZE_MAX_VENTIRAD = 2;

    private static final Integer DEFAULT_SIZE_MAX_PSU = 1;
    private static final Integer UPDATED_SIZE_MAX_PSU = 2;

    private static final String DEFAULT_HARD_DRIVE_SLOTS = "AAAAAAAAAA";
    private static final String UPDATED_HARD_DRIVE_SLOTS = "BBBBBBBBBB";

    private static final String DEFAULT_FRONT_PANEL_OUTPUTS = "AAAAAAAAAA";
    private static final String UPDATED_FRONT_PANEL_OUTPUTS = "BBBBBBBBBB";

    private static final String DEFAULT_FAN_INCLUDED = "AAAAAAAAAA";
    private static final String UPDATED_FAN_INCLUDED = "BBBBBBBBBB";

    private static final String DEFAULT_FAN_SLOTS_AVAILABLE = "AAAAAAAAAA";
    private static final String UPDATED_FAN_SLOTS_AVAILABLE = "BBBBBBBBBB";

    private static final String DEFAULT_WATERCOOLING_COMPATIBILITY = "AAAAAAAAAA";
    private static final String UPDATED_WATERCOOLING_COMPATIBILITY = "BBBBBBBBBB";

    private static final Dimension DEFAULT_DIMENSION = new Dimension(51, 21, 26);
    private static final Dimension UPDATED_DIMENSION = new Dimension(75, 250, 3950);

    private static final String ENTITY_API_URL = "/api/computer-cases";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ComputerCaseRepository computerCaseRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restComputerCaseMockMvc;

    private ComputerCase computerCase;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComputerCase createEntity() {
        ComputerCase computerCase = new ComputerCase()
            .type(DEFAULT_TYPE)
            .formats(DEFAULT_FORMATS)
            .sizeMaxGpu(DEFAULT_SIZE_MAX_GPU)
            .sizeMaxVentirad(DEFAULT_SIZE_MAX_VENTIRAD)
            .sizeMaxPsu(DEFAULT_SIZE_MAX_PSU)
            .hardDriveSlots(DEFAULT_HARD_DRIVE_SLOTS)
            .frontPanelOutputs(DEFAULT_FRONT_PANEL_OUTPUTS)
            .fanIncluded(DEFAULT_FAN_INCLUDED)
            .fanSlotsAvailable(DEFAULT_FAN_SLOTS_AVAILABLE)
            .watercoolingCompatibility(DEFAULT_WATERCOOLING_COMPATIBILITY)
            .dimension(DEFAULT_DIMENSION);

        ProductResourceIT.createProductField(computerCase);
        return computerCase;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ComputerCase createUpdatedEntity() {
        ComputerCase computerCase = new ComputerCase()
            .type(UPDATED_TYPE)
            .formats(UPDATED_FORMATS)
            .sizeMaxGpu(UPDATED_SIZE_MAX_GPU)
            .sizeMaxVentirad(UPDATED_SIZE_MAX_VENTIRAD)
            .sizeMaxPsu(UPDATED_SIZE_MAX_PSU)
            .hardDriveSlots(UPDATED_HARD_DRIVE_SLOTS)
            .frontPanelOutputs(UPDATED_FRONT_PANEL_OUTPUTS)
            .fanIncluded(UPDATED_FAN_INCLUDED)
            .fanSlotsAvailable(UPDATED_FAN_SLOTS_AVAILABLE)
            .watercoolingCompatibility(UPDATED_WATERCOOLING_COMPATIBILITY)
            .dimension(UPDATED_DIMENSION);

        ProductResourceIT.updateProductField(computerCase);
        return computerCase;
    }

    @BeforeEach
    public void initTest() {
        computerCase = createEntity();
    }

    @Test
    @Transactional
    void createComputerCase() throws Exception {
        int databaseSizeBeforeCreate = computerCaseRepository.findAll().size();
        // Create the ComputerCase
        restComputerCaseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(computerCase)))
            .andExpect(status().is4xxClientError());

        // Validate the ComputerCase in the database
        List<ComputerCase> computerCaseList = computerCaseRepository.findAll();
        assertThat(computerCaseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllComputerCases() throws Exception {
        // Initialize the database
        computerCaseRepository.saveAndFlush(computerCase);

        // Get all the computerCaseList
        var action = restComputerCaseMockMvc.perform(get(ENTITY_API_URL + "?sort=id,desc"));

        action
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(computerCase.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            //            .andExpect(jsonPath("$.[*].formats").value(hasItem(DEFAULT_FORMATS)))
            .andExpect(jsonPath("$.[*].sizeMaxGpu").value(hasItem(DEFAULT_SIZE_MAX_GPU)))
            .andExpect(jsonPath("$.[*].sizeMaxVentirad").value(hasItem(DEFAULT_SIZE_MAX_VENTIRAD)))
            .andExpect(jsonPath("$.[*].sizeMaxPsu").value(hasItem(DEFAULT_SIZE_MAX_PSU)))
            .andExpect(jsonPath("$.[*].hardDriveSlots").value(hasItem(DEFAULT_HARD_DRIVE_SLOTS)))
            .andExpect(jsonPath("$.[*].frontPanelOutputs").value(hasItem(DEFAULT_FRONT_PANEL_OUTPUTS)))
            .andExpect(jsonPath("$.[*].fanIncluded").value(hasItem(DEFAULT_FAN_INCLUDED)))
            .andExpect(jsonPath("$.[*].fanSlotsAvailable").value(hasItem(DEFAULT_FAN_SLOTS_AVAILABLE)))
            .andExpect(jsonPath("$.[*].watercoolingCompatibility").value(hasItem(DEFAULT_WATERCOOLING_COMPATIBILITY)))
            .andExpect(jsonPath("$.[*].dimension.height").value(hasItem(DEFAULT_DIMENSION.getHeight())))
            .andExpect(jsonPath("$.[*].dimension.width").value(hasItem(DEFAULT_DIMENSION.getWidth())))
            .andExpect(jsonPath("$.[*].dimension.length").value(hasItem(DEFAULT_DIMENSION.getLength())));

        getAllProductAssertProductField(action);
    }

    @Test
    @Transactional
    void getComputerCase() throws Exception {
        // Initialize the database
        computerCaseRepository.saveAndFlush(computerCase);

        // Get the computerCase
        var action = restComputerCaseMockMvc.perform(get(ENTITY_API_URL_ID, computerCase.getId()));

        action
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(computerCase.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            //            .andExpect(jsonPath("$.formats").value(DEFAULT_FORMATS))
            .andExpect(jsonPath("$.sizeMaxGpu").value(DEFAULT_SIZE_MAX_GPU))
            .andExpect(jsonPath("$.sizeMaxVentirad").value(DEFAULT_SIZE_MAX_VENTIRAD))
            .andExpect(jsonPath("$.sizeMaxPsu").value(DEFAULT_SIZE_MAX_PSU))
            .andExpect(jsonPath("$.hardDriveSlots").value(DEFAULT_HARD_DRIVE_SLOTS))
            .andExpect(jsonPath("$.frontPanelOutputs").value(DEFAULT_FRONT_PANEL_OUTPUTS))
            .andExpect(jsonPath("$.fanIncluded").value(DEFAULT_FAN_INCLUDED))
            .andExpect(jsonPath("$.fanSlotsAvailable").value(DEFAULT_FAN_SLOTS_AVAILABLE))
            .andExpect(jsonPath("$.watercoolingCompatibility").value(DEFAULT_WATERCOOLING_COMPATIBILITY))
            .andExpect(jsonPath("$.dimension").value(DEFAULT_DIMENSION));

        getProductAssertProductField(action);
    }

    @Test
    @Transactional
    void getNonExistingComputerCase() throws Exception {
        // Get the computerCase
        restComputerCaseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewComputerCase() throws Exception {
        // Initialize the database
        computerCaseRepository.saveAndFlush(computerCase);

        int databaseSizeBeforeUpdate = computerCaseRepository.findAll().size();

        // Update the computerCase
        ComputerCase updatedComputerCase = computerCaseRepository.findById(computerCase.getId()).get();
        // Disconnect from session so that the updates on updatedComputerCase are not directly saved in db
        em.detach(updatedComputerCase);
        updatedComputerCase
            .type(UPDATED_TYPE)
            .formats(UPDATED_FORMATS)
            .sizeMaxGpu(UPDATED_SIZE_MAX_GPU)
            .sizeMaxVentirad(UPDATED_SIZE_MAX_VENTIRAD)
            .sizeMaxPsu(UPDATED_SIZE_MAX_PSU)
            .hardDriveSlots(UPDATED_HARD_DRIVE_SLOTS)
            .frontPanelOutputs(UPDATED_FRONT_PANEL_OUTPUTS)
            .fanIncluded(UPDATED_FAN_INCLUDED)
            .fanSlotsAvailable(UPDATED_FAN_SLOTS_AVAILABLE)
            .watercoolingCompatibility(UPDATED_WATERCOOLING_COMPATIBILITY)
            .dimension(UPDATED_DIMENSION);

        ProductResourceIT.updateProductField(updatedComputerCase);

        restComputerCaseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedComputerCase.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedComputerCase))
            )
            .andExpect(status().is4xxClientError());

        // Validate the ComputerCase in the database
        List<ComputerCase> computerCaseList = computerCaseRepository.findAll();
        assertThat(computerCaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateComputerCaseWithPatch() throws Exception {
        // Initialize the database
        computerCaseRepository.saveAndFlush(computerCase);

        int databaseSizeBeforeUpdate = computerCaseRepository.findAll().size();

        // Update the computerCase using partial update
        ComputerCase partialUpdatedComputerCase = new ComputerCase();
        partialUpdatedComputerCase.setId(computerCase.getId());

        partialUpdatedComputerCase
            .sizeMaxGpu(UPDATED_SIZE_MAX_GPU)
            .sizeMaxVentirad(UPDATED_SIZE_MAX_VENTIRAD)
            .sizeMaxPsu(UPDATED_SIZE_MAX_PSU)
            .hardDriveSlots(UPDATED_HARD_DRIVE_SLOTS)
            .frontPanelOutputs(UPDATED_FRONT_PANEL_OUTPUTS)
            .fanIncluded(UPDATED_FAN_INCLUDED)
            .fanSlotsAvailable(UPDATED_FAN_SLOTS_AVAILABLE)
            .watercoolingCompatibility(UPDATED_WATERCOOLING_COMPATIBILITY);

        partialUpdateField(partialUpdatedComputerCase);

        restComputerCaseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComputerCase.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedComputerCase))
            )
            .andExpect(status().is4xxClientError());

        // Validate the ComputerCase in the database
        List<ComputerCase> computerCaseList = computerCaseRepository.findAll();
        assertThat(computerCaseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteComputerCase() throws Exception {
        // Initialize the database
        computerCaseRepository.saveAndFlush(computerCase);

        int databaseSizeBeforeDelete = computerCaseRepository.findAll().size();

        // Delete the computerCase
        restComputerCaseMockMvc
            .perform(delete(ENTITY_API_URL_ID, computerCase.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError());

        // Validate the database contains one less item
        List<ComputerCase> computerCaseList = computerCaseRepository.findAll();
        assertThat(computerCaseList).hasSize(databaseSizeBeforeDelete);
    }
}
