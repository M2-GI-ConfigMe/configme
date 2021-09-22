package com.configme.repository;

import com.configme.domain.ClientConfig;
import com.configme.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ClientConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientConfigRepository extends JpaRepository<ClientConfig, Long> {
    List<ClientConfig> findByUser(Optional<User> user);

    @Query("select case when count(o) = 1 then true else false end from OrderLine o where o.order.buyer = :buyer and :config = o.config")
    boolean isBuyer(@Param("buyer") User buyer, @Param("config") ClientConfig config);
}
