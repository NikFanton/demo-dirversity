package com.dirversity.web.rest;

import com.dirversity.service.UserGroupTypeService;
import com.dirversity.web.rest.errors.BadRequestAlertException;
import com.dirversity.service.dto.UserGroupTypeDTO;

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
 * REST controller for managing {@link com.dirversity.domain.UserGroupType}.
 */
@RestController
@RequestMapping("/api")
public class UserGroupTypeResource {

    private final Logger log = LoggerFactory.getLogger(UserGroupTypeResource.class);

    private static final String ENTITY_NAME = "userGroupType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserGroupTypeService userGroupTypeService;

    public UserGroupTypeResource(UserGroupTypeService userGroupTypeService) {
        this.userGroupTypeService = userGroupTypeService;
    }

    /**
     * {@code POST  /user-group-types} : Create a new userGroupType.
     *
     * @param userGroupTypeDTO the userGroupTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userGroupTypeDTO, or with status {@code 400 (Bad Request)} if the userGroupType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-group-types")
    public ResponseEntity<UserGroupTypeDTO> createUserGroupType(@Valid @RequestBody UserGroupTypeDTO userGroupTypeDTO) throws URISyntaxException {
        log.debug("REST request to save UserGroupType : {}", userGroupTypeDTO);
        if (userGroupTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new userGroupType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserGroupTypeDTO result = userGroupTypeService.save(userGroupTypeDTO);
        return ResponseEntity.created(new URI("/api/user-group-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-group-types} : Updates an existing userGroupType.
     *
     * @param userGroupTypeDTO the userGroupTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userGroupTypeDTO,
     * or with status {@code 400 (Bad Request)} if the userGroupTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userGroupTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-group-types")
    public ResponseEntity<UserGroupTypeDTO> updateUserGroupType(@Valid @RequestBody UserGroupTypeDTO userGroupTypeDTO) throws URISyntaxException {
        log.debug("REST request to update UserGroupType : {}", userGroupTypeDTO);
        if (userGroupTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserGroupTypeDTO result = userGroupTypeService.save(userGroupTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userGroupTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-group-types} : get all the userGroupTypes.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userGroupTypes in body.
     */
    @GetMapping("/user-group-types")
    public ResponseEntity<List<UserGroupTypeDTO>> getAllUserGroupTypes(Pageable pageable) {
        log.debug("REST request to get a page of UserGroupTypes");
        Page<UserGroupTypeDTO> page = userGroupTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-group-types/:id} : get the "id" userGroupType.
     *
     * @param id the id of the userGroupTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userGroupTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-group-types/{id}")
    public ResponseEntity<UserGroupTypeDTO> getUserGroupType(@PathVariable Long id) {
        log.debug("REST request to get UserGroupType : {}", id);
        Optional<UserGroupTypeDTO> userGroupTypeDTO = userGroupTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userGroupTypeDTO);
    }

    /**
     * {@code DELETE  /user-group-types/:id} : delete the "id" userGroupType.
     *
     * @param id the id of the userGroupTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-group-types/{id}")
    public ResponseEntity<Void> deleteUserGroupType(@PathVariable Long id) {
        log.debug("REST request to delete UserGroupType : {}", id);
        userGroupTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
