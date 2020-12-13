package com.unubol.demo.store.web.rest;

import com.unubol.demo.store.StoreApp;
import com.unubol.demo.store.domain.UserAddress;
import com.unubol.demo.store.domain.ClientDetails;
import com.unubol.demo.store.repository.UserAddressRepository;
import com.unubol.demo.store.service.UserAddressService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UserAddressResource} REST controller.
 */
@SpringBootTest(classes = StoreApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserAddressResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    @Autowired
    private UserAddressRepository userAddressRepository;

    @Autowired
    private UserAddressService userAddressService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserAddressMockMvc;

    private UserAddress userAddress;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAddress createEntity(EntityManager em) {
        UserAddress userAddress = new UserAddress()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .detail(DEFAULT_DETAIL)
            .city(DEFAULT_CITY)
            .country(DEFAULT_COUNTRY);
        // Add required entity
        ClientDetails clientDetails;
        if (TestUtil.findAll(em, ClientDetails.class).isEmpty()) {
            clientDetails = ClientDetailsResourceIT.createEntity(em);
            em.persist(clientDetails);
            em.flush();
        } else {
            clientDetails = TestUtil.findAll(em, ClientDetails.class).get(0);
        }
        userAddress.setClientDetails(clientDetails);
        return userAddress;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAddress createUpdatedEntity(EntityManager em) {
        UserAddress userAddress = new UserAddress()
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .detail(UPDATED_DETAIL)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY);
        // Add required entity
        ClientDetails clientDetails;
        if (TestUtil.findAll(em, ClientDetails.class).isEmpty()) {
            clientDetails = ClientDetailsResourceIT.createUpdatedEntity(em);
            em.persist(clientDetails);
            em.flush();
        } else {
            clientDetails = TestUtil.findAll(em, ClientDetails.class).get(0);
        }
        userAddress.setClientDetails(clientDetails);
        return userAddress;
    }

    @BeforeEach
    public void initTest() {
        userAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserAddress() throws Exception {
        int databaseSizeBeforeCreate = userAddressRepository.findAll().size();
        // Create the UserAddress
        restUserAddressMockMvc.perform(post("/api/user-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAddress)))
            .andExpect(status().isCreated());

        // Validate the UserAddress in the database
        List<UserAddress> userAddressList = userAddressRepository.findAll();
        assertThat(userAddressList).hasSize(databaseSizeBeforeCreate + 1);
        UserAddress testUserAddress = userAddressList.get(userAddressList.size() - 1);
        assertThat(testUserAddress.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUserAddress.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testUserAddress.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testUserAddress.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testUserAddress.getCountry()).isEqualTo(DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    public void createUserAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userAddressRepository.findAll().size();

        // Create the UserAddress with an existing ID
        userAddress.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAddressMockMvc.perform(post("/api/user-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAddress)))
            .andExpect(status().isBadRequest());

        // Validate the UserAddress in the database
        List<UserAddress> userAddressList = userAddressRepository.findAll();
        assertThat(userAddressList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAddressRepository.findAll().size();
        // set the field null
        userAddress.setName(null);

        // Create the UserAddress, which fails.


        restUserAddressMockMvc.perform(post("/api/user-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAddress)))
            .andExpect(status().isBadRequest());

        List<UserAddress> userAddressList = userAddressRepository.findAll();
        assertThat(userAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAddressRepository.findAll().size();
        // set the field null
        userAddress.setType(null);

        // Create the UserAddress, which fails.


        restUserAddressMockMvc.perform(post("/api/user-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAddress)))
            .andExpect(status().isBadRequest());

        List<UserAddress> userAddressList = userAddressRepository.findAll();
        assertThat(userAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDetailIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAddressRepository.findAll().size();
        // set the field null
        userAddress.setDetail(null);

        // Create the UserAddress, which fails.


        restUserAddressMockMvc.perform(post("/api/user-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAddress)))
            .andExpect(status().isBadRequest());

        List<UserAddress> userAddressList = userAddressRepository.findAll();
        assertThat(userAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAddressRepository.findAll().size();
        // set the field null
        userAddress.setCity(null);

        // Create the UserAddress, which fails.


        restUserAddressMockMvc.perform(post("/api/user-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAddress)))
            .andExpect(status().isBadRequest());

        List<UserAddress> userAddressList = userAddressRepository.findAll();
        assertThat(userAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAddressRepository.findAll().size();
        // set the field null
        userAddress.setCountry(null);

        // Create the UserAddress, which fails.


        restUserAddressMockMvc.perform(post("/api/user-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAddress)))
            .andExpect(status().isBadRequest());

        List<UserAddress> userAddressList = userAddressRepository.findAll();
        assertThat(userAddressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserAddresses() throws Exception {
        // Initialize the database
        userAddressRepository.saveAndFlush(userAddress);

        // Get all the userAddressList
        restUserAddressMockMvc.perform(get("/api/user-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)));
    }
    
    @Test
    @Transactional
    public void getUserAddress() throws Exception {
        // Initialize the database
        userAddressRepository.saveAndFlush(userAddress);

        // Get the userAddress
        restUserAddressMockMvc.perform(get("/api/user-addresses/{id}", userAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userAddress.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY));
    }
    @Test
    @Transactional
    public void getNonExistingUserAddress() throws Exception {
        // Get the userAddress
        restUserAddressMockMvc.perform(get("/api/user-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserAddress() throws Exception {
        // Initialize the database
        userAddressService.save(userAddress);

        int databaseSizeBeforeUpdate = userAddressRepository.findAll().size();

        // Update the userAddress
        UserAddress updatedUserAddress = userAddressRepository.findById(userAddress.getId()).get();
        // Disconnect from session so that the updates on updatedUserAddress are not directly saved in db
        em.detach(updatedUserAddress);
        updatedUserAddress
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .detail(UPDATED_DETAIL)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY);

        restUserAddressMockMvc.perform(put("/api/user-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserAddress)))
            .andExpect(status().isOk());

        // Validate the UserAddress in the database
        List<UserAddress> userAddressList = userAddressRepository.findAll();
        assertThat(userAddressList).hasSize(databaseSizeBeforeUpdate);
        UserAddress testUserAddress = userAddressList.get(userAddressList.size() - 1);
        assertThat(testUserAddress.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUserAddress.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testUserAddress.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testUserAddress.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testUserAddress.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void updateNonExistingUserAddress() throws Exception {
        int databaseSizeBeforeUpdate = userAddressRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAddressMockMvc.perform(put("/api/user-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAddress)))
            .andExpect(status().isBadRequest());

        // Validate the UserAddress in the database
        List<UserAddress> userAddressList = userAddressRepository.findAll();
        assertThat(userAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserAddress() throws Exception {
        // Initialize the database
        userAddressService.save(userAddress);

        int databaseSizeBeforeDelete = userAddressRepository.findAll().size();

        // Delete the userAddress
        restUserAddressMockMvc.perform(delete("/api/user-addresses/{id}", userAddress.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserAddress> userAddressList = userAddressRepository.findAll();
        assertThat(userAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
