package com.configme.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.configme.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MbeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mbe.class);
        Mbe mbe1 = new Mbe();
        mbe1.setId(1L);
        Mbe mbe2 = new Mbe();
        mbe2.setId(mbe1.getId());
        assertThat(mbe1).isEqualTo(mbe2);
        mbe2.setId(2L);
        assertThat(mbe1).isNotEqualTo(mbe2);
        mbe1.setId(null);
        assertThat(mbe1).isNotEqualTo(mbe2);
    }
}
