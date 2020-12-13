package com.unubol.demo.store.repository;

import com.unubol.demo.store.domain.ClientDetails;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ClientDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientDetailsRepository extends JpaRepository<ClientDetails, Long> {
}
