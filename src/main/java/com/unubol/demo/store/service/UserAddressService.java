package com.unubol.demo.store.service;

import com.unubol.demo.store.domain.UserAddress;
import com.unubol.demo.store.repository.UserAddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserAddress}.
 */
@Service
@Transactional
public class UserAddressService {

    private final Logger log = LoggerFactory.getLogger(UserAddressService.class);

    private final UserAddressRepository userAddressRepository;

    public UserAddressService(UserAddressRepository userAddressRepository) {
        this.userAddressRepository = userAddressRepository;
    }

    /**
     * Save a userAddress.
     *
     * @param userAddress the entity to save.
     * @return the persisted entity.
     */
    public UserAddress save(UserAddress userAddress) {
        log.debug("Request to save UserAddress : {}", userAddress);
        return userAddressRepository.save(userAddress);
    }

    /**
     * Get all the userAddresses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<UserAddress> findAll(Pageable pageable) {
        log.debug("Request to get all UserAddresses");
        return userAddressRepository.findAll(pageable);
    }


    /**
     * Get one userAddress by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserAddress> findOne(Long id) {
        log.debug("Request to get UserAddress : {}", id);
        return userAddressRepository.findById(id);
    }

    /**
     * Delete the userAddress by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete UserAddress : {}", id);
        userAddressRepository.deleteById(id);
    }
}
