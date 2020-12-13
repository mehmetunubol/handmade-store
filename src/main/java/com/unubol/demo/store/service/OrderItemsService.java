package com.unubol.demo.store.service;

import com.unubol.demo.store.domain.OrderItems;
import com.unubol.demo.store.repository.OrderItemsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link OrderItems}.
 */
@Service
@Transactional
public class OrderItemsService {

    private final Logger log = LoggerFactory.getLogger(OrderItemsService.class);

    private final OrderItemsRepository orderItemsRepository;

    public OrderItemsService(OrderItemsRepository orderItemsRepository) {
        this.orderItemsRepository = orderItemsRepository;
    }

    /**
     * Save a orderItems.
     *
     * @param orderItems the entity to save.
     * @return the persisted entity.
     */
    public OrderItems save(OrderItems orderItems) {
        log.debug("Request to save OrderItems : {}", orderItems);
        return orderItemsRepository.save(orderItems);
    }

    /**
     * Get all the orderItems.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<OrderItems> findAll() {
        log.debug("Request to get all OrderItems");
        return orderItemsRepository.findAll();
    }


    /**
     * Get one orderItems by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrderItems> findOne(Long id) {
        log.debug("Request to get OrderItems : {}", id);
        return orderItemsRepository.findById(id);
    }

    /**
     * Delete the orderItems by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OrderItems : {}", id);
        orderItemsRepository.deleteById(id);
    }
}
