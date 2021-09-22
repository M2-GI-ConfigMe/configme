package com.configme.repository;

import com.configme.domain.OrderLine;
import com.configme.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OrderLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
    @Query("select case when count(o) > 0 then true else false end from OrderLine o where o.id = :id and o.order.buyer = :buyer")
    boolean isOwner(@Param("buyer") User buyer, @Param("id") Long id);

    @Query("select case when count(o) = 1 then true else false end from OrderLine o where o.id = :id and o.order.buyer = :buyer")
    boolean isAlone(@Param("id") Long id);
}
