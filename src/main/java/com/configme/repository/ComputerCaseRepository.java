package com.configme.repository;

import com.configme.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ComputerCase entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComputerCaseRepository extends JpaRepository<ComputerCase, Long> {
    @Query(
        value = "SELECT c FROM ComputerCase c WHERE " +
        "(:ventirad is null or (c.sizeMaxVentirad > :#{ #ventirad == null ? 0 : (#ventirad.dimension.height)})) " +
        "AND (:gpu is null or  c.sizeMaxGpu > (:#{ #gpu == null ? 0 : #gpu.dimension.length})) " +
        "AND (:mbe is null or (:#{ #mbe == null ? null : #mbe.format} MEMBER OF c.formats)) " +
        "AND (c.isActive = true or true = :#{ #user == null ? false : #user.isAdmin })"
    )
    Page<ComputerCase> findByCompatibility(
        @Param("user") User user,
        @Param("mbe") Mbe mbe,
        @Param("gpu") Gpu gpu,
        @Param("ventirad") Ventirad ventirad,
        Pageable pageable
    );
}
