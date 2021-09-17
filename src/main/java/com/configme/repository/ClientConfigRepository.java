package com.configme.repository;

import com.configme.domain.ClientConfig;
import com.configme.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ClientConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientConfigRepository extends JpaRepository<ClientConfig, Long> {
    List<ClientConfig> findByUser(Optional<User> user);
}
