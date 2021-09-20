package com.configme.repository;

import com.configme.domain.Order;
import com.configme.domain.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Order entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order o where o.buyer = :user AND o.status = 'CART'")
    Order findOrderInCartByUser(@Param("user") User user);
}
