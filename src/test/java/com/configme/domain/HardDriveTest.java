package com.configme.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.configme.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HardDriveTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HardDrive.class);
        HardDrive hardDrive1 = new HardDrive();
        hardDrive1.setId(1L);
        HardDrive hardDrive2 = new HardDrive();
        hardDrive2.setId(hardDrive1.getId());
        assertThat(hardDrive1).isEqualTo(hardDrive2);
        hardDrive2.setId(2L);
        assertThat(hardDrive1).isNotEqualTo(hardDrive2);
        hardDrive1.setId(null);
        assertThat(hardDrive1).isNotEqualTo(hardDrive2);
    }
}
