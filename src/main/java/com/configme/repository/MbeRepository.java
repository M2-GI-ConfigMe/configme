package com.configme.repository;

import com.configme.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Mbe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MbeRepository extends JpaRepository<Mbe, Long> {
    //     +
    //         "AND (:gpu is null or  m.sizeMaxGpu > (:#{ #gpu == null ? 0 : #gpu.dimension.height})) " +
    //         "AND (:mbe is null or (:#{ #mbe == null ? null : #mbe.format} MEMBER OF m.formats))

    @Query(
        value = "SELECT m FROM Mbe m WHERE " +
        "(:ventirad is null or m.socketCpu in :#{ #ventirad == null ? null : #ventirad.sockets }) " +
        "AND (:cpu is null or m.socketCpu = :#{ #cpu == null ? null : #cpu.socketType }) " +
        "AND (:ram is null or m.ramSizeMax >= :#{ #ram == null ? new Float(0) : new Float(#ram.unitSize * #ram.quantity) }) " +
        "AND (:ram is null or m.ramType = :#{ #ram == null ? null : #ram.type }) " +
        "AND (:computerCase is null or m.format in :#{ #computerCase == null ? null : #computerCase.formats }) "
    )
    Page<Mbe> findByCompatibility(
        @Param("cpu") Cpu cpu,
        @Param("ram") Ram ram,
        @Param("ventirad") Ventirad ventirad,
        @Param("computerCase") ComputerCase computerCase,
        Pageable pageable
    );
}
