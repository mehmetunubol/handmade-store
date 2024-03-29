package com.unubol.demo.store.web.rest;

import com.unubol.demo.store.domain.ClientDetails;
import com.unubol.demo.store.service.ClientDetailsService;
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
 * REST controller for managing {@link com.unubol.demo.store.domain.ClientDetails}.
 */
@RestController
@RequestMapping("/api")
public class ClientDetailsResource {

    private final Logger log = LoggerFactory.getLogger(ClientDetailsResource.class);

    private static final String ENTITY_NAME = "clientDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClientDetailsService clientDetailsService;

    public ClientDetailsResource(ClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }

    /**
     * {@code POST  /client-details} : Create a new clientDetails.
     *
     * @param clientDetails the clientDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clientDetails, or with status {@code 400 (Bad Request)} if the clientDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/client-details")
    public ResponseEntity<ClientDetails> createClientDetails(@Valid @RequestBody ClientDetails clientDetails) throws URISyntaxException {
        log.debug("REST request to save ClientDetails : {}", clientDetails);
        if (clientDetails.getId() != null) {
            throw new BadRequestAlertException("A new clientDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClientDetails result = clientDetailsService.save(clientDetails);
        return ResponseEntity.created(new URI("/api/client-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /client-details} : Updates an existing clientDetails.
     *
     * @param clientDetails the clientDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clientDetails,
     * or with status {@code 400 (Bad Request)} if the clientDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clientDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/client-details")
    public ResponseEntity<ClientDetails> updateClientDetails(@Valid @RequestBody ClientDetails clientDetails) throws URISyntaxException {
        log.debug("REST request to update ClientDetails : {}", clientDetails);
        if (clientDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClientDetails result = clientDetailsService.save(clientDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, clientDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /client-details} : get all the clientDetails.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clientDetails in body.
     */
    @GetMapping("/client-details")
    public ResponseEntity<List<ClientDetails>> getAllClientDetails(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of ClientDetails");
        Page<ClientDetails> page;
        if (eagerload) {
            page = clientDetailsService.findAllWithEagerRelationships(pageable);
        } else {
            page = clientDetailsService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /client-details/:id} : get the "id" clientDetails.
     *
     * @param id the id of the clientDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clientDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/client-details/{id}")
    public ResponseEntity<ClientDetails> getClientDetails(@PathVariable Long id) {
        log.debug("REST request to get ClientDetails : {}", id);
        Optional<ClientDetails> clientDetails = clientDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(clientDetails);
    }

    /**
     * {@code DELETE  /client-details/:id} : delete the "id" clientDetails.
     *
     * @param id the id of the clientDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/client-details/{id}")
    public ResponseEntity<Void> deleteClientDetails(@PathVariable Long id) {
        log.debug("REST request to delete ClientDetails : {}", id);
        clientDetailsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
