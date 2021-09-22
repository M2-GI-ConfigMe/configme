package com.configme.repository;

import com.configme.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Gpu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GpuRepository extends JpaRepository<Gpu, Long> {
    @Query(
        value = "SELECT g FROM Gpu g WHERE " +
        "(:computerCase is null or ( g.dimension.length <= :#{ #computerCase == null ? 0 : #computerCase.sizeMaxGpu})) " +
        "AND (g.isActive = true or true = :#{ #user == null ? false : #user.isAdmin }) " +
        "AND (lower(g.name) LIKE lower(concat('%',:name,'%')))"
    )
    Page<Gpu> findByCompatibility(
        @Param("user") User user,
        @Param("computerCase") ComputerCase computerCase,
        @Param("name") String name,
        Pageable pageable
    );
}
