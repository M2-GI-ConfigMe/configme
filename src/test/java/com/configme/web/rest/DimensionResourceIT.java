package com.configme.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.configme.IntegrationTest;
import com.configme.domain.Dimension;
import com.configme.repository.DimensionRepository;
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
 * Integration tests for the {@link DimensionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DimensionResourceIT {

    private static final Integer DEFAULT_HEIGHT = 1;
    private static final Integer UPDATED_HEIGHT = 2;

    private static final Integer DEFAULT_WIDTH = 1;
    private static final Integer UPDATED_WIDTH = 2;

    private static final Integer DEFAULT_LENGTH = 1;
    private static final Integer UPDATED_LENGTH = 2;

    private static final String ENTITY_API_URL = "/api/dimensions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DimensionRepository dimensionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDimensionMockMvc;

    private Dimension dimension;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dimension createEntity(EntityManager em) {
        Dimension dimension = new Dimension().height(DEFAULT_HEIGHT).width(DEFAULT_WIDTH).length(DEFAULT_LENGTH);
        return dimension;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dimension createUpdatedEntity(EntityManager em) {
        Dimension dimension = new Dimension().height(UPDATED_HEIGHT).width(UPDATED_WIDTH).length(UPDATED_LENGTH);
        return dimension;
    }

    @BeforeEach
    public void initTest() {
        dimension = createEntity(em);
    }

    @Test
    @Transactional
    void createDimension() throws Exception {
        int databaseSizeBeforeCreate = dimensionRepository.findAll().size();
        // Create the Dimension
        restDimensionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dimension)))
            .andExpect(status().isCreated());

        // Validate the Dimension in the database
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeCreate + 1);
        Dimension testDimension = dimensionList.get(dimensionList.size() - 1);
        assertThat(testDimension.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testDimension.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testDimension.getLength()).isEqualTo(DEFAULT_LENGTH);
    }

    @Test
    @Transactional
    void createDimensionWithExistingId() throws Exception {
        // Create the Dimension with an existing ID
        dimension.setId(1L);

        int databaseSizeBeforeCreate = dimensionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDimensionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dimension)))
            .andExpect(status().isBadRequest());

        // Validate the Dimension in the database
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkHeightIsRequired() throws Exception {
        int databaseSizeBeforeTest = dimensionRepository.findAll().size();
        // set the field null
        dimension.setHeight(null);

        // Create the Dimension, which fails.

        restDimensionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dimension)))
            .andExpect(status().isBadRequest());

        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWidthIsRequired() throws Exception {
        int databaseSizeBeforeTest = dimensionRepository.findAll().size();
        // set the field null
        dimension.setWidth(null);

        // Create the Dimension, which fails.

        restDimensionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dimension)))
            .andExpect(status().isBadRequest());

        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLengthIsRequired() throws Exception {
        int databaseSizeBeforeTest = dimensionRepository.findAll().size();
        // set the field null
        dimension.setLength(null);

        // Create the Dimension, which fails.

        restDimensionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dimension)))
            .andExpect(status().isBadRequest());

        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDimensions() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get all the dimensionList
        restDimensionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dimension.getId().intValue())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT)))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH)))
            .andExpect(jsonPath("$.[*].length").value(hasItem(DEFAULT_LENGTH)));
    }

    @Test
    @Transactional
    void getDimension() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        // Get the dimension
        restDimensionMockMvc
            .perform(get(ENTITY_API_URL_ID, dimension.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dimension.getId().intValue()))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH))
            .andExpect(jsonPath("$.length").value(DEFAULT_LENGTH));
    }

    @Test
    @Transactional
    void getNonExistingDimension() throws Exception {
        // Get the dimension
        restDimensionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDimension() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        int databaseSizeBeforeUpdate = dimensionRepository.findAll().size();

        // Update the dimension
        Dimension updatedDimension = dimensionRepository.findById(dimension.getId()).get();
        // Disconnect from session so that the updates on updatedDimension are not directly saved in db
        em.detach(updatedDimension);
        updatedDimension.height(UPDATED_HEIGHT).width(UPDATED_WIDTH).length(UPDATED_LENGTH);

        restDimensionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDimension.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDimension))
            )
            .andExpect(status().isOk());

        // Validate the Dimension in the database
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeUpdate);
        Dimension testDimension = dimensionList.get(dimensionList.size() - 1);
        assertThat(testDimension.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testDimension.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testDimension.getLength()).isEqualTo(UPDATED_LENGTH);
    }

    @Test
    @Transactional
    void putNonExistingDimension() throws Exception {
        int databaseSizeBeforeUpdate = dimensionRepository.findAll().size();
        dimension.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDimensionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dimension.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dimension))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dimension in the database
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDimension() throws Exception {
        int databaseSizeBeforeUpdate = dimensionRepository.findAll().size();
        dimension.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDimensionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dimension))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dimension in the database
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDimension() throws Exception {
        int databaseSizeBeforeUpdate = dimensionRepository.findAll().size();
        dimension.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDimensionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dimension)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dimension in the database
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDimensionWithPatch() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        int databaseSizeBeforeUpdate = dimensionRepository.findAll().size();

        // Update the dimension using partial update
        Dimension partialUpdatedDimension = new Dimension();
        partialUpdatedDimension.setId(dimension.getId());

        restDimensionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDimension.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDimension))
            )
            .andExpect(status().isOk());

        // Validate the Dimension in the database
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeUpdate);
        Dimension testDimension = dimensionList.get(dimensionList.size() - 1);
        assertThat(testDimension.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testDimension.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testDimension.getLength()).isEqualTo(DEFAULT_LENGTH);
    }

    @Test
    @Transactional
    void fullUpdateDimensionWithPatch() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        int databaseSizeBeforeUpdate = dimensionRepository.findAll().size();

        // Update the dimension using partial update
        Dimension partialUpdatedDimension = new Dimension();
        partialUpdatedDimension.setId(dimension.getId());

        partialUpdatedDimension.height(UPDATED_HEIGHT).width(UPDATED_WIDTH).length(UPDATED_LENGTH);

        restDimensionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDimension.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDimension))
            )
            .andExpect(status().isOk());

        // Validate the Dimension in the database
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeUpdate);
        Dimension testDimension = dimensionList.get(dimensionList.size() - 1);
        assertThat(testDimension.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testDimension.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testDimension.getLength()).isEqualTo(UPDATED_LENGTH);
    }

    @Test
    @Transactional
    void patchNonExistingDimension() throws Exception {
        int databaseSizeBeforeUpdate = dimensionRepository.findAll().size();
        dimension.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDimensionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dimension.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dimension))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dimension in the database
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDimension() throws Exception {
        int databaseSizeBeforeUpdate = dimensionRepository.findAll().size();
        dimension.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDimensionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dimension))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dimension in the database
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDimension() throws Exception {
        int databaseSizeBeforeUpdate = dimensionRepository.findAll().size();
        dimension.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDimensionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dimension))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dimension in the database
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDimension() throws Exception {
        // Initialize the database
        dimensionRepository.saveAndFlush(dimension);

        int databaseSizeBeforeDelete = dimensionRepository.findAll().size();

        // Delete the dimension
        restDimensionMockMvc
            .perform(delete(ENTITY_API_URL_ID, dimension.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Dimension> dimensionList = dimensionRepository.findAll();
        assertThat(dimensionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
