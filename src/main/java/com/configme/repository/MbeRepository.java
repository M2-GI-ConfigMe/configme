package com.configme.repository;

import com.configme.domain.Mbe;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Mbe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MbeRepository extends JpaRepository<Mbe, Long> {}
