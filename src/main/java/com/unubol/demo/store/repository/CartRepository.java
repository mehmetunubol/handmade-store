package com.unubol.demo.store.repository;

import com.unubol.demo.store.domain.Cart;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Cart entity.
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query(value = "select distinct cart from Cart cart left join fetch cart.addresses",
        countQuery = "select count(distinct cart) from Cart cart")
    Page<Cart> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct cart from Cart cart left join fetch cart.addresses")
    List<Cart> findAllWithEagerRelationships();

    @Query("select cart from Cart cart left join fetch cart.addresses where cart.id =:id")
    Optional<Cart> findOneWithEagerRelationships(@Param("id") Long id);
}
