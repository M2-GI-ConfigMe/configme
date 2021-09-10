package com.configme.repository;

import com.configme.domain.HardDrive;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the HardDrive entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HardDriveRepository extends JpaRepository<HardDrive, Long> {}
