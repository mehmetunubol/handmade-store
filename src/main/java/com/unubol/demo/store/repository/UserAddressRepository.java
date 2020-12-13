package com.unubol.demo.store.repository;

import com.unubol.demo.store.domain.UserAddress;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserAddress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {
}
