package com.unubol.demo.store.repository;

import com.unubol.demo.store.domain.AttributeValues;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AttributeValues entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttributeValuesRepository extends JpaRepository<AttributeValues, Long> {
}
