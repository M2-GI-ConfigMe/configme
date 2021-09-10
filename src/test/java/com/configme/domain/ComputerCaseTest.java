package com.configme.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.configme.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ComputerCaseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComputerCase.class);
        ComputerCase computerCase1 = new ComputerCase();
        computerCase1.setId(1L);
        ComputerCase computerCase2 = new ComputerCase();
        computerCase2.setId(computerCase1.getId());
        assertThat(computerCase1).isEqualTo(computerCase2);
        computerCase2.setId(2L);
        assertThat(computerCase1).isNotEqualTo(computerCase2);
        computerCase1.setId(null);
        assertThat(computerCase1).isNotEqualTo(computerCase2);
    }
}
