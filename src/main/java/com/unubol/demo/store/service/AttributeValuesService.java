package com.unubol.demo.store.service;

import com.unubol.demo.store.domain.AttributeValues;
import com.unubol.demo.store.repository.AttributeValuesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AttributeValues}.
 */
@Service
@Transactional
public class AttributeValuesService {

    private final Logger log = LoggerFactory.getLogger(AttributeValuesService.class);

    private final AttributeValuesRepository attributeValuesRepository;

    public AttributeValuesService(AttributeValuesRepository attributeValuesRepository) {
        this.attributeValuesRepository = attributeValuesRepository;
    }

    /**
     * Save a attributeValues.
     *
     * @param attributeValues the entity to save.
     * @return the persisted entity.
     */
    public AttributeValues save(AttributeValues attributeValues) {
        log.debug("Request to save AttributeValues : {}", attributeValues);
        return attributeValuesRepository.save(attributeValues);
    }

    /**
     * Get all the attributeValues.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<AttributeValues> findAll(Pageable pageable) {
        log.debug("Request to get all AttributeValues");
        return attributeValuesRepository.findAll(pageable);
    }


    /**
     * Get one attributeValues by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AttributeValues> findOne(Long id) {
        log.debug("Request to get AttributeValues : {}", id);
        return attributeValuesRepository.findById(id);
    }

    /**
     * Delete the attributeValues by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AttributeValues : {}", id);
        attributeValuesRepository.deleteById(id);
    }
}
