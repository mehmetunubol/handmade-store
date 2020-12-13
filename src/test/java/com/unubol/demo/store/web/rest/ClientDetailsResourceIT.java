package com.unubol.demo.store.web.rest;

import com.unubol.demo.store.StoreApp;
import com.unubol.demo.store.domain.ClientDetails;
import com.unubol.demo.store.domain.User;
import com.unubol.demo.store.domain.Cart;
import com.unubol.demo.store.repository.ClientDetailsRepository;
import com.unubol.demo.store.service.ClientDetailsService;

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

import com.unubol.demo.store.domain.enumeration.Gender;
/**
 * Integration tests for the {@link ClientDetailsResource} REST controller.
 */
@SpringBootTest(classes = StoreApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ClientDetailsResourceIT {

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    @Autowired
    private ClientDetailsRepository clientDetailsRepository;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClientDetailsMockMvc;

    private ClientDetails clientDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientDetails createEntity(EntityManager em) {
        ClientDetails clientDetails = new ClientDetails()
            .gender(DEFAULT_GENDER)
            .phone(DEFAULT_PHONE)
            .city(DEFAULT_CITY)
            .country(DEFAULT_COUNTRY);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        clientDetails.setUser(user);
        // Add required entity
        Cart cart;
        if (TestUtil.findAll(em, Cart.class).isEmpty()) {
            cart = CartResourceIT.createEntity(em);
            em.persist(cart);
            em.flush();
        } else {
            cart = TestUtil.findAll(em, Cart.class).get(0);
        }
        clientDetails.getCarts().add(cart);
        return clientDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientDetails createUpdatedEntity(EntityManager em) {
        ClientDetails clientDetails = new ClientDetails()
            .gender(UPDATED_GENDER)
            .phone(UPDATED_PHONE)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        clientDetails.setUser(user);
        // Add required entity
        Cart cart;
        if (TestUtil.findAll(em, Cart.class).isEmpty()) {
            cart = CartResourceIT.createUpdatedEntity(em);
            em.persist(cart);
            em.flush();
        } else {
            cart = TestUtil.findAll(em, Cart.class).get(0);
        }
        clientDetails.getCarts().add(cart);
        return clientDetails;
    }

    @BeforeEach
    public void initTest() {
        clientDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createClientDetails() throws Exception {
        int databaseSizeBeforeCreate = clientDetailsRepository.findAll().size();
        // Create the ClientDetails
        restClientDetailsMockMvc.perform(post("/api/client-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientDetails)))
            .andExpect(status().isCreated());

        // Validate the ClientDetails in the database
        List<ClientDetails> clientDetailsList = clientDetailsRepository.findAll();
        assertThat(clientDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        ClientDetails testClientDetails = clientDetailsList.get(clientDetailsList.size() - 1);
        assertThat(testClientDetails.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testClientDetails.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testClientDetails.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testClientDetails.getCountry()).isEqualTo(DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    public void createClientDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientDetailsRepository.findAll().size();

        // Create the ClientDetails with an existing ID
        clientDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientDetailsMockMvc.perform(post("/api/client-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientDetails)))
            .andExpect(status().isBadRequest());

        // Validate the ClientDetails in the database
        List<ClientDetails> clientDetailsList = clientDetailsRepository.findAll();
        assertThat(clientDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientDetailsRepository.findAll().size();
        // set the field null
        clientDetails.setGender(null);

        // Create the ClientDetails, which fails.


        restClientDetailsMockMvc.perform(post("/api/client-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientDetails)))
            .andExpect(status().isBadRequest());

        List<ClientDetails> clientDetailsList = clientDetailsRepository.findAll();
        assertThat(clientDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientDetailsRepository.findAll().size();
        // set the field null
        clientDetails.setPhone(null);

        // Create the ClientDetails, which fails.


        restClientDetailsMockMvc.perform(post("/api/client-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientDetails)))
            .andExpect(status().isBadRequest());

        List<ClientDetails> clientDetailsList = clientDetailsRepository.findAll();
        assertThat(clientDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientDetailsRepository.findAll().size();
        // set the field null
        clientDetails.setCity(null);

        // Create the ClientDetails, which fails.


        restClientDetailsMockMvc.perform(post("/api/client-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientDetails)))
            .andExpect(status().isBadRequest());

        List<ClientDetails> clientDetailsList = clientDetailsRepository.findAll();
        assertThat(clientDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = clientDetailsRepository.findAll().size();
        // set the field null
        clientDetails.setCountry(null);

        // Create the ClientDetails, which fails.


        restClientDetailsMockMvc.perform(post("/api/client-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientDetails)))
            .andExpect(status().isBadRequest());

        List<ClientDetails> clientDetailsList = clientDetailsRepository.findAll();
        assertThat(clientDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClientDetails() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get all the clientDetailsList
        restClientDetailsMockMvc.perform(get("/api/client-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)));
    }
    
    @Test
    @Transactional
    public void getClientDetails() throws Exception {
        // Initialize the database
        clientDetailsRepository.saveAndFlush(clientDetails);

        // Get the clientDetails
        restClientDetailsMockMvc.perform(get("/api/client-details/{id}", clientDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clientDetails.getId().intValue()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY));
    }
    @Test
    @Transactional
    public void getNonExistingClientDetails() throws Exception {
        // Get the clientDetails
        restClientDetailsMockMvc.perform(get("/api/client-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClientDetails() throws Exception {
        // Initialize the database
        clientDetailsService.save(clientDetails);

        int databaseSizeBeforeUpdate = clientDetailsRepository.findAll().size();

        // Update the clientDetails
        ClientDetails updatedClientDetails = clientDetailsRepository.findById(clientDetails.getId()).get();
        // Disconnect from session so that the updates on updatedClientDetails are not directly saved in db
        em.detach(updatedClientDetails);
        updatedClientDetails
            .gender(UPDATED_GENDER)
            .phone(UPDATED_PHONE)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY);

        restClientDetailsMockMvc.perform(put("/api/client-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedClientDetails)))
            .andExpect(status().isOk());

        // Validate the ClientDetails in the database
        List<ClientDetails> clientDetailsList = clientDetailsRepository.findAll();
        assertThat(clientDetailsList).hasSize(databaseSizeBeforeUpdate);
        ClientDetails testClientDetails = clientDetailsList.get(clientDetailsList.size() - 1);
        assertThat(testClientDetails.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testClientDetails.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testClientDetails.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testClientDetails.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void updateNonExistingClientDetails() throws Exception {
        int databaseSizeBeforeUpdate = clientDetailsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientDetailsMockMvc.perform(put("/api/client-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clientDetails)))
            .andExpect(status().isBadRequest());

        // Validate the ClientDetails in the database
        List<ClientDetails> clientDetailsList = clientDetailsRepository.findAll();
        assertThat(clientDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClientDetails() throws Exception {
        // Initialize the database
        clientDetailsService.save(clientDetails);

        int databaseSizeBeforeDelete = clientDetailsRepository.findAll().size();

        // Delete the clientDetails
        restClientDetailsMockMvc.perform(delete("/api/client-details/{id}", clientDetails.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClientDetails> clientDetailsList = clientDetailsRepository.findAll();
        assertThat(clientDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
