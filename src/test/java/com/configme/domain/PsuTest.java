package com.configme.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.configme.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PsuTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Psu.class);
        Psu psu1 = new Psu();
        psu1.setId(1L);
        Psu psu2 = new Psu();
        psu2.setId(psu1.getId());
        assertThat(psu1).isEqualTo(psu2);
        psu2.setId(2L);
        assertThat(psu1).isNotEqualTo(psu2);
        psu1.setId(null);
        assertThat(psu1).isNotEqualTo(psu2);
    }
}
