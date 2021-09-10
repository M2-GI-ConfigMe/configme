package com.configme.repository;

import com.configme.domain.Gpu;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Gpu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GpuRepository extends JpaRepository<Gpu, Long> {}
