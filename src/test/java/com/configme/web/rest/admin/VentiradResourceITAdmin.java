package com.configme.web.rest.admin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.configme.IntegrationTest;
import com.configme.domain.Dimension;
import com.configme.domain.Product;
import com.configme.domain.Ventirad;
import com.configme.repository.ProductRepository;
import com.configme.repository.VentiradRepository;
import com.configme.web.rest.ProductResourceIT;
import com.configme.web.rest.TestUtil;
import com.configme.web.rest.VentiradResource;
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
@WithMockUser(roles = { "ADMIN" })
class VentiradResourceITAdmin implements ProductResourceIT {

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
    public static Ventirad createEntity() {
        Ventirad ventirad = new Ventirad()
            .rangeFanSpeed(DEFAULT_RANGE_FAN_SPEED)
            .noise(DEFAULT_NOISE)
            .hasThermalPaste(DEFAULT_HAS_THERMAL_PASTE)
            .dimension(DEFAULT_DIMENSION);

        ProductResourceIT.createProductField(ventirad);
        return ventirad;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ventirad createUpdatedEntity() {
        Ventirad ventirad = new Ventirad()
            .rangeFanSpeed(UPDATED_RANGE_FAN_SPEED)
            .noise(UPDATED_NOISE)
            .hasThermalPaste(UPDATED_HAS_THERMAL_PASTE)
            .dimension(UPDATED_DIMENSION);
        return ventirad;
    }

    @BeforeEach
    public void initTest() {
        ventirad = createEntity();
    }

    @Test
    @Transactional
    void createVentirad() throws Exception {
        int databaseSizeBeforeCreate = ventiradRepository.findAll().size();
        // Create the Ventirad
        restVentiradMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ventirad)))
            .andExpect(status().isCreated());

        // Validate the Ventirad in the database
        List<Ventirad> ventiradList = ventiradRepository.findAll();
        assertThat(ventiradList).hasSize(databaseSizeBeforeCreate + 1);
        Ventirad testVentirad = ventiradList.get(ventiradList.size() - 1);
        assertThat(testVentirad.getRangeFanSpeed()).isEqualTo(DEFAULT_RANGE_FAN_SPEED);
        assertThat(testVentirad.getNoise()).isEqualTo(DEFAULT_NOISE);
        assertThat(testVentirad.getHasThermalPaste()).isEqualTo(DEFAULT_HAS_THERMAL_PASTE);
        assertThat(testVentirad.getDimension()).isEqualTo(DEFAULT_DIMENSION);

        assertProductCreation(testVentirad);
    }

    @Test
    @Transactional
    void createVentiradWithExistingId() throws Exception {
        // Create the Ventirad with an existing ID
        ventirad.setId(1L);

        int databaseSizeBeforeCreate = ventiradRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVentiradMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ventirad)))
            .andExpect(status().isBadRequest());

        // Validate the Ventirad in the database
        List<Ventirad> ventiradList = ventiradRepository.findAll();
        assertThat(ventiradList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRangeFanSpeedIsRequired() throws Exception {
        int databaseSizeBeforeTest = ventiradRepository.findAll().size();
        // set the field null
        ventirad.setRangeFanSpeed(null);

        // Create the Ventirad, which fails.

        restVentiradMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ventirad)))
            .andExpect(status().isBadRequest());

        List<Ventirad> ventiradList = ventiradRepository.findAll();
        assertThat(ventiradList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHasThermalPasteIsRequired() throws Exception {
        int databaseSizeBeforeTest = ventiradRepository.findAll().size();
        // set the field null
        ventirad.setHasThermalPaste(null);

        // Create the Ventirad, which fails.

        restVentiradMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ventirad)))
            .andExpect(status().isBadRequest());

        List<Ventirad> ventiradList = ventiradRepository.findAll();
        assertThat(ventiradList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVentirads() throws Exception {
        // Initialize the database
        ventiradRepository.saveAndFlush(ventirad);

        // Get all the ventiradList
        var action = restVentiradMockMvc.perform(get(ENTITY_API_URL + "?sort=id,desc"));

        action
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.content.[*].id").value(hasItem(ventirad.getId().intValue())))
            .andExpect(jsonPath("$.content.[*].rangeFanSpeed").value(hasItem(DEFAULT_RANGE_FAN_SPEED)))
            .andExpect(jsonPath("$.content.[*].noise").value(hasItem(DEFAULT_NOISE)))
            .andExpect(jsonPath("$.content.[*].hasThermalPaste").value(hasItem(DEFAULT_HAS_THERMAL_PASTE.booleanValue())))
            .andExpect(jsonPath("$.content.[*].dimension.height").value(hasItem(DEFAULT_DIMENSION.getHeight())))
            .andExpect(jsonPath("$.content.[*].dimension.width").value(hasItem(DEFAULT_DIMENSION.getWidth())))
            .andExpect(jsonPath("$.content.[*].dimension.length").value(hasItem(DEFAULT_DIMENSION.getLength())));

        getAllProductAssertProductField(action);
    }

    @Test
    @Transactional
    void getVentirad() throws Exception {
        // Initialize the database
        ventiradRepository.saveAndFlush(ventirad);

        // Get the ventirad
        var actions = restVentiradMockMvc.perform(get(ENTITY_API_URL_ID, ventirad.getId()));

        actions
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ventirad.getId().intValue()))
            .andExpect(jsonPath("$.rangeFanSpeed").value(DEFAULT_RANGE_FAN_SPEED))
            .andExpect(jsonPath("$.noise").value(DEFAULT_NOISE))
            .andExpect(jsonPath("$.hasThermalPaste").value(DEFAULT_HAS_THERMAL_PASTE.booleanValue()))
            .andExpect(jsonPath("$.dimension").value(DEFAULT_DIMENSION));

        getProductAssertProductField(actions);
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

        ProductResourceIT.updateProductField(updatedVentirad);

        restVentiradMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedVentirad.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedVentirad))
            )
            .andExpect(status().isOk());

        // Validate the Ventirad in the database
        List<Ventirad> ventiradList = ventiradRepository.findAll();
        assertThat(ventiradList).hasSize(databaseSizeBeforeUpdate);
        Ventirad testVentirad = ventiradList.get(ventiradList.size() - 1);
        assertThat(testVentirad.getRangeFanSpeed()).isEqualTo(UPDATED_RANGE_FAN_SPEED);
        assertThat(testVentirad.getNoise()).isEqualTo(UPDATED_NOISE);
        assertThat(testVentirad.getHasThermalPaste()).isEqualTo(UPDATED_HAS_THERMAL_PASTE);
        assertThat(testVentirad.getDimension()).usingRecursiveComparison().isEqualTo(UPDATED_DIMENSION);

        assertProductUpdate(testVentirad);
    }

    @Test
    @Transactional
    void putNonExistingVentirad() throws Exception {
        int databaseSizeBeforeUpdate = ventiradRepository.findAll().size();
        ventirad.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVentiradMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ventirad.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ventirad))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ventirad in the database
        List<Ventirad> ventiradList = ventiradRepository.findAll();
        assertThat(ventiradList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVentirad() throws Exception {
        int databaseSizeBeforeUpdate = ventiradRepository.findAll().size();
        ventirad.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVentiradMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ventirad))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ventirad in the database
        List<Ventirad> ventiradList = ventiradRepository.findAll();
        assertThat(ventiradList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVentirad() throws Exception {
        int databaseSizeBeforeUpdate = ventiradRepository.findAll().size();
        ventirad.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVentiradMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ventirad)))
            .andExpect(status().isMethodNotAllowed());

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

        partialUpdateField(partialUpdatedVentirad);

        restVentiradMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVentirad.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVentirad))
            )
            .andExpect(status().isOk());

        // Validate the Ventirad in the database
        List<Ventirad> ventiradList = ventiradRepository.findAll();
        assertThat(ventiradList).hasSize(databaseSizeBeforeUpdate);
        Ventirad testVentirad = ventiradList.get(ventiradList.size() - 1);
        assertThat(testVentirad.getRangeFanSpeed()).isEqualTo(DEFAULT_RANGE_FAN_SPEED);
        assertThat(testVentirad.getNoise()).isEqualTo(DEFAULT_NOISE);
        assertThat(testVentirad.getHasThermalPaste()).isEqualTo(DEFAULT_HAS_THERMAL_PASTE);
        assertThat(testVentirad.getDimension()).isEqualTo(DEFAULT_DIMENSION);

        assertPartialUpdateField(testVentirad);
    }

    @Test
    @Transactional
    void fullUpdateVentiradWithPatch() throws Exception {
        // Initialize the database
        ventiradRepository.saveAndFlush(ventirad);

        int databaseSizeBeforeUpdate = ventiradRepository.findAll().size();

        // Update the ventirad using partial update
        Ventirad partialUpdatedVentirad = new Ventirad();
        partialUpdatedVentirad.setId(ventirad.getId());

        partialUpdatedVentirad
            .rangeFanSpeed(UPDATED_RANGE_FAN_SPEED)
            .noise(UPDATED_NOISE)
            .hasThermalPaste(UPDATED_HAS_THERMAL_PASTE)
            .dimension(UPDATED_DIMENSION);

        ProductResourceIT.updateProductField(partialUpdatedVentirad);

        restVentiradMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVentirad.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVentirad))
            )
            .andExpect(status().isOk());

        // Validate the Ventirad in the database
        List<Ventirad> ventiradList = ventiradRepository.findAll();
        assertThat(ventiradList).hasSize(databaseSizeBeforeUpdate);
        Ventirad testVentirad = ventiradList.get(ventiradList.size() - 1);
        assertThat(testVentirad.getRangeFanSpeed()).isEqualTo(UPDATED_RANGE_FAN_SPEED);
        assertThat(testVentirad.getNoise()).isEqualTo(UPDATED_NOISE);
        assertThat(testVentirad.getHasThermalPaste()).isEqualTo(UPDATED_HAS_THERMAL_PASTE);
        assertThat(testVentirad.getDimension()).isEqualTo(UPDATED_DIMENSION);

        assertProductUpdate(testVentirad);
    }

    @Test
    @Transactional
    void patchNonExistingVentirad() throws Exception {
        int databaseSizeBeforeUpdate = ventiradRepository.findAll().size();
        ventirad.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVentiradMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ventirad.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ventirad))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ventirad in the database
        List<Ventirad> ventiradList = ventiradRepository.findAll();
        assertThat(ventiradList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVentirad() throws Exception {
        int databaseSizeBeforeUpdate = ventiradRepository.findAll().size();
        ventirad.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVentiradMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ventirad))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ventirad in the database
        List<Ventirad> ventiradList = ventiradRepository.findAll();
        assertThat(ventiradList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVentirad() throws Exception {
        int databaseSizeBeforeUpdate = ventiradRepository.findAll().size();
        ventirad.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVentiradMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ventirad)))
            .andExpect(status().isMethodNotAllowed());

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
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ventirad> ventiradList = ventiradRepository.findAll();
        assertThat(ventiradList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    void testProductField(@Autowired ProductRepository productRepository, @Autowired MockMvc mockMvc) throws Exception {
        Product product = createEntity();
        testProductField(productRepository, mockMvc, product, ENTITY_API_URL);
    }
}
