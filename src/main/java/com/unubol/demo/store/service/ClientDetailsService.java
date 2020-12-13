package com.unubol.demo.store.service;

import com.unubol.demo.store.domain.ClientDetails;
import com.unubol.demo.store.repository.ClientDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ClientDetails}.
 */
@Service
@Transactional
public class ClientDetailsService {

    private final Logger log = LoggerFactory.getLogger(ClientDetailsService.class);

    private final ClientDetailsRepository clientDetailsRepository;

    public ClientDetailsService(ClientDetailsRepository clientDetailsRepository) {
        this.clientDetailsRepository = clientDetailsRepository;
    }

    /**
     * Save a clientDetails.
     *
     * @param clientDetails the entity to save.
     * @return the persisted entity.
     */
    public ClientDetails save(ClientDetails clientDetails) {
        log.debug("Request to save ClientDetails : {}", clientDetails);
        return clientDetailsRepository.save(clientDetails);
    }

    /**
     * Get all the clientDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClientDetails> findAll(Pageable pageable) {
        log.debug("Request to get all ClientDetails");
        return clientDetailsRepository.findAll(pageable);
    }


    /**
     * Get all the clientDetails with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<ClientDetails> findAllWithEagerRelationships(Pageable pageable) {
        return clientDetailsRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one clientDetails by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClientDetails> findOne(Long id) {
        log.debug("Request to get ClientDetails : {}", id);
        return clientDetailsRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the clientDetails by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ClientDetails : {}", id);
        clientDetailsRepository.deleteById(id);
    }
}
