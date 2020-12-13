package com.unubol.demo.store.web.rest;

import com.unubol.demo.store.StoreApp;
import com.unubol.demo.store.domain.AttributeValues;
import com.unubol.demo.store.repository.AttributeValuesRepository;
import com.unubol.demo.store.service.AttributeValuesService;

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
 * Integration tests for the {@link AttributeValuesResource} REST controller.
 */
@SpringBootTest(classes = StoreApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AttributeValuesResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(0);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(1);

    @Autowired
    private AttributeValuesRepository attributeValuesRepository;

    @Autowired
    private AttributeValuesService attributeValuesService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttributeValuesMockMvc;

    private AttributeValues attributeValues;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttributeValues createEntity(EntityManager em) {
        AttributeValues attributeValues = new AttributeValues()
            .value(DEFAULT_VALUE)
            .price(DEFAULT_PRICE);
        return attributeValues;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttributeValues createUpdatedEntity(EntityManager em) {
        AttributeValues attributeValues = new AttributeValues()
            .value(UPDATED_VALUE)
            .price(UPDATED_PRICE);
        return attributeValues;
    }

    @BeforeEach
    public void initTest() {
        attributeValues = createEntity(em);
    }

    @Test
    @Transactional
    public void createAttributeValues() throws Exception {
        int databaseSizeBeforeCreate = attributeValuesRepository.findAll().size();
        // Create the AttributeValues
        restAttributeValuesMockMvc.perform(post("/api/attribute-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attributeValues)))
            .andExpect(status().isCreated());

        // Validate the AttributeValues in the database
        List<AttributeValues> attributeValuesList = attributeValuesRepository.findAll();
        assertThat(attributeValuesList).hasSize(databaseSizeBeforeCreate + 1);
        AttributeValues testAttributeValues = attributeValuesList.get(attributeValuesList.size() - 1);
        assertThat(testAttributeValues.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testAttributeValues.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createAttributeValuesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = attributeValuesRepository.findAll().size();

        // Create the AttributeValues with an existing ID
        attributeValues.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttributeValuesMockMvc.perform(post("/api/attribute-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attributeValues)))
            .andExpect(status().isBadRequest());

        // Validate the AttributeValues in the database
        List<AttributeValues> attributeValuesList = attributeValuesRepository.findAll();
        assertThat(attributeValuesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = attributeValuesRepository.findAll().size();
        // set the field null
        attributeValues.setValue(null);

        // Create the AttributeValues, which fails.


        restAttributeValuesMockMvc.perform(post("/api/attribute-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attributeValues)))
            .andExpect(status().isBadRequest());

        List<AttributeValues> attributeValuesList = attributeValuesRepository.findAll();
        assertThat(attributeValuesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = attributeValuesRepository.findAll().size();
        // set the field null
        attributeValues.setPrice(null);

        // Create the AttributeValues, which fails.


        restAttributeValuesMockMvc.perform(post("/api/attribute-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attributeValues)))
            .andExpect(status().isBadRequest());

        List<AttributeValues> attributeValuesList = attributeValuesRepository.findAll();
        assertThat(attributeValuesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAttributeValues() throws Exception {
        // Initialize the database
        attributeValuesRepository.saveAndFlush(attributeValues);

        // Get all the attributeValuesList
        restAttributeValuesMockMvc.perform(get("/api/attribute-values?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attributeValues.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())));
    }
    
    @Test
    @Transactional
    public void getAttributeValues() throws Exception {
        // Initialize the database
        attributeValuesRepository.saveAndFlush(attributeValues);

        // Get the attributeValues
        restAttributeValuesMockMvc.perform(get("/api/attribute-values/{id}", attributeValues.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attributeValues.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingAttributeValues() throws Exception {
        // Get the attributeValues
        restAttributeValuesMockMvc.perform(get("/api/attribute-values/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttributeValues() throws Exception {
        // Initialize the database
        attributeValuesService.save(attributeValues);

        int databaseSizeBeforeUpdate = attributeValuesRepository.findAll().size();

        // Update the attributeValues
        AttributeValues updatedAttributeValues = attributeValuesRepository.findById(attributeValues.getId()).get();
        // Disconnect from session so that the updates on updatedAttributeValues are not directly saved in db
        em.detach(updatedAttributeValues);
        updatedAttributeValues
            .value(UPDATED_VALUE)
            .price(UPDATED_PRICE);

        restAttributeValuesMockMvc.perform(put("/api/attribute-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAttributeValues)))
            .andExpect(status().isOk());

        // Validate the AttributeValues in the database
        List<AttributeValues> attributeValuesList = attributeValuesRepository.findAll();
        assertThat(attributeValuesList).hasSize(databaseSizeBeforeUpdate);
        AttributeValues testAttributeValues = attributeValuesList.get(attributeValuesList.size() - 1);
        assertThat(testAttributeValues.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testAttributeValues.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingAttributeValues() throws Exception {
        int databaseSizeBeforeUpdate = attributeValuesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttributeValuesMockMvc.perform(put("/api/attribute-values")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attributeValues)))
            .andExpect(status().isBadRequest());

        // Validate the AttributeValues in the database
        List<AttributeValues> attributeValuesList = attributeValuesRepository.findAll();
        assertThat(attributeValuesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAttributeValues() throws Exception {
        // Initialize the database
        attributeValuesService.save(attributeValues);

        int databaseSizeBeforeDelete = attributeValuesRepository.findAll().size();

        // Delete the attributeValues
        restAttributeValuesMockMvc.perform(delete("/api/attribute-values/{id}", attributeValues.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AttributeValues> attributeValuesList = attributeValuesRepository.findAll();
        assertThat(attributeValuesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
