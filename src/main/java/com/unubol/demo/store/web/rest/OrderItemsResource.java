package com.unubol.demo.store.web.rest;

import com.unubol.demo.store.domain.OrderItems;
import com.unubol.demo.store.service.OrderItemsService;
import com.unubol.demo.store.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.unubol.demo.store.domain.OrderItems}.
 */
@RestController
@RequestMapping("/api")
public class OrderItemsResource {

    private final Logger log = LoggerFactory.getLogger(OrderItemsResource.class);

    private static final String ENTITY_NAME = "orderItems";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderItemsService orderItemsService;

    public OrderItemsResource(OrderItemsService orderItemsService) {
        this.orderItemsService = orderItemsService;
    }

    /**
     * {@code POST  /order-items} : Create a new orderItems.
     *
     * @param orderItems the orderItems to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderItems, or with status {@code 400 (Bad Request)} if the orderItems has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-items")
    public ResponseEntity<OrderItems> createOrderItems(@Valid @RequestBody OrderItems orderItems) throws URISyntaxException {
        log.debug("REST request to save OrderItems : {}", orderItems);
        if (orderItems.getId() != null) {
            throw new BadRequestAlertException("A new orderItems cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderItems result = orderItemsService.save(orderItems);
        return ResponseEntity.created(new URI("/api/order-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-items} : Updates an existing orderItems.
     *
     * @param orderItems the orderItems to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderItems,
     * or with status {@code 400 (Bad Request)} if the orderItems is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderItems couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-items")
    public ResponseEntity<OrderItems> updateOrderItems(@Valid @RequestBody OrderItems orderItems) throws URISyntaxException {
        log.debug("REST request to update OrderItems : {}", orderItems);
        if (orderItems.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderItems result = orderItemsService.save(orderItems);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, orderItems.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /order-items} : get all the orderItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderItems in body.
     */
    @GetMapping("/order-items")
    public List<OrderItems> getAllOrderItems() {
        log.debug("REST request to get all OrderItems");
        return orderItemsService.findAll();
    }

    /**
     * {@code GET  /order-items/:id} : get the "id" orderItems.
     *
     * @param id the id of the orderItems to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderItems, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-items/{id}")
    public ResponseEntity<OrderItems> getOrderItems(@PathVariable Long id) {
        log.debug("REST request to get OrderItems : {}", id);
        Optional<OrderItems> orderItems = orderItemsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderItems);
    }

    /**
     * {@code DELETE  /order-items/:id} : delete the "id" orderItems.
     *
     * @param id the id of the orderItems to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-items/{id}")
    public ResponseEntity<Void> deleteOrderItems(@PathVariable Long id) {
        log.debug("REST request to delete OrderItems : {}", id);
        orderItemsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
