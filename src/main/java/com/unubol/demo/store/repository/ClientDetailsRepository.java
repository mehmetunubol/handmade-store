package com.unubol.demo.store.repository;

import com.unubol.demo.store.domain.ClientDetails;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the ClientDetails entity.
 */
@Repository
public interface ClientDetailsRepository extends JpaRepository<ClientDetails, Long> {

    @Query(value = "select distinct clientDetails from ClientDetails clientDetails left join fetch clientDetails.carts",
        countQuery = "select count(distinct clientDetails) from ClientDetails clientDetails")
    Page<ClientDetails> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct clientDetails from ClientDetails clientDetails left join fetch clientDetails.carts")
    List<ClientDetails> findAllWithEagerRelationships();

    @Query("select clientDetails from ClientDetails clientDetails left join fetch clientDetails.carts where clientDetails.id =:id")
    Optional<ClientDetails> findOneWithEagerRelationships(@Param("id") Long id);
}
