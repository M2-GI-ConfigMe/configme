package com.configme.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.configme.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClientConfigTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientConfig.class);
        ClientConfig clientConfig1 = new ClientConfig();
        clientConfig1.setId(1L);
        ClientConfig clientConfig2 = new ClientConfig();
        clientConfig2.setId(clientConfig1.getId());
        assertThat(clientConfig1).isEqualTo(clientConfig2);
        clientConfig2.setId(2L);
        assertThat(clientConfig1).isNotEqualTo(clientConfig2);
        clientConfig1.setId(null);
        assertThat(clientConfig1).isNotEqualTo(clientConfig2);
    }
}
