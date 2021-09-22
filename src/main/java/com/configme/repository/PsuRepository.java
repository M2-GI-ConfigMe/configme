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
        "(:computerCase is null or (p.dimension.width < :#{ #computerCase == null ? 0 : (#computerCase.sizeMaxPsu)})) " +
        "AND (p.isActive = true or true = :#{ #user == null ? false : #user.isAdmin })" +
        "AND (lower(p.name) LIKE lower(concat('%',:name,'%')))"
    )
    Page<Psu> findByCompatibility(
        @Param("user") User user,
        @Param("computerCase") ComputerCase computerCase,
        @Param("name") String name,
        Pageable pageable
    );
}
