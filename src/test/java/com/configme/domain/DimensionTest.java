package com.configme.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.configme.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DimensionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dimension.class);
        Dimension dimension1 = new Dimension();
        dimension1.setId(1L);
        Dimension dimension2 = new Dimension();
        dimension2.setId(dimension1.getId());
        assertThat(dimension1).isEqualTo(dimension2);
        dimension2.setId(2L);
        assertThat(dimension1).isNotEqualTo(dimension2);
        dimension1.setId(null);
        assertThat(dimension1).isNotEqualTo(dimension2);
    }
}
