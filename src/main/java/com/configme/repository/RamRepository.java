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
        "(:mbe is null or (r.unitSize * r.quantity) < :#{ #mbe == null ? 0 : #mbe.ramSizeMax } ) " +
        "AND (:mbe is null or r.type = :#{ #mbe == null ? null : #mbe.ramType } ) " +
        "AND (r.isActive = true or true = :#{ #user == null ? false : #user.isAdmin }) " +
        "AND (lower(r.name) LIKE lower(concat('%',:name,'%')))"
    )
    Page<Ram> findByCompatibility(@Param("user") User user, @Param("mbe") Mbe mbe, @Param("name") String name, Pageable pageable);
}
