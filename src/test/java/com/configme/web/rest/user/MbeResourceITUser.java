package com.configme.web.rest.user;

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
@WithMockUser
class MbeResourceITUser implements ProductResourceIT {

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
    public static Mbe createEntity() {
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

        ProductResourceIT.createProductField(mbe);
        return mbe;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mbe createUpdatedEntity() {
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

        ProductResourceIT.updateProductField(mbe);
        return mbe;
    }

    @BeforeEach
    public void initTest() {
        mbe = createEntity();
    }

    @Test
    @Transactional
    void createMbe() throws Exception {
        int databaseSizeBeforeCreate = mbeRepository.findAll().size();
        // Create the Mbe
        restMbeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mbe)))
            .andExpect(status().is4xxClientError());

        // Validate the Mbe in the database
        List<Mbe> mbeList = mbeRepository.findAll();
        assertThat(mbeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMbes() throws Exception {
        // Initialize the database
        mbeRepository.saveAndFlush(mbe);

        // Get all the mbeList
        var action = restMbeMockMvc.perform(get(ENTITY_API_URL + "?sort=id,desc"));

        action
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.content.[*].id").value(hasItem(mbe.getId().intValue())))
            .andExpect(jsonPath("$.content.[*].socketCpu").value(hasItem(DEFAULT_SOCKET_CPU.toString())))
            .andExpect(jsonPath("$.content.[*].ramType").value(hasItem(DEFAULT_RAM_TYPE.toString())))
            .andExpect(jsonPath("$.content.[*].ramFrequencyMax").value(hasItem(DEFAULT_RAM_FREQUENCY_MAX.doubleValue())))
            .andExpect(jsonPath("$.content.[*].ramSizeMax").value(hasItem(DEFAULT_RAM_SIZE_MAX.doubleValue())))
            .andExpect(jsonPath("$.content.[*].pciOutputs").value(hasItem(DEFAULT_PCI_OUTPUTS)))
            .andExpect(jsonPath("$.content.[*].displayOutput").value(hasItem(DEFAULT_DISPLAY_OUTPUT)))
            .andExpect(jsonPath("$.content.[*].storageOutput").value(hasItem(DEFAULT_STORAGE_OUTPUT)))
            .andExpect(jsonPath("$.content.[*].insideIO").value(hasItem(DEFAULT_INSIDE_IO)))
            .andExpect(jsonPath("$.content.[*].backPanelOutput").value(hasItem(DEFAULT_BACK_PANEL_OUTPUT)))
            .andExpect(jsonPath("$.content.[*].bios").value(hasItem(DEFAULT_BIOS)))
            .andExpect(jsonPath("$.content.[*].format").value(hasItem(DEFAULT_FORMAT.toString())));

        getAllProductAssertProductField(action);
    }

    @Test
    @Transactional
    void getMbe() throws Exception {
        // Initialize the database
        mbeRepository.saveAndFlush(mbe);

        // Get the mbe
        var action = restMbeMockMvc.perform(get(ENTITY_API_URL_ID, mbe.getId()));

        action
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

        getProductAssertProductField(action);
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

        ProductResourceIT.updateProductField(updatedMbe);

        restMbeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMbe.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMbe))
            )
            .andExpect(status().is4xxClientError());

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

        partialUpdateField(partialUpdatedMbe);

        restMbeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMbe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMbe))
            )
            .andExpect(status().is4xxClientError());

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
        restMbeMockMvc
            .perform(delete(ENTITY_API_URL_ID, mbe.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError());

        // Validate the database contains one less item
        List<Mbe> mbeList = mbeRepository.findAll();
        assertThat(mbeList).hasSize(databaseSizeBeforeDelete);
    }
}
