package com.dirversity.web.rest;

import com.dirversity.service.ContentModuleService;
import com.dirversity.web.rest.errors.BadRequestAlertException;
import com.dirversity.service.dto.ContentModuleDTO;

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
 * REST controller for managing {@link com.dirversity.domain.ContentModule}.
 */
@RestController
@RequestMapping("/api")
public class ContentModuleResource {

    private final Logger log = LoggerFactory.getLogger(ContentModuleResource.class);

    private static final String ENTITY_NAME = "contentModule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContentModuleService contentModuleService;

    public ContentModuleResource(ContentModuleService contentModuleService) {
        this.contentModuleService = contentModuleService;
    }

    /**
     * {@code POST  /content-modules} : Create a new contentModule.
     *
     * @param contentModuleDTO the contentModuleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contentModuleDTO, or with status {@code 400 (Bad Request)} if the contentModule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/content-modules")
    public ResponseEntity<ContentModuleDTO> createContentModule(@Valid @RequestBody ContentModuleDTO contentModuleDTO) throws URISyntaxException {
        log.debug("REST request to save ContentModule : {}", contentModuleDTO);
        if (contentModuleDTO.getId() != null) {
            throw new BadRequestAlertException("A new contentModule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContentModuleDTO result = contentModuleService.save(contentModuleDTO);
        return ResponseEntity.created(new URI("/api/content-modules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /content-modules} : Updates an existing contentModule.
     *
     * @param contentModuleDTO the contentModuleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contentModuleDTO,
     * or with status {@code 400 (Bad Request)} if the contentModuleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contentModuleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/content-modules")
    public ResponseEntity<ContentModuleDTO> updateContentModule(@Valid @RequestBody ContentModuleDTO contentModuleDTO) throws URISyntaxException {
        log.debug("REST request to update ContentModule : {}", contentModuleDTO);
        if (contentModuleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContentModuleDTO result = contentModuleService.save(contentModuleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contentModuleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /content-modules} : get all the contentModules.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contentModules in body.
     */
    @GetMapping("/content-modules")
    public ResponseEntity<List<ContentModuleDTO>> getAllContentModules(Pageable pageable) {
        log.debug("REST request to get a page of ContentModules");
        Page<ContentModuleDTO> page = contentModuleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /content-modules/:id} : get the "id" contentModule.
     *
     * @param id the id of the contentModuleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contentModuleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/content-modules/{id}")
    public ResponseEntity<ContentModuleDTO> getContentModule(@PathVariable Long id) {
        log.debug("REST request to get ContentModule : {}", id);
        Optional<ContentModuleDTO> contentModuleDTO = contentModuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contentModuleDTO);
    }

    /**
     * {@code DELETE  /content-modules/:id} : delete the "id" contentModule.
     *
     * @param id the id of the contentModuleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/content-modules/{id}")
    public ResponseEntity<Void> deleteContentModule(@PathVariable Long id) {
        log.debug("REST request to delete ContentModule : {}", id);
        contentModuleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
