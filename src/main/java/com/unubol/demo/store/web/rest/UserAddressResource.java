package com.unubol.demo.store.web.rest;

import com.unubol.demo.store.domain.UserAddress;
import com.unubol.demo.store.service.UserAddressService;
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
 * REST controller for managing {@link com.unubol.demo.store.domain.UserAddress}.
 */
@RestController
@RequestMapping("/api")
public class UserAddressResource {

    private final Logger log = LoggerFactory.getLogger(UserAddressResource.class);

    private static final String ENTITY_NAME = "userAddress";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserAddressService userAddressService;

    public UserAddressResource(UserAddressService userAddressService) {
        this.userAddressService = userAddressService;
    }

    /**
     * {@code POST  /user-addresses} : Create a new userAddress.
     *
     * @param userAddress the userAddress to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userAddress, or with status {@code 400 (Bad Request)} if the userAddress has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-addresses")
    public ResponseEntity<UserAddress> createUserAddress(@Valid @RequestBody UserAddress userAddress) throws URISyntaxException {
        log.debug("REST request to save UserAddress : {}", userAddress);
        if (userAddress.getId() != null) {
            throw new BadRequestAlertException("A new userAddress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserAddress result = userAddressService.save(userAddress);
        return ResponseEntity.created(new URI("/api/user-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-addresses} : Updates an existing userAddress.
     *
     * @param userAddress the userAddress to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userAddress,
     * or with status {@code 400 (Bad Request)} if the userAddress is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userAddress couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-addresses")
    public ResponseEntity<UserAddress> updateUserAddress(@Valid @RequestBody UserAddress userAddress) throws URISyntaxException {
        log.debug("REST request to update UserAddress : {}", userAddress);
        if (userAddress.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserAddress result = userAddressService.save(userAddress);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userAddress.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-addresses} : get all the userAddresses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userAddresses in body.
     */
    @GetMapping("/user-addresses")
    public ResponseEntity<List<UserAddress>> getAllUserAddresses(Pageable pageable) {
        log.debug("REST request to get a page of UserAddresses");
        Page<UserAddress> page = userAddressService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-addresses/:id} : get the "id" userAddress.
     *
     * @param id the id of the userAddress to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userAddress, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-addresses/{id}")
    public ResponseEntity<UserAddress> getUserAddress(@PathVariable Long id) {
        log.debug("REST request to get UserAddress : {}", id);
        Optional<UserAddress> userAddress = userAddressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userAddress);
    }

    /**
     * {@code DELETE  /user-addresses/:id} : delete the "id" userAddress.
     *
     * @param id the id of the userAddress to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-addresses/{id}")
    public ResponseEntity<Void> deleteUserAddress(@PathVariable Long id) {
        log.debug("REST request to delete UserAddress : {}", id);
        userAddressService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
