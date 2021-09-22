package com.configme.repository;

import com.configme.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Psu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PsuRepository extends JpaRepository<Psu, Long> {
    @Query(
        value = "SELECT p FROM Psu p WHERE " +
        "(:computerCase is null or (p.dimension.height < :#{ #computerCase == null ? 0 : (#computerCase.maxSizeGpu)})) " +
        "AND (p.isActive = true or true = :#{ #user == null ? false : #user.isAdmin })"
    )
    Page<Psu> findByCompatibility(@Param("user") User user, @Param("computerCase") ComputerCase computerCase, Pageable pageable);
}
