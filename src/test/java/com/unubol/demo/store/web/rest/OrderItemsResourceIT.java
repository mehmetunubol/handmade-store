package com.unubol.demo.store.web.rest;

import com.unubol.demo.store.StoreApp;
import com.unubol.demo.store.domain.OrderItems;
import com.unubol.demo.store.domain.Product;
import com.unubol.demo.store.domain.Cart;
import com.unubol.demo.store.repository.OrderItemsRepository;
import com.unubol.demo.store.service.OrderItemsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OrderItemsResource} REST controller.
 */
@SpringBootTest(classes = StoreApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OrderItemsResourceIT {

    private static final Integer DEFAULT_QUANTITY = 0;
    private static final Integer UPDATED_QUANTITY = 1;

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(0);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(1);

    @Autowired
    private OrderItemsRepository orderItemsRepository;

    @Autowired
    private OrderItemsService orderItemsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderItemsMockMvc;

    private OrderItems orderItems;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderItems createEntity(EntityManager em) {
        OrderItems orderItems = new OrderItems()
            .quantity(DEFAULT_QUANTITY)
            .price(DEFAULT_PRICE);
        // Add required entity
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        orderItems.setProduct(product);
        // Add required entity
        Cart cart;
        if (TestUtil.findAll(em, Cart.class).isEmpty()) {
            cart = CartResourceIT.createEntity(em);
            em.persist(cart);
            em.flush();
        } else {
            cart = TestUtil.findAll(em, Cart.class).get(0);
        }
        orderItems.setCart(cart);
        return orderItems;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderItems createUpdatedEntity(EntityManager em) {
        OrderItems orderItems = new OrderItems()
            .quantity(UPDATED_QUANTITY)
            .price(UPDATED_PRICE);
        // Add required entity
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createUpdatedEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        orderItems.setProduct(product);
        // Add required entity
        Cart cart;
        if (TestUtil.findAll(em, Cart.class).isEmpty()) {
            cart = CartResourceIT.createUpdatedEntity(em);
            em.persist(cart);
            em.flush();
        } else {
            cart = TestUtil.findAll(em, Cart.class).get(0);
        }
        orderItems.setCart(cart);
        return orderItems;
    }

    @BeforeEach
    public void initTest() {
        orderItems = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderItems() throws Exception {
        int databaseSizeBeforeCreate = orderItemsRepository.findAll().size();
        // Create the OrderItems
        restOrderItemsMockMvc.perform(post("/api/order-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderItems)))
            .andExpect(status().isCreated());

        // Validate the OrderItems in the database
        List<OrderItems> orderItemsList = orderItemsRepository.findAll();
        assertThat(orderItemsList).hasSize(databaseSizeBeforeCreate + 1);
        OrderItems testOrderItems = orderItemsList.get(orderItemsList.size() - 1);
        assertThat(testOrderItems.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testOrderItems.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createOrderItemsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderItemsRepository.findAll().size();

        // Create the OrderItems with an existing ID
        orderItems.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderItemsMockMvc.perform(post("/api/order-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderItems)))
            .andExpect(status().isBadRequest());

        // Validate the OrderItems in the database
        List<OrderItems> orderItemsList = orderItemsRepository.findAll();
        assertThat(orderItemsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderItemsRepository.findAll().size();
        // set the field null
        orderItems.setQuantity(null);

        // Create the OrderItems, which fails.


        restOrderItemsMockMvc.perform(post("/api/order-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderItems)))
            .andExpect(status().isBadRequest());

        List<OrderItems> orderItemsList = orderItemsRepository.findAll();
        assertThat(orderItemsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderItemsRepository.findAll().size();
        // set the field null
        orderItems.setPrice(null);

        // Create the OrderItems, which fails.


        restOrderItemsMockMvc.perform(post("/api/order-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderItems)))
            .andExpect(status().isBadRequest());

        List<OrderItems> orderItemsList = orderItemsRepository.findAll();
        assertThat(orderItemsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrderItems() throws Exception {
        // Initialize the database
        orderItemsRepository.saveAndFlush(orderItems);

        // Get all the orderItemsList
        restOrderItemsMockMvc.perform(get("/api/order-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderItems.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())));
    }
    
    @Test
    @Transactional
    public void getOrderItems() throws Exception {
        // Initialize the database
        orderItemsRepository.saveAndFlush(orderItems);

        // Get the orderItems
        restOrderItemsMockMvc.perform(get("/api/order-items/{id}", orderItems.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orderItems.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingOrderItems() throws Exception {
        // Get the orderItems
        restOrderItemsMockMvc.perform(get("/api/order-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderItems() throws Exception {
        // Initialize the database
        orderItemsService.save(orderItems);

        int databaseSizeBeforeUpdate = orderItemsRepository.findAll().size();

        // Update the orderItems
        OrderItems updatedOrderItems = orderItemsRepository.findById(orderItems.getId()).get();
        // Disconnect from session so that the updates on updatedOrderItems are not directly saved in db
        em.detach(updatedOrderItems);
        updatedOrderItems
            .quantity(UPDATED_QUANTITY)
            .price(UPDATED_PRICE);

        restOrderItemsMockMvc.perform(put("/api/order-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrderItems)))
            .andExpect(status().isOk());

        // Validate the OrderItems in the database
        List<OrderItems> orderItemsList = orderItemsRepository.findAll();
        assertThat(orderItemsList).hasSize(databaseSizeBeforeUpdate);
        OrderItems testOrderItems = orderItemsList.get(orderItemsList.size() - 1);
        assertThat(testOrderItems.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testOrderItems.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderItems() throws Exception {
        int databaseSizeBeforeUpdate = orderItemsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderItemsMockMvc.perform(put("/api/order-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderItems)))
            .andExpect(status().isBadRequest());

        // Validate the OrderItems in the database
        List<OrderItems> orderItemsList = orderItemsRepository.findAll();
        assertThat(orderItemsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderItems() throws Exception {
        // Initialize the database
        orderItemsService.save(orderItems);

        int databaseSizeBeforeDelete = orderItemsRepository.findAll().size();

        // Delete the orderItems
        restOrderItemsMockMvc.perform(delete("/api/order-items/{id}", orderItems.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderItems> orderItemsList = orderItemsRepository.findAll();
        assertThat(orderItemsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
