package com.configme.repository;

import com.configme.domain.ComputerCase;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ComputerCase entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComputerCaseRepository extends JpaRepository<ComputerCase, Long> {}
