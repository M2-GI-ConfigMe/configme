package com.configme.web.rest.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.configme.IntegrationTest;
import com.configme.domain.ClientConfig;
import com.configme.repository.ClientConfigRepository;
import com.configme.web.rest.ClientConfigResource;
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
 * Integration tests for the {@link ClientConfigResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClientConfigResourceIT {

    private static final String DEFAULT_TAGS = "AAAAAAAAAA";
    private static final String UPDATED_TAGS = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_COMLETE = false;
    private static final Boolean UPDATED_IS_COMLETE = true;

    private static final String DEFAULT_RESEARCH_KEY = "AAAAAAAAAA";
    private static final String UPDATED_RESEARCH_KEY = "BBBBBBBBBB";

    private static final Float DEFAULT_CPU_PRICE = 1F;
    private static final Float UPDATED_CPU_PRICE = 2F;

    private static final Float DEFAULT_GPU_PRICE = 1F;
    private static final Float UPDATED_GPU_PRICE = 2F;

    private static final Float DEFAULT_RAM_1_PRICE = 1F;
    private static final Float UPDATED_RAM_1_PRICE = 2F;

    private static final Float DEFAULT_RAM_2_PRICE = 1F;
    private static final Float UPDATED_RAM_2_PRICE = 2F;

    private static final Float DEFAULT_PSU_PRICE = 1F;
    private static final Float UPDATED_PSU_PRICE = 2F;

    private static final Float DEFAULT_COMPUTER_CASE_PRICE = 1F;
    private static final Float UPDATED_COMPUTER_CASE_PRICE = 2F;

    private static final Float DEFAULT_VENTIRAD_PRICE = 1F;
    private static final Float UPDATED_VENTIRAD_PRICE = 2F;

    private static final Float DEFAULT_HD_1_PRICE = 1F;
    private static final Float UPDATED_HD_1_PRICE = 2F;

    private static final Float DEFAULT_HD_2_PRICE = 1F;
    private static final Float UPDATED_HD_2_PRICE = 2F;

    private static final String ENTITY_API_URL = "/api/client-configs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClientConfigRepository clientConfigRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClientConfigMockMvc;

    private ClientConfig clientConfig;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientConfig createEntity(EntityManager em) {
        ClientConfig clientConfig = new ClientConfig()
            .tags(DEFAULT_TAGS)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .isComlete(DEFAULT_IS_COMLETE)
            .researchKey(DEFAULT_RESEARCH_KEY)
            .cpuPrice(DEFAULT_CPU_PRICE)
            .gpuPrice(DEFAULT_GPU_PRICE)
            .ram1Price(DEFAULT_RAM_1_PRICE)
            .ram2Price(DEFAULT_RAM_2_PRICE)
            .psuPrice(DEFAULT_PSU_PRICE)
            .computerCasePrice(DEFAULT_COMPUTER_CASE_PRICE)
            .ventiradPrice(DEFAULT_VENTIRAD_PRICE)
            .hd1Price(DEFAULT_HD_1_PRICE)
            .hd2Price(DEFAULT_HD_2_PRICE);
        return clientConfig;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientConfig createUpdatedEntity(EntityManager em) {
        ClientConfig clientConfig = new ClientConfig()
            .tags(UPDATED_TAGS)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .isComlete(UPDATED_IS_COMLETE)
            .researchKey(UPDATED_RESEARCH_KEY)
            .cpuPrice(UPDATED_CPU_PRICE)
            .gpuPrice(UPDATED_GPU_PRICE)
            .ram1Price(UPDATED_RAM_1_PRICE)
            .ram2Price(UPDATED_RAM_2_PRICE)
            .psuPrice(UPDATED_PSU_PRICE)
            .computerCasePrice(UPDATED_COMPUTER_CASE_PRICE)
            .ventiradPrice(UPDATED_VENTIRAD_PRICE)
            .hd1Price(UPDATED_HD_1_PRICE)
            .hd2Price(UPDATED_HD_2_PRICE);
        return clientConfig;
    }

    @BeforeEach
    public void initTest() {
        clientConfig = createEntity(em);
    }

    @Test
    @Transactional
    void createClientConfig() throws Exception {
        int databaseSizeBeforeCreate = clientConfigRepository.findAll().size();
        // Create the ClientConfig
        restClientConfigMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientConfig)))
            .andExpect(status().isCreated());

        // Validate the ClientConfig in the database
        List<ClientConfig> clientConfigList = clientConfigRepository.findAll();
        assertThat(clientConfigList).hasSize(databaseSizeBeforeCreate + 1);
        ClientConfig testClientConfig = clientConfigList.get(clientConfigList.size() - 1);
        assertThat(testClientConfig.getTags()).isEqualTo(DEFAULT_TAGS);
        assertThat(testClientConfig.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClientConfig.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testClientConfig.getIsComlete()).isEqualTo(DEFAULT_IS_COMLETE);
        assertThat(testClientConfig.getResearchKey()).isEqualTo(DEFAULT_RESEARCH_KEY);
        assertThat(testClientConfig.getCpuPrice()).isEqualTo(DEFAULT_CPU_PRICE);
        assertThat(testClientConfig.getGpuPrice()).isEqualTo(DEFAULT_GPU_PRICE);
        assertThat(testClientConfig.getRam1Price()).isEqualTo(DEFAULT_RAM_1_PRICE);
        assertThat(testClientConfig.getRam2Price()).isEqualTo(DEFAULT_RAM_2_PRICE);
        assertThat(testClientConfig.getPsuPrice()).isEqualTo(DEFAULT_PSU_PRICE);
        assertThat(testClientConfig.getComputerCasePrice()).isEqualTo(DEFAULT_COMPUTER_CASE_PRICE);
        assertThat(testClientConfig.getVentiradPrice()).isEqualTo(DEFAULT_VENTIRAD_PRICE);
        assertThat(testClientConfig.getHd1Price()).isEqualTo(DEFAULT_HD_1_PRICE);
        assertThat(testClientConfig.getHd2Price()).isEqualTo(DEFAULT_HD_2_PRICE);
    }

    @Test
    @Transactional
    void createClientConfigWithExistingId() throws Exception {
        // Create the ClientConfig with an existing ID
        clientConfig.setId(1L);

        int databaseSizeBeforeCreate = clientConfigRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientConfigMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientConfig)))
            .andExpect(status().isBadRequest());

        // Validate the ClientConfig in the database
        List<ClientConfig> clientConfigList = clientConfigRepository.findAll();
        assertThat(clientConfigList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClientConfigs() throws Exception {
        // Initialize the database
        clientConfigRepository.saveAndFlush(clientConfig);

        // Get all the clientConfigList
        restClientConfigMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isComlete").value(hasItem(DEFAULT_IS_COMLETE.booleanValue())))
            .andExpect(jsonPath("$.[*].researchKey").value(hasItem(DEFAULT_RESEARCH_KEY)))
            .andExpect(jsonPath("$.[*].cpuPrice").value(hasItem(DEFAULT_CPU_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].gpuPrice").value(hasItem(DEFAULT_GPU_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].ram1Price").value(hasItem(DEFAULT_RAM_1_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].ram2Price").value(hasItem(DEFAULT_RAM_2_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].psuPrice").value(hasItem(DEFAULT_PSU_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].computerCasePrice").value(hasItem(DEFAULT_COMPUTER_CASE_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].ventiradPrice").value(hasItem(DEFAULT_VENTIRAD_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].hd1Price").value(hasItem(DEFAULT_HD_1_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].hd2Price").value(hasItem(DEFAULT_HD_2_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    void getClientConfig() throws Exception {
        // Initialize the database
        clientConfigRepository.saveAndFlush(clientConfig);

        // Get the clientConfig
        restClientConfigMockMvc
            .perform(get(ENTITY_API_URL_ID, clientConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clientConfig.getId().intValue()))
            .andExpect(jsonPath("$.tags").value(DEFAULT_TAGS))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isComlete").value(DEFAULT_IS_COMLETE.booleanValue()))
            .andExpect(jsonPath("$.researchKey").value(DEFAULT_RESEARCH_KEY))
            .andExpect(jsonPath("$.cpuPrice").value(DEFAULT_CPU_PRICE.doubleValue()))
            .andExpect(jsonPath("$.gpuPrice").value(DEFAULT_GPU_PRICE.doubleValue()))
            .andExpect(jsonPath("$.ram1Price").value(DEFAULT_RAM_1_PRICE.doubleValue()))
            .andExpect(jsonPath("$.ram2Price").value(DEFAULT_RAM_2_PRICE.doubleValue()))
            .andExpect(jsonPath("$.psuPrice").value(DEFAULT_PSU_PRICE.doubleValue()))
            .andExpect(jsonPath("$.computerCasePrice").value(DEFAULT_COMPUTER_CASE_PRICE.doubleValue()))
            .andExpect(jsonPath("$.ventiradPrice").value(DEFAULT_VENTIRAD_PRICE.doubleValue()))
            .andExpect(jsonPath("$.hd1Price").value(DEFAULT_HD_1_PRICE.doubleValue()))
            .andExpect(jsonPath("$.hd2Price").value(DEFAULT_HD_2_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingClientConfig() throws Exception {
        // Get the clientConfig
        restClientConfigMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewClientConfig() throws Exception {
        // Initialize the database
        clientConfigRepository.saveAndFlush(clientConfig);

        int databaseSizeBeforeUpdate = clientConfigRepository.findAll().size();

        // Update the clientConfig
        ClientConfig updatedClientConfig = clientConfigRepository.findById(clientConfig.getId()).get();
        // Disconnect from session so that the updates on updatedClientConfig are not directly saved in db
        em.detach(updatedClientConfig);
        updatedClientConfig
            .tags(UPDATED_TAGS)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .isComlete(UPDATED_IS_COMLETE)
            .researchKey(UPDATED_RESEARCH_KEY)
            .cpuPrice(UPDATED_CPU_PRICE)
            .gpuPrice(UPDATED_GPU_PRICE)
            .ram1Price(UPDATED_RAM_1_PRICE)
            .ram2Price(UPDATED_RAM_2_PRICE)
            .psuPrice(UPDATED_PSU_PRICE)
            .computerCasePrice(UPDATED_COMPUTER_CASE_PRICE)
            .ventiradPrice(UPDATED_VENTIRAD_PRICE)
            .hd1Price(UPDATED_HD_1_PRICE)
            .hd2Price(UPDATED_HD_2_PRICE);

        restClientConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedClientConfig.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedClientConfig))
            )
            .andExpect(status().isOk());

        // Validate the ClientConfig in the database
        List<ClientConfig> clientConfigList = clientConfigRepository.findAll();
        assertThat(clientConfigList).hasSize(databaseSizeBeforeUpdate);
        ClientConfig testClientConfig = clientConfigList.get(clientConfigList.size() - 1);
        assertThat(testClientConfig.getTags()).isEqualTo(UPDATED_TAGS);
        assertThat(testClientConfig.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClientConfig.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testClientConfig.getIsComlete()).isEqualTo(UPDATED_IS_COMLETE);
        assertThat(testClientConfig.getResearchKey()).isEqualTo(UPDATED_RESEARCH_KEY);
        assertThat(testClientConfig.getCpuPrice()).isEqualTo(UPDATED_CPU_PRICE);
        assertThat(testClientConfig.getGpuPrice()).isEqualTo(UPDATED_GPU_PRICE);
        assertThat(testClientConfig.getRam1Price()).isEqualTo(UPDATED_RAM_1_PRICE);
        assertThat(testClientConfig.getRam2Price()).isEqualTo(UPDATED_RAM_2_PRICE);
        assertThat(testClientConfig.getPsuPrice()).isEqualTo(UPDATED_PSU_PRICE);
        assertThat(testClientConfig.getComputerCasePrice()).isEqualTo(UPDATED_COMPUTER_CASE_PRICE);
        assertThat(testClientConfig.getVentiradPrice()).isEqualTo(UPDATED_VENTIRAD_PRICE);
        assertThat(testClientConfig.getHd1Price()).isEqualTo(UPDATED_HD_1_PRICE);
        assertThat(testClientConfig.getHd2Price()).isEqualTo(UPDATED_HD_2_PRICE);
    }

    @Test
    @Transactional
    void putNonExistingClientConfig() throws Exception {
        int databaseSizeBeforeUpdate = clientConfigRepository.findAll().size();
        clientConfig.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clientConfig.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientConfig))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientConfig in the database
        List<ClientConfig> clientConfigList = clientConfigRepository.findAll();
        assertThat(clientConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClientConfig() throws Exception {
        int databaseSizeBeforeUpdate = clientConfigRepository.findAll().size();
        clientConfig.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clientConfig))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientConfig in the database
        List<ClientConfig> clientConfigList = clientConfigRepository.findAll();
        assertThat(clientConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClientConfig() throws Exception {
        int databaseSizeBeforeUpdate = clientConfigRepository.findAll().size();
        clientConfig.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientConfigMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clientConfig)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClientConfig in the database
        List<ClientConfig> clientConfigList = clientConfigRepository.findAll();
        assertThat(clientConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClientConfigWithPatch() throws Exception {
        // Initialize the database
        clientConfigRepository.saveAndFlush(clientConfig);

        int databaseSizeBeforeUpdate = clientConfigRepository.findAll().size();

        // Update the clientConfig using partial update
        ClientConfig partialUpdatedClientConfig = new ClientConfig();
        partialUpdatedClientConfig.setId(clientConfig.getId());

        partialUpdatedClientConfig
            .tags(UPDATED_TAGS)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .isComlete(UPDATED_IS_COMLETE)
            .researchKey(UPDATED_RESEARCH_KEY)
            .ram1Price(UPDATED_RAM_1_PRICE)
            .ram2Price(UPDATED_RAM_2_PRICE)
            .hd1Price(UPDATED_HD_1_PRICE);

        restClientConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClientConfig.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClientConfig))
            )
            .andExpect(status().isOk());

        // Validate the ClientConfig in the database
        List<ClientConfig> clientConfigList = clientConfigRepository.findAll();
        assertThat(clientConfigList).hasSize(databaseSizeBeforeUpdate);
        ClientConfig testClientConfig = clientConfigList.get(clientConfigList.size() - 1);
        assertThat(testClientConfig.getTags()).isEqualTo(UPDATED_TAGS);
        assertThat(testClientConfig.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClientConfig.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testClientConfig.getIsComlete()).isEqualTo(UPDATED_IS_COMLETE);
        assertThat(testClientConfig.getResearchKey()).isEqualTo(UPDATED_RESEARCH_KEY);
        assertThat(testClientConfig.getCpuPrice()).isEqualTo(DEFAULT_CPU_PRICE);
        assertThat(testClientConfig.getGpuPrice()).isEqualTo(DEFAULT_GPU_PRICE);
        assertThat(testClientConfig.getRam1Price()).isEqualTo(UPDATED_RAM_1_PRICE);
        assertThat(testClientConfig.getRam2Price()).isEqualTo(UPDATED_RAM_2_PRICE);
        assertThat(testClientConfig.getPsuPrice()).isEqualTo(DEFAULT_PSU_PRICE);
        assertThat(testClientConfig.getComputerCasePrice()).isEqualTo(DEFAULT_COMPUTER_CASE_PRICE);
        assertThat(testClientConfig.getVentiradPrice()).isEqualTo(DEFAULT_VENTIRAD_PRICE);
        assertThat(testClientConfig.getHd1Price()).isEqualTo(UPDATED_HD_1_PRICE);
        assertThat(testClientConfig.getHd2Price()).isEqualTo(DEFAULT_HD_2_PRICE);
    }

    @Test
    @Transactional
    void fullUpdateClientConfigWithPatch() throws Exception {
        // Initialize the database
        clientConfigRepository.saveAndFlush(clientConfig);

        int databaseSizeBeforeUpdate = clientConfigRepository.findAll().size();

        // Update the clientConfig using partial update
        ClientConfig partialUpdatedClientConfig = new ClientConfig();
        partialUpdatedClientConfig.setId(clientConfig.getId());

        partialUpdatedClientConfig
            .tags(UPDATED_TAGS)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .isComlete(UPDATED_IS_COMLETE)
            .researchKey(UPDATED_RESEARCH_KEY)
            .cpuPrice(UPDATED_CPU_PRICE)
            .gpuPrice(UPDATED_GPU_PRICE)
            .ram1Price(UPDATED_RAM_1_PRICE)
            .ram2Price(UPDATED_RAM_2_PRICE)
            .psuPrice(UPDATED_PSU_PRICE)
            .computerCasePrice(UPDATED_COMPUTER_CASE_PRICE)
            .ventiradPrice(UPDATED_VENTIRAD_PRICE)
            .hd1Price(UPDATED_HD_1_PRICE)
            .hd2Price(UPDATED_HD_2_PRICE);

        restClientConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClientConfig.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClientConfig))
            )
            .andExpect(status().isOk());

        // Validate the ClientConfig in the database
        List<ClientConfig> clientConfigList = clientConfigRepository.findAll();
        assertThat(clientConfigList).hasSize(databaseSizeBeforeUpdate);
        ClientConfig testClientConfig = clientConfigList.get(clientConfigList.size() - 1);
        assertThat(testClientConfig.getTags()).isEqualTo(UPDATED_TAGS);
        assertThat(testClientConfig.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClientConfig.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testClientConfig.getIsComlete()).isEqualTo(UPDATED_IS_COMLETE);
        assertThat(testClientConfig.getResearchKey()).isEqualTo(UPDATED_RESEARCH_KEY);
        assertThat(testClientConfig.getCpuPrice()).isEqualTo(UPDATED_CPU_PRICE);
        assertThat(testClientConfig.getGpuPrice()).isEqualTo(UPDATED_GPU_PRICE);
        assertThat(testClientConfig.getRam1Price()).isEqualTo(UPDATED_RAM_1_PRICE);
        assertThat(testClientConfig.getRam2Price()).isEqualTo(UPDATED_RAM_2_PRICE);
        assertThat(testClientConfig.getPsuPrice()).isEqualTo(UPDATED_PSU_PRICE);
        assertThat(testClientConfig.getComputerCasePrice()).isEqualTo(UPDATED_COMPUTER_CASE_PRICE);
        assertThat(testClientConfig.getVentiradPrice()).isEqualTo(UPDATED_VENTIRAD_PRICE);
        assertThat(testClientConfig.getHd1Price()).isEqualTo(UPDATED_HD_1_PRICE);
        assertThat(testClientConfig.getHd2Price()).isEqualTo(UPDATED_HD_2_PRICE);
    }

    @Test
    @Transactional
    void patchNonExistingClientConfig() throws Exception {
        int databaseSizeBeforeUpdate = clientConfigRepository.findAll().size();
        clientConfig.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clientConfig.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientConfig))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientConfig in the database
        List<ClientConfig> clientConfigList = clientConfigRepository.findAll();
        assertThat(clientConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClientConfig() throws Exception {
        int databaseSizeBeforeUpdate = clientConfigRepository.findAll().size();
        clientConfig.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clientConfig))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClientConfig in the database
        List<ClientConfig> clientConfigList = clientConfigRepository.findAll();
        assertThat(clientConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClientConfig() throws Exception {
        int databaseSizeBeforeUpdate = clientConfigRepository.findAll().size();
        clientConfig.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientConfigMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(clientConfig))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClientConfig in the database
        List<ClientConfig> clientConfigList = clientConfigRepository.findAll();
        assertThat(clientConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClientConfig() throws Exception {
        // Initialize the database
        clientConfigRepository.saveAndFlush(clientConfig);

        int databaseSizeBeforeDelete = clientConfigRepository.findAll().size();

        // Delete the clientConfig
        restClientConfigMockMvc
            .perform(delete(ENTITY_API_URL_ID, clientConfig.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClientConfig> clientConfigList = clientConfigRepository.findAll();
        assertThat(clientConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
