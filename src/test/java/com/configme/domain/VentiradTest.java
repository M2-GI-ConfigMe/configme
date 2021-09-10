package com.configme.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.configme.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VentiradTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ventirad.class);
        Ventirad ventirad1 = new Ventirad();
        ventirad1.setId(1L);
        Ventirad ventirad2 = new Ventirad();
        ventirad2.setId(ventirad1.getId());
        assertThat(ventirad1).isEqualTo(ventirad2);
        ventirad2.setId(2L);
        assertThat(ventirad1).isNotEqualTo(ventirad2);
        ventirad1.setId(null);
        assertThat(ventirad1).isNotEqualTo(ventirad2);
    }
}
