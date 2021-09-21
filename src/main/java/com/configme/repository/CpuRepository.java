package com.configme.repository;

import com.configme.domain.Cpu;
import com.configme.domain.Mbe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Cpu entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CpuRepository extends JpaRepository<Cpu, Long> {
    //    @Query("select c from Cpu c where (:mbe is null or c.socketType = :mbe.socketCpu)")
    //    Page<Cpu> findByCompatibility(@Param("mbe") Mbe mbe, Pageable pageable);
}
