package com.configme.repository;

import com.configme.domain.Ram;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Ram entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RamRepository extends JpaRepository<Ram, Long> {}
