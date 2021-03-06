package com.configme.repository;

import com.configme.domain.Ventirad;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Ventirad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VentiradRepository extends JpaRepository<Ventirad, Long> {}
