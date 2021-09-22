package com.configme.repository;

import com.configme.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Ventirad entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VentiradRepository extends JpaRepository<Ventirad, Long> {
    @Query(
        value = "SELECT v FROM Ventirad v WHERE " +
        ":mbe is null or :#{ #mbe == null ? null : #mbe.socketCpu } member of v.sockets " +
        "AND (v.isActive = true  or true = :#{ #user == null ? false : #user.isAdmin })"
    )
    Page<Ventirad> findByCompatibility(@Param("user") User user, @Param("mbe") Mbe mbe, Pageable pageable);
}
