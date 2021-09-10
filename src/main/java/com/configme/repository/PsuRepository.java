package com.configme.repository;

import com.configme.domain.Psu;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Psu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PsuRepository extends JpaRepository<Psu, Long> {}
