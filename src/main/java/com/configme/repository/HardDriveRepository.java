package com.configme.repository;

import com.configme.domain.Cpu;
import com.configme.domain.HardDrive;
import com.configme.domain.Mbe;
import com.configme.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the HardDrive entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HardDriveRepository extends JpaRepository<HardDrive, Long> {
    @Query(
        value = "SELECT h FROM HardDrive h WHERE " +
        "h.isActive = true or true = :#{ #user == null ? false : #user.isAdmin } " +
        "AND (lower(h.name) LIKE lower(concat('%',:name,'%')))"
    )
    Page<HardDrive> findByCompatibility(@Param("user") User user, @Param("name") String name, Pageable pageable);
}
