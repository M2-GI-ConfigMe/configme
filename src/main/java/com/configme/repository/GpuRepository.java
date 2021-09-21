package com.configme.repository;

import com.configme.domain.ComputerCase;
import com.configme.domain.Cpu;
import com.configme.domain.Gpu;
import com.configme.domain.Mbe;
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
        "(:computerCase is null or ( g.dimension.height <= :#{ #computerCase == null ? 0 : #computerCase.sizeMaxGpu})) "
    )
    Page<Gpu> findByCompatibility(@Param("computerCase") ComputerCase computerCase, Pageable pageable);
}
