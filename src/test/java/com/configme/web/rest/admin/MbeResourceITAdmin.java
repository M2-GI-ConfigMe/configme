package com.configme.web.rest.admin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.configme.IntegrationTest;
import com.configme.domain.Mbe;
import com.configme.domain.enumeration.FormatType;
import com.configme.domain.enumeration.RamType;
import com.configme.domain.enumeration.SocketType;
import com.configme.repository.MbeRepository;
import com.configme.web.rest.MbeResource;
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
 * Integration tests for the {@link MbeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser(roles = { "ADMIN" })
class MbeResourceITAdmin {

    private static final SocketType DEFAULT_SOCKET_CPU = SocketType.AM4;
    private static final SocketType UPDATED_SOCKET_CPU = SocketType.TR4;

    private static final RamType DEFAULT_RAM_TYPE = RamType.DDR3;
    private static final RamType UPDATED_RAM_TYPE = RamType.DDR4;

    private static final Float DEFAULT_RAM_FREQUENCY_MAX = 1F;
    private static final Float UPDATED_RAM_FREQUENCY_MAX = 2F;

    private static final Float DEFAULT_RAM_SIZE_MAX = 1F;
    private static final Float UPDATED_RAM_SIZE_MAX = 2F;

    private static final String DEFAULT_PCI_OUTPUTS = "AAAAAAAAAA";
    private static final String UPDATED_PCI_OUTPUTS = "BBBBBBBBBB";

    private static final String DEFAULT_DISPLAY_OUTPUT = "AAAAAAAAAA";
    private static final String UPDATED_DISPLAY_OUTPUT = "BBBBBBBBBB";

    private static final String DEFAULT_STORAGE_OUTPUT = "AAAAAAAAAA";
    private static final String UPDATED_STORAGE_OUTPUT = "BBBBBBBBBB";

    private static final String DEFAULT_INSIDE_IO = "AAAAAAAAAA";
    private static final String UPDATED_INSIDE_IO = "BBBBBBBBBB";

    private static final String DEFAULT_BACK_PANEL_OUTPUT = "AAAAAAAAAA";
    private static final String UPDATED_BACK_PANEL_OUTPUT = "BBBBBBBBBB";

    private static final String DEFAULT_BIOS = "AAAAAAAAAA";
    private static final String UPDATED_BIOS = "BBBBBBBBBB";

    private static final FormatType DEFAULT_FORMAT = FormatType.ATX;
    private static final FormatType UPDATED_FORMAT = FormatType.FLEX_ATX;

    private static final String ENTITY_API_URL = "/api/mbes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MbeRepository mbeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMbeMockMvc;

    private Mbe mbe;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mbe createEntity(EntityManager em) {
        Mbe mbe = new Mbe()
            .socketCpu(DEFAULT_SOCKET_CPU)
            .ramType(DEFAULT_RAM_TYPE)
            .ramFrequencyMax(DEFAULT_RAM_FREQUENCY_MAX)
            .ramSizeMax(DEFAULT_RAM_SIZE_MAX)
            .pciOutputs(DEFAULT_PCI_OUTPUTS)
            .displayOutput(DEFAULT_DISPLAY_OUTPUT)
            .storageOutput(DEFAULT_STORAGE_OUTPUT)
            .insideIO(DEFAULT_INSIDE_IO)
            .backPanelOutput(DEFAULT_BACK_PANEL_OUTPUT)
            .bios(DEFAULT_BIOS)
            .format(DEFAULT_FORMAT);
        return mbe;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mbe createUpdatedEntity(EntityManager em) {
        Mbe mbe = new Mbe()
            .socketCpu(UPDATED_SOCKET_CPU)
            .ramType(UPDATED_RAM_TYPE)
            .ramFrequencyMax(UPDATED_RAM_FREQUENCY_MAX)
            .ramSizeMax(UPDATED_RAM_SIZE_MAX)
            .pciOutputs(UPDATED_PCI_OUTPUTS)
            .displayOutput(UPDATED_DISPLAY_OUTPUT)
            .storageOutput(UPDATED_STORAGE_OUTPUT)
            .insideIO(UPDATED_INSIDE_IO)
            .backPanelOutput(UPDATED_BACK_PANEL_OUTPUT)
            .bios(UPDATED_BIOS)
            .format(UPDATED_FORMAT);
        return mbe;
    }

    @BeforeEach
    public void initTest() {
        mbe = createEntity(em);
    }

    @Test
    @Transactional
    void createMbe() throws Exception {
        int databaseSizeBeforeCreate = mbeRepository.findAll().size();
        // Create the Mbe
        restMbeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mbe)))
            .andExpect(status().isCreated());

        // Validate the Mbe in the database
        List<Mbe> mbeList = mbeRepository.findAll();
        assertThat(mbeList).hasSize(databaseSizeBeforeCreate + 1);
        Mbe testMbe = mbeList.get(mbeList.size() - 1);
        assertThat(testMbe.getSocketCpu()).isEqualTo(DEFAULT_SOCKET_CPU);
        assertThat(testMbe.getRamType()).isEqualTo(DEFAULT_RAM_TYPE);
        assertThat(testMbe.getRamFrequencyMax()).isEqualTo(DEFAULT_RAM_FREQUENCY_MAX);
        assertThat(testMbe.getRamSizeMax()).isEqualTo(DEFAULT_RAM_SIZE_MAX);
        assertThat(testMbe.getPciOutputs()).isEqualTo(DEFAULT_PCI_OUTPUTS);
        assertThat(testMbe.getDisplayOutput()).isEqualTo(DEFAULT_DISPLAY_OUTPUT);
        assertThat(testMbe.getStorageOutput()).isEqualTo(DEFAULT_STORAGE_OUTPUT);
        assertThat(testMbe.getInsideIO()).isEqualTo(DEFAULT_INSIDE_IO);
        assertThat(testMbe.getBackPanelOutput()).isEqualTo(DEFAULT_BACK_PANEL_OUTPUT);
        assertThat(testMbe.getBios()).isEqualTo(DEFAULT_BIOS);
        assertThat(testMbe.getFormat()).isEqualTo(DEFAULT_FORMAT);
    }

    @Test
    @Transactional
    void createMbeWithExistingId() throws Exception {
        // Create the Mbe with an existing ID
        mbe.setId(1L);

        int databaseSizeBeforeCreate = mbeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMbeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mbe)))
            .andExpect(status().isBadRequest());

        // Validate the Mbe in the database
        List<Mbe> mbeList = mbeRepository.findAll();
        assertThat(mbeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkSocketCpuIsRequired() throws Exception {
        int databaseSizeBeforeTest = mbeRepository.findAll().size();
        // set the field null
        mbe.setSocketCpu(null);

        // Create the Mbe, which fails.

        restMbeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mbe)))
            .andExpect(status().isBadRequest());

        List<Mbe> mbeList = mbeRepository.findAll();
        assertThat(mbeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRamTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mbeRepository.findAll().size();
        // set the field null
        mbe.setRamType(null);

        // Create the Mbe, which fails.

        restMbeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mbe)))
            .andExpect(status().isBadRequest());

        List<Mbe> mbeList = mbeRepository.findAll();
        assertThat(mbeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRamFrequencyMaxIsRequired() throws Exception {
        int databaseSizeBeforeTest = mbeRepository.findAll().size();
        // set the field null
        mbe.setRamFrequencyMax(null);

        // Create the Mbe, which fails.

        restMbeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mbe)))
            .andExpect(status().isBadRequest());

        List<Mbe> mbeList = mbeRepository.findAll();
        assertThat(mbeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRamSizeMaxIsRequired() throws Exception {
        int databaseSizeBeforeTest = mbeRepository.findAll().size();
        // set the field null
        mbe.setRamSizeMax(null);

        // Create the Mbe, which fails.

        restMbeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mbe)))
            .andExpect(status().isBadRequest());

        List<Mbe> mbeList = mbeRepository.findAll();
        assertThat(mbeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPciOutputsIsRequired() throws Exception {
        int databaseSizeBeforeTest = mbeRepository.findAll().size();
        // set the field null
        mbe.setPciOutputs(null);

        // Create the Mbe, which fails.

        restMbeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mbe)))
            .andExpect(status().isBadRequest());

        List<Mbe> mbeList = mbeRepository.findAll();
        assertThat(mbeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFormatIsRequired() throws Exception {
        int databaseSizeBeforeTest = mbeRepository.findAll().size();
        // set the field null
        mbe.setFormat(null);

        // Create the Mbe, which fails.

        restMbeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mbe)))
            .andExpect(status().isBadRequest());

        List<Mbe> mbeList = mbeRepository.findAll();
        assertThat(mbeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMbes() throws Exception {
        // Initialize the database
        mbeRepository.saveAndFlush(mbe);

        // Get all the mbeList
        restMbeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mbe.getId().intValue())))
            .andExpect(jsonPath("$.[*].socketCpu").value(hasItem(DEFAULT_SOCKET_CPU.toString())))
            .andExpect(jsonPath("$.[*].ramType").value(hasItem(DEFAULT_RAM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].ramFrequencyMax").value(hasItem(DEFAULT_RAM_FREQUENCY_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].ramSizeMax").value(hasItem(DEFAULT_RAM_SIZE_MAX.doubleValue())))
            .andExpect(jsonPath("$.[*].pciOutputs").value(hasItem(DEFAULT_PCI_OUTPUTS)))
            .andExpect(jsonPath("$.[*].displayOutput").value(hasItem(DEFAULT_DISPLAY_OUTPUT)))
            .andExpect(jsonPath("$.[*].storageOutput").value(hasItem(DEFAULT_STORAGE_OUTPUT)))
            .andExpect(jsonPath("$.[*].insideIO").value(hasItem(DEFAULT_INSIDE_IO)))
            .andExpect(jsonPath("$.[*].backPanelOutput").value(hasItem(DEFAULT_BACK_PANEL_OUTPUT)))
            .andExpect(jsonPath("$.[*].bios").value(hasItem(DEFAULT_BIOS)))
            .andExpect(jsonPath("$.[*].format").value(hasItem(DEFAULT_FORMAT.toString())));
    }

    @Test
    @Transactional
    void getMbe() throws Exception {
        // Initialize the database
        mbeRepository.saveAndFlush(mbe);

        // Get the mbe
        restMbeMockMvc
            .perform(get(ENTITY_API_URL_ID, mbe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mbe.getId().intValue()))
            .andExpect(jsonPath("$.socketCpu").value(DEFAULT_SOCKET_CPU.toString()))
            .andExpect(jsonPath("$.ramType").value(DEFAULT_RAM_TYPE.toString()))
            .andExpect(jsonPath("$.ramFrequencyMax").value(DEFAULT_RAM_FREQUENCY_MAX.doubleValue()))
            .andExpect(jsonPath("$.ramSizeMax").value(DEFAULT_RAM_SIZE_MAX.doubleValue()))
            .andExpect(jsonPath("$.pciOutputs").value(DEFAULT_PCI_OUTPUTS))
            .andExpect(jsonPath("$.displayOutput").value(DEFAULT_DISPLAY_OUTPUT))
            .andExpect(jsonPath("$.storageOutput").value(DEFAULT_STORAGE_OUTPUT))
            .andExpect(jsonPath("$.insideIO").value(DEFAULT_INSIDE_IO))
            .andExpect(jsonPath("$.backPanelOutput").value(DEFAULT_BACK_PANEL_OUTPUT))
            .andExpect(jsonPath("$.bios").value(DEFAULT_BIOS))
            .andExpect(jsonPath("$.format").value(DEFAULT_FORMAT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingMbe() throws Exception {
        // Get the mbe
        restMbeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMbe() throws Exception {
        // Initialize the database
        mbeRepository.saveAndFlush(mbe);

        int databaseSizeBeforeUpdate = mbeRepository.findAll().size();

        // Update the mbe
        Mbe updatedMbe = mbeRepository.findById(mbe.getId()).get();
        // Disconnect from session so that the updates on updatedMbe are not directly saved in db
        em.detach(updatedMbe);
        updatedMbe
            .socketCpu(UPDATED_SOCKET_CPU)
            .ramType(UPDATED_RAM_TYPE)
            .ramFrequencyMax(UPDATED_RAM_FREQUENCY_MAX)
            .ramSizeMax(UPDATED_RAM_SIZE_MAX)
            .pciOutputs(UPDATED_PCI_OUTPUTS)
            .displayOutput(UPDATED_DISPLAY_OUTPUT)
            .storageOutput(UPDATED_STORAGE_OUTPUT)
            .insideIO(UPDATED_INSIDE_IO)
            .backPanelOutput(UPDATED_BACK_PANEL_OUTPUT)
            .bios(UPDATED_BIOS)
            .format(UPDATED_FORMAT);

        restMbeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMbe.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMbe))
            )
            .andExpect(status().isOk());

        // Validate the Mbe in the database
        List<Mbe> mbeList = mbeRepository.findAll();
        assertThat(mbeList).hasSize(databaseSizeBeforeUpdate);
        Mbe testMbe = mbeList.get(mbeList.size() - 1);
        assertThat(testMbe.getSocketCpu()).isEqualTo(UPDATED_SOCKET_CPU);
        assertThat(testMbe.getRamType()).isEqualTo(UPDATED_RAM_TYPE);
        assertThat(testMbe.getRamFrequencyMax()).isEqualTo(UPDATED_RAM_FREQUENCY_MAX);
        assertThat(testMbe.getRamSizeMax()).isEqualTo(UPDATED_RAM_SIZE_MAX);
        assertThat(testMbe.getPciOutputs()).isEqualTo(UPDATED_PCI_OUTPUTS);
        assertThat(testMbe.getDisplayOutput()).isEqualTo(UPDATED_DISPLAY_OUTPUT);
        assertThat(testMbe.getStorageOutput()).isEqualTo(UPDATED_STORAGE_OUTPUT);
        assertThat(testMbe.getInsideIO()).isEqualTo(UPDATED_INSIDE_IO);
        assertThat(testMbe.getBackPanelOutput()).isEqualTo(UPDATED_BACK_PANEL_OUTPUT);
        assertThat(testMbe.getBios()).isEqualTo(UPDATED_BIOS);
        assertThat(testMbe.getFormat()).isEqualTo(UPDATED_FORMAT);
    }

    @Test
    @Transactional
    void putNonExistingMbe() throws Exception {
        int databaseSizeBeforeUpdate = mbeRepository.findAll().size();
        mbe.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMbeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mbe.getId()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mbe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mbe in the database
        List<Mbe> mbeList = mbeRepository.findAll();
        assertThat(mbeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMbe() throws Exception {
        int databaseSizeBeforeUpdate = mbeRepository.findAll().size();
        mbe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMbeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mbe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mbe in the database
        List<Mbe> mbeList = mbeRepository.findAll();
        assertThat(mbeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMbe() throws Exception {
        int databaseSizeBeforeUpdate = mbeRepository.findAll().size();
        mbe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMbeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mbe)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Mbe in the database
        List<Mbe> mbeList = mbeRepository.findAll();
        assertThat(mbeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMbeWithPatch() throws Exception {
        // Initialize the database
        mbeRepository.saveAndFlush(mbe);

        int databaseSizeBeforeUpdate = mbeRepository.findAll().size();

        // Update the mbe using partial update
        Mbe partialUpdatedMbe = new Mbe();
        partialUpdatedMbe.setId(mbe.getId());

        partialUpdatedMbe
            .ramSizeMax(UPDATED_RAM_SIZE_MAX)
            .pciOutputs(UPDATED_PCI_OUTPUTS)
            .insideIO(UPDATED_INSIDE_IO)
            .backPanelOutput(UPDATED_BACK_PANEL_OUTPUT)
            .format(UPDATED_FORMAT);

        restMbeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMbe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMbe))
            )
            .andExpect(status().isOk());

        // Validate the Mbe in the database
        List<Mbe> mbeList = mbeRepository.findAll();
        assertThat(mbeList).hasSize(databaseSizeBeforeUpdate);
        Mbe testMbe = mbeList.get(mbeList.size() - 1);
        assertThat(testMbe.getSocketCpu()).isEqualTo(DEFAULT_SOCKET_CPU);
        assertThat(testMbe.getRamType()).isEqualTo(DEFAULT_RAM_TYPE);
        assertThat(testMbe.getRamFrequencyMax()).isEqualTo(DEFAULT_RAM_FREQUENCY_MAX);
        assertThat(testMbe.getRamSizeMax()).isEqualTo(UPDATED_RAM_SIZE_MAX);
        assertThat(testMbe.getPciOutputs()).isEqualTo(UPDATED_PCI_OUTPUTS);
        assertThat(testMbe.getDisplayOutput()).isEqualTo(DEFAULT_DISPLAY_OUTPUT);
        assertThat(testMbe.getStorageOutput()).isEqualTo(DEFAULT_STORAGE_OUTPUT);
        assertThat(testMbe.getInsideIO()).isEqualTo(UPDATED_INSIDE_IO);
        assertThat(testMbe.getBackPanelOutput()).isEqualTo(UPDATED_BACK_PANEL_OUTPUT);
        assertThat(testMbe.getBios()).isEqualTo(DEFAULT_BIOS);
        assertThat(testMbe.getFormat()).isEqualTo(UPDATED_FORMAT);
    }

    @Test
    @Transactional
    void fullUpdateMbeWithPatch() throws Exception {
        // Initialize the database
        mbeRepository.saveAndFlush(mbe);

        int databaseSizeBeforeUpdate = mbeRepository.findAll().size();

        // Update the mbe using partial update
        Mbe partialUpdatedMbe = new Mbe();
        partialUpdatedMbe.setId(mbe.getId());

        partialUpdatedMbe
            .socketCpu(UPDATED_SOCKET_CPU)
            .ramType(UPDATED_RAM_TYPE)
            .ramFrequencyMax(UPDATED_RAM_FREQUENCY_MAX)
            .ramSizeMax(UPDATED_RAM_SIZE_MAX)
            .pciOutputs(UPDATED_PCI_OUTPUTS)
            .displayOutput(UPDATED_DISPLAY_OUTPUT)
            .storageOutput(UPDATED_STORAGE_OUTPUT)
            .insideIO(UPDATED_INSIDE_IO)
            .backPanelOutput(UPDATED_BACK_PANEL_OUTPUT)
            .bios(UPDATED_BIOS)
            .format(UPDATED_FORMAT);

        restMbeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMbe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMbe))
            )
            .andExpect(status().isOk());

        // Validate the Mbe in the database
        List<Mbe> mbeList = mbeRepository.findAll();
        assertThat(mbeList).hasSize(databaseSizeBeforeUpdate);
        Mbe testMbe = mbeList.get(mbeList.size() - 1);
        assertThat(testMbe.getSocketCpu()).isEqualTo(UPDATED_SOCKET_CPU);
        assertThat(testMbe.getRamType()).isEqualTo(UPDATED_RAM_TYPE);
        assertThat(testMbe.getRamFrequencyMax()).isEqualTo(UPDATED_RAM_FREQUENCY_MAX);
        assertThat(testMbe.getRamSizeMax()).isEqualTo(UPDATED_RAM_SIZE_MAX);
        assertThat(testMbe.getPciOutputs()).isEqualTo(UPDATED_PCI_OUTPUTS);
        assertThat(testMbe.getDisplayOutput()).isEqualTo(UPDATED_DISPLAY_OUTPUT);
        assertThat(testMbe.getStorageOutput()).isEqualTo(UPDATED_STORAGE_OUTPUT);
        assertThat(testMbe.getInsideIO()).isEqualTo(UPDATED_INSIDE_IO);
        assertThat(testMbe.getBackPanelOutput()).isEqualTo(UPDATED_BACK_PANEL_OUTPUT);
        assertThat(testMbe.getBios()).isEqualTo(UPDATED_BIOS);
        assertThat(testMbe.getFormat()).isEqualTo(UPDATED_FORMAT);
    }

    @Test
    @Transactional
    void patchNonExistingMbe() throws Exception {
        int databaseSizeBeforeUpdate = mbeRepository.findAll().size();
        mbe.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMbeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mbe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mbe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mbe in the database
        List<Mbe> mbeList = mbeRepository.findAll();
        assertThat(mbeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMbe() throws Exception {
        int databaseSizeBeforeUpdate = mbeRepository.findAll().size();
        mbe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMbeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mbe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mbe in the database
        List<Mbe> mbeList = mbeRepository.findAll();
        assertThat(mbeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMbe() throws Exception {
        int databaseSizeBeforeUpdate = mbeRepository.findAll().size();
        mbe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMbeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(mbe)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Mbe in the database
        List<Mbe> mbeList = mbeRepository.findAll();
        assertThat(mbeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMbe() throws Exception {
        // Initialize the database
        mbeRepository.saveAndFlush(mbe);

        int databaseSizeBeforeDelete = mbeRepository.findAll().size();

        // Delete the mbe
        restMbeMockMvc.perform(delete(ENTITY_API_URL_ID, mbe.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Mbe> mbeList = mbeRepository.findAll();
        assertThat(mbeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
