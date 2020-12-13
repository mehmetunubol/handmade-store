package com.unubol.demo.store.web.rest;

import com.unubol.demo.store.domain.AttributeValues;
import com.unubol.demo.store.service.AttributeValuesService;
import com.unubol.demo.store.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.unubol.demo.store.domain.AttributeValues}.
 */
@RestController
@RequestMapping("/api")
public class AttributeValuesResource {

    private final Logger log = LoggerFactory.getLogger(AttributeValuesResource.class);

    private static final String ENTITY_NAME = "attributeValues";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttributeValuesService attributeValuesService;

    public AttributeValuesResource(AttributeValuesService attributeValuesService) {
        this.attributeValuesService = attributeValuesService;
    }

    /**
     * {@code POST  /attribute-values} : Create a new attributeValues.
     *
     * @param attributeValues the attributeValues to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attributeValues, or with status {@code 400 (Bad Request)} if the attributeValues has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attribute-values")
    public ResponseEntity<AttributeValues> createAttributeValues(@Valid @RequestBody AttributeValues attributeValues) throws URISyntaxException {
        log.debug("REST request to save AttributeValues : {}", attributeValues);
        if (attributeValues.getId() != null) {
            throw new BadRequestAlertException("A new attributeValues cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttributeValues result = attributeValuesService.save(attributeValues);
        return ResponseEntity.created(new URI("/api/attribute-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attribute-values} : Updates an existing attributeValues.
     *
     * @param attributeValues the attributeValues to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attributeValues,
     * or with status {@code 400 (Bad Request)} if the attributeValues is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attributeValues couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attribute-values")
    public ResponseEntity<AttributeValues> updateAttributeValues(@Valid @RequestBody AttributeValues attributeValues) throws URISyntaxException {
        log.debug("REST request to update AttributeValues : {}", attributeValues);
        if (attributeValues.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AttributeValues result = attributeValuesService.save(attributeValues);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, attributeValues.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /attribute-values} : get all the attributeValues.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attributeValues in body.
     */
    @GetMapping("/attribute-values")
    public ResponseEntity<List<AttributeValues>> getAllAttributeValues(Pageable pageable) {
        log.debug("REST request to get a page of AttributeValues");
        Page<AttributeValues> page = attributeValuesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /attribute-values/:id} : get the "id" attributeValues.
     *
     * @param id the id of the attributeValues to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attributeValues, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attribute-values/{id}")
    public ResponseEntity<AttributeValues> getAttributeValues(@PathVariable Long id) {
        log.debug("REST request to get AttributeValues : {}", id);
        Optional<AttributeValues> attributeValues = attributeValuesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attributeValues);
    }

    /**
     * {@code DELETE  /attribute-values/:id} : delete the "id" attributeValues.
     *
     * @param id the id of the attributeValues to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attribute-values/{id}")
    public ResponseEntity<Void> deleteAttributeValues(@PathVariable Long id) {
        log.debug("REST request to delete AttributeValues : {}", id);
        attributeValuesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
