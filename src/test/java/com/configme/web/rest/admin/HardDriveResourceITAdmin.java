package com.configme.web.rest.admin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.configme.IntegrationTest;
import com.configme.domain.HardDrive;
import com.configme.domain.enumeration.MemoryType;
import com.configme.repository.HardDriveRepository;
import com.configme.web.rest.*;
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
 * Integration tests for the {@link HardDriveResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser(roles = { "ADMIN" })
class HardDriveResourceITAdmin {

    private static final Integer DEFAULT_CAPACITY = 1;
    private static final Integer UPDATED_CAPACITY = 2;

    private static final Float DEFAULT_SPEED_WRITE = 1F;
    private static final Float UPDATED_SPEED_WRITE = 2F;

    private static final Float DEFAULT_SPEED_READ = 1F;
    private static final Float UPDATED_SPEED_READ = 2F;

    private static final MemoryType DEFAULT_TYPE = MemoryType.HDD;
    private static final MemoryType UPDATED_TYPE = MemoryType.SSD;

    private static final String ENTITY_API_URL = "/api/hard-drives";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private HardDriveRepository hardDriveRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restHardDriveMockMvc;

    private HardDrive hardDrive;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HardDrive createEntity(EntityManager em) {
        HardDrive hardDrive = new HardDrive()
            .capacity(DEFAULT_CAPACITY)
            .speedWrite(DEFAULT_SPEED_WRITE)
            .speedRead(DEFAULT_SPEED_READ)
            .type(DEFAULT_TYPE);

        ProductResourceIT.createProductField(hardDrive);
        return hardDrive;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HardDrive createUpdatedEntity(EntityManager em) {
        HardDrive hardDrive = new HardDrive()
            .capacity(UPDATED_CAPACITY)
            .speedWrite(UPDATED_SPEED_WRITE)
            .speedRead(UPDATED_SPEED_READ)
            .type(UPDATED_TYPE);

        ProductResourceIT.updateProductField(hardDrive);
        return hardDrive;
    }

    @BeforeEach
    public void initTest() {
        hardDrive = createEntity(em);
    }

    @Test
    @Transactional
    void createHardDrive() throws Exception {
        int databaseSizeBeforeCreate = hardDriveRepository.findAll().size();
        // Create the HardDrive
        restHardDriveMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hardDrive)))
            .andExpect(status().isCreated());

        // Validate the HardDrive in the database
        List<HardDrive> hardDriveList = hardDriveRepository.findAll();
        assertThat(hardDriveList).hasSize(databaseSizeBeforeCreate + 1);
        HardDrive testHardDrive = hardDriveList.get(hardDriveList.size() - 1);
        assertThat(testHardDrive.getCapacity()).isEqualTo(DEFAULT_CAPACITY);
        assertThat(testHardDrive.getSpeedWrite()).isEqualTo(DEFAULT_SPEED_WRITE);
        assertThat(testHardDrive.getSpeedRead()).isEqualTo(DEFAULT_SPEED_READ);
        assertThat(testHardDrive.getType()).isEqualTo(DEFAULT_TYPE);

        ProductResourceIT.assertProductCreation(testHardDrive);
    }

    @Test
    @Transactional
    void createHardDriveWithExistingId() throws Exception {
        // Create the HardDrive with an existing ID
        hardDrive.setId(1L);

        int databaseSizeBeforeCreate = hardDriveRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restHardDriveMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hardDrive)))
            .andExpect(status().isBadRequest());

        // Validate the HardDrive in the database
        List<HardDrive> hardDriveList = hardDriveRepository.findAll();
        assertThat(hardDriveList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCapacityIsRequired() throws Exception {
        int databaseSizeBeforeTest = hardDriveRepository.findAll().size();
        // set the field null
        hardDrive.setCapacity(null);

        // Create the HardDrive, which fails.

        restHardDriveMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hardDrive)))
            .andExpect(status().isBadRequest());

        List<HardDrive> hardDriveList = hardDriveRepository.findAll();
        assertThat(hardDriveList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSpeedWriteIsRequired() throws Exception {
        int databaseSizeBeforeTest = hardDriveRepository.findAll().size();
        // set the field null
        hardDrive.setSpeedWrite(null);

        // Create the HardDrive, which fails.

        restHardDriveMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hardDrive)))
            .andExpect(status().isBadRequest());

        List<HardDrive> hardDriveList = hardDriveRepository.findAll();
        assertThat(hardDriveList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSpeedReadIsRequired() throws Exception {
        int databaseSizeBeforeTest = hardDriveRepository.findAll().size();
        // set the field null
        hardDrive.setSpeedRead(null);

        // Create the HardDrive, which fails.

        restHardDriveMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hardDrive)))
            .andExpect(status().isBadRequest());

        List<HardDrive> hardDriveList = hardDriveRepository.findAll();
        assertThat(hardDriveList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = hardDriveRepository.findAll().size();
        // set the field null
        hardDrive.setType(null);

        // Create the HardDrive, which fails.

        restHardDriveMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hardDrive)))
            .andExpect(status().isBadRequest());

        List<HardDrive> hardDriveList = hardDriveRepository.findAll();
        assertThat(hardDriveList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllHardDrives() throws Exception {
        // Initialize the database
        hardDriveRepository.saveAndFlush(hardDrive);

        // Get all the hardDriveList
        var action = restHardDriveMockMvc.perform(get(ENTITY_API_URL + "?sort=id,desc"));

        action
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hardDrive.getId().intValue())))
            .andExpect(jsonPath("$.[*].capacity").value(hasItem(DEFAULT_CAPACITY)))
            .andExpect(jsonPath("$.[*].speedWrite").value(hasItem(DEFAULT_SPEED_WRITE.doubleValue())))
            .andExpect(jsonPath("$.[*].speedRead").value(hasItem(DEFAULT_SPEED_READ.doubleValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));

        ProductResourceIT.getAllProductAssertProductField(action);
    }

    @Test
    @Transactional
    void getHardDrive() throws Exception {
        // Initialize the database
        hardDriveRepository.saveAndFlush(hardDrive);

        // Get the hardDrive
        var actions = restHardDriveMockMvc.perform(get(ENTITY_API_URL_ID, hardDrive.getId()));

        actions
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(hardDrive.getId().intValue()))
            .andExpect(jsonPath("$.capacity").value(DEFAULT_CAPACITY))
            .andExpect(jsonPath("$.speedWrite").value(DEFAULT_SPEED_WRITE.doubleValue()))
            .andExpect(jsonPath("$.speedRead").value(DEFAULT_SPEED_READ.doubleValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));

        ProductResourceIT.getProductAssertProductField(actions);
    }

    @Test
    @Transactional
    void getNonExistingHardDrive() throws Exception {
        // Get the hardDrive
        restHardDriveMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewHardDrive() throws Exception {
        // Initialize the database
        hardDriveRepository.saveAndFlush(hardDrive);

        int databaseSizeBeforeUpdate = hardDriveRepository.findAll().size();

        // Update the hardDrive
        HardDrive updatedHardDrive = hardDriveRepository.findById(hardDrive.getId()).get();
        // Disconnect from session so that the updates on updatedHardDrive are not directly saved in db
        em.detach(updatedHardDrive);
        updatedHardDrive.capacity(UPDATED_CAPACITY).speedWrite(UPDATED_SPEED_WRITE).speedRead(UPDATED_SPEED_READ).type(UPDATED_TYPE);

        ProductResourceIT.updateProductField(updatedHardDrive);

        restHardDriveMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedHardDrive.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedHardDrive))
            )
            .andExpect(status().isOk());

        // Validate the HardDrive in the database
        List<HardDrive> hardDriveList = hardDriveRepository.findAll();
        assertThat(hardDriveList).hasSize(databaseSizeBeforeUpdate);
        HardDrive testHardDrive = hardDriveList.get(hardDriveList.size() - 1);
        assertThat(testHardDrive.getCapacity()).isEqualTo(UPDATED_CAPACITY);
        assertThat(testHardDrive.getSpeedWrite()).isEqualTo(UPDATED_SPEED_WRITE);
        assertThat(testHardDrive.getSpeedRead()).isEqualTo(UPDATED_SPEED_READ);
        assertThat(testHardDrive.getType()).isEqualTo(UPDATED_TYPE);

        ProductResourceIT.assertProductUpdate(testHardDrive);
    }

    @Test
    @Transactional
    void putNonExistingHardDrive() throws Exception {
        int databaseSizeBeforeUpdate = hardDriveRepository.findAll().size();
        hardDrive.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHardDriveMockMvc
            .perform(
                put(ENTITY_API_URL_ID, hardDrive.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hardDrive))
            )
            .andExpect(status().isBadRequest());

        // Validate the HardDrive in the database
        List<HardDrive> hardDriveList = hardDriveRepository.findAll();
        assertThat(hardDriveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchHardDrive() throws Exception {
        int databaseSizeBeforeUpdate = hardDriveRepository.findAll().size();
        hardDrive.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHardDriveMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(hardDrive))
            )
            .andExpect(status().isBadRequest());

        // Validate the HardDrive in the database
        List<HardDrive> hardDriveList = hardDriveRepository.findAll();
        assertThat(hardDriveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamHardDrive() throws Exception {
        int databaseSizeBeforeUpdate = hardDriveRepository.findAll().size();
        hardDrive.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHardDriveMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(hardDrive)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the HardDrive in the database
        List<HardDrive> hardDriveList = hardDriveRepository.findAll();
        assertThat(hardDriveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateHardDriveWithPatch() throws Exception {
        // Initialize the database
        hardDriveRepository.saveAndFlush(hardDrive);

        int databaseSizeBeforeUpdate = hardDriveRepository.findAll().size();

        // Update the hardDrive using partial update
        HardDrive partialUpdatedHardDrive = new HardDrive();
        partialUpdatedHardDrive.setId(hardDrive.getId());

        partialUpdatedHardDrive.capacity(UPDATED_CAPACITY).speedWrite(UPDATED_SPEED_WRITE);

        ProductResourceIT.partialUpdateField(partialUpdatedHardDrive);

        restHardDriveMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHardDrive.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHardDrive))
            )
            .andExpect(status().isOk());

        // Validate the HardDrive in the database
        List<HardDrive> hardDriveList = hardDriveRepository.findAll();
        assertThat(hardDriveList).hasSize(databaseSizeBeforeUpdate);
        HardDrive testHardDrive = hardDriveList.get(hardDriveList.size() - 1);
        assertThat(testHardDrive.getCapacity()).isEqualTo(UPDATED_CAPACITY);
        assertThat(testHardDrive.getSpeedWrite()).isEqualTo(UPDATED_SPEED_WRITE);
        assertThat(testHardDrive.getSpeedRead()).isEqualTo(DEFAULT_SPEED_READ);
        assertThat(testHardDrive.getType()).isEqualTo(DEFAULT_TYPE);

        ProductResourceIT.assertPartialUpdateField(testHardDrive);
    }

    @Test
    @Transactional
    void fullUpdateHardDriveWithPatch() throws Exception {
        // Initialize the database
        hardDriveRepository.saveAndFlush(hardDrive);

        int databaseSizeBeforeUpdate = hardDriveRepository.findAll().size();

        // Update the hardDrive using partial update
        HardDrive partialUpdatedHardDrive = new HardDrive();
        partialUpdatedHardDrive.setId(hardDrive.getId());

        partialUpdatedHardDrive.capacity(UPDATED_CAPACITY).speedWrite(UPDATED_SPEED_WRITE).speedRead(UPDATED_SPEED_READ).type(UPDATED_TYPE);

        ProductResourceIT.updateProductField(partialUpdatedHardDrive);

        restHardDriveMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedHardDrive.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedHardDrive))
            )
            .andExpect(status().isOk());

        // Validate the HardDrive in the database
        List<HardDrive> hardDriveList = hardDriveRepository.findAll();
        assertThat(hardDriveList).hasSize(databaseSizeBeforeUpdate);
        HardDrive testHardDrive = hardDriveList.get(hardDriveList.size() - 1);
        assertThat(testHardDrive.getCapacity()).isEqualTo(UPDATED_CAPACITY);
        assertThat(testHardDrive.getSpeedWrite()).isEqualTo(UPDATED_SPEED_WRITE);
        assertThat(testHardDrive.getSpeedRead()).isEqualTo(UPDATED_SPEED_READ);
        assertThat(testHardDrive.getType()).isEqualTo(UPDATED_TYPE);

        ProductResourceIT.assertProductUpdate(testHardDrive);
    }

    @Test
    @Transactional
    void patchNonExistingHardDrive() throws Exception {
        int databaseSizeBeforeUpdate = hardDriveRepository.findAll().size();
        hardDrive.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHardDriveMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, hardDrive.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hardDrive))
            )
            .andExpect(status().isBadRequest());

        // Validate the HardDrive in the database
        List<HardDrive> hardDriveList = hardDriveRepository.findAll();
        assertThat(hardDriveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchHardDrive() throws Exception {
        int databaseSizeBeforeUpdate = hardDriveRepository.findAll().size();
        hardDrive.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHardDriveMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(hardDrive))
            )
            .andExpect(status().isBadRequest());

        // Validate the HardDrive in the database
        List<HardDrive> hardDriveList = hardDriveRepository.findAll();
        assertThat(hardDriveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamHardDrive() throws Exception {
        int databaseSizeBeforeUpdate = hardDriveRepository.findAll().size();
        hardDrive.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restHardDriveMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(hardDrive))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the HardDrive in the database
        List<HardDrive> hardDriveList = hardDriveRepository.findAll();
        assertThat(hardDriveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteHardDrive() throws Exception {
        // Initialize the database
        hardDriveRepository.saveAndFlush(hardDrive);

        int databaseSizeBeforeDelete = hardDriveRepository.findAll().size();

        // Delete the hardDrive
        restHardDriveMockMvc
            .perform(delete(ENTITY_API_URL_ID, hardDrive.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HardDrive> hardDriveList = hardDriveRepository.findAll();
        assertThat(hardDriveList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
