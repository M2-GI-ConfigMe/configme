package com.configme.repository;

import com.configme.domain.Cpu;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Cpu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CpuRepository extends JpaRepository<Cpu, Long> {}
