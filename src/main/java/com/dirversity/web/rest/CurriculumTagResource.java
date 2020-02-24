package com.dirversity.web.rest;

import com.dirversity.service.CurriculumTagService;
import com.dirversity.web.rest.errors.BadRequestAlertException;
import com.dirversity.service.dto.CurriculumTagDTO;

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
 * REST controller for managing {@link com.dirversity.domain.CurriculumTag}.
 */
@RestController
@RequestMapping("/api")
public class CurriculumTagResource {

    private final Logger log = LoggerFactory.getLogger(CurriculumTagResource.class);

    private static final String ENTITY_NAME = "curriculumTag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CurriculumTagService curriculumTagService;

    public CurriculumTagResource(CurriculumTagService curriculumTagService) {
        this.curriculumTagService = curriculumTagService;
    }

    /**
     * {@code POST  /curriculum-tags} : Create a new curriculumTag.
     *
     * @param curriculumTagDTO the curriculumTagDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new curriculumTagDTO, or with status {@code 400 (Bad Request)} if the curriculumTag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/curriculum-tags")
    public ResponseEntity<CurriculumTagDTO> createCurriculumTag(@Valid @RequestBody CurriculumTagDTO curriculumTagDTO) throws URISyntaxException {
        log.debug("REST request to save CurriculumTag : {}", curriculumTagDTO);
        if (curriculumTagDTO.getId() != null) {
            throw new BadRequestAlertException("A new curriculumTag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CurriculumTagDTO result = curriculumTagService.save(curriculumTagDTO);
        return ResponseEntity.created(new URI("/api/curriculum-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /curriculum-tags} : Updates an existing curriculumTag.
     *
     * @param curriculumTagDTO the curriculumTagDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated curriculumTagDTO,
     * or with status {@code 400 (Bad Request)} if the curriculumTagDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the curriculumTagDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/curriculum-tags")
    public ResponseEntity<CurriculumTagDTO> updateCurriculumTag(@Valid @RequestBody CurriculumTagDTO curriculumTagDTO) throws URISyntaxException {
        log.debug("REST request to update CurriculumTag : {}", curriculumTagDTO);
        if (curriculumTagDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CurriculumTagDTO result = curriculumTagService.save(curriculumTagDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, curriculumTagDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /curriculum-tags} : get all the curriculumTags.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of curriculumTags in body.
     */
    @GetMapping("/curriculum-tags")
    public ResponseEntity<List<CurriculumTagDTO>> getAllCurriculumTags(Pageable pageable) {
        log.debug("REST request to get a page of CurriculumTags");
        Page<CurriculumTagDTO> page = curriculumTagService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /curriculum-tags/:id} : get the "id" curriculumTag.
     *
     * @param id the id of the curriculumTagDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the curriculumTagDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/curriculum-tags/{id}")
    public ResponseEntity<CurriculumTagDTO> getCurriculumTag(@PathVariable Long id) {
        log.debug("REST request to get CurriculumTag : {}", id);
        Optional<CurriculumTagDTO> curriculumTagDTO = curriculumTagService.findOne(id);
        return ResponseUtil.wrapOrNotFound(curriculumTagDTO);
    }

    /**
     * {@code DELETE  /curriculum-tags/:id} : delete the "id" curriculumTag.
     *
     * @param id the id of the curriculumTagDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/curriculum-tags/{id}")
    public ResponseEntity<Void> deleteCurriculumTag(@PathVariable Long id) {
        log.debug("REST request to delete CurriculumTag : {}", id);
        curriculumTagService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
