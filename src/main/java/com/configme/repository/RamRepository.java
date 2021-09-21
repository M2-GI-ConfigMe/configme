package com.configme.repository;

import com.configme.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Ram entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RamRepository extends JpaRepository<Ram, Long> {
    @Query(
        value = "SELECT r FROM Ram r WHERE " +
        "(:mbe is null or (r.unitSize * r.quantity) < :#{ #mbe == null ? 0 : floor(#mbe.ramSizeMax) } ) "
    )
    Page<Ram> findByCompatibility(@Param("mbe") Mbe mbe, Pageable pageable);
}
