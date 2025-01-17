package com.dirversity.web.rest;

import com.dirversity.security.AuthoritiesConstants;
import com.dirversity.security.SecurityUtils;
import com.dirversity.service.CloudStorageService;
import com.dirversity.service.ResourceService;
import com.dirversity.service.UserService;
import com.dirversity.service.dto.ResourceDTO;
import com.dirversity.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.dirversity.domain.Resource}.
 */
@RestController
@RequestMapping("/api")
public class ResourceResource {

    private final Logger log = LoggerFactory.getLogger(ResourceResource.class);

    private static final String ENTITY_NAME = "resource";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResourceService resourceService;
    private final UserService userService;
    private final CloudStorageService cloudStorageService;

    public ResourceResource(ResourceService resourceService, UserService userService, CloudStorageService cloudStorageService) {
        this.resourceService = resourceService;
        this.userService = userService;
        this.cloudStorageService = cloudStorageService;
    }

    /**
     * {@code POST  /resources} : Create a new resource.
     *
     * @param resourceDTO the resourceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resourceDTO, or with status {@code 400 (Bad Request)} if the resource has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resources")
    public ResponseEntity<ResourceDTO> createResource(@RequestBody ResourceDTO resourceDTO) throws URISyntaxException {
        log.debug("REST request to save Resource : {}", resourceDTO);
        if (resourceDTO.getId() != null) {
            throw new BadRequestAlertException("A new resource cannot already have an ID", ENTITY_NAME, "idexists");
        }

//        ToDo Refactor this to make it less coupled
        uploadResourceToCloudStorage(resourceDTO);

        ResourceDTO result = resourceService.save(resourceDTO);
        return ResponseEntity.created(new URI("/api/resources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    private Instant getNow() {
        LocalDateTime dateTime = LocalDateTime.now();
        return dateTime.atZone(ZoneId.systemDefault()).toInstant();
    }

    /**
     * {@code PUT  /resources} : Updates an existing resource.
     *
     * @param resourceDTO the resourceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resourceDTO,
     * or with status {@code 400 (Bad Request)} if the resourceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resourceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resources")
    public ResponseEntity<ResourceDTO> updateResource(@RequestBody ResourceDTO resourceDTO) throws URISyntaxException {
        log.debug("REST request to update Resource : {}", resourceDTO);
        if (resourceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        uploadResourceToCloudStorage(resourceDTO);
        ResourceDTO result = resourceService.save(resourceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, resourceDTO.getId().toString()))
            .body(result);
    }

    private void uploadResourceToCloudStorage(ResourceDTO resourceDTO) {
        if (resourceDTO.getData() != null && resourceDTO.getDataContentType() != null) {
            cloudStorageService
                .uploadFileData(resourceDTO.getDataContentType(), resourceDTO.getDataDisplayName(), resourceDTO.getData())
                .ifPresent(file -> {
                    resourceDTO.setFileId(file.getId());
                    resourceDTO.setAccessUrl(file.getWebViewLink());
                });
        }
    }


    /**
     * {@code GET  /resources} : get all the resources.
     *
     * @param pageable  the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resources in body.
     */
    @GetMapping("/resources")
    public ResponseEntity<List<ResourceDTO>> getAllResources(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Resources");

        Page<ResourceDTO> page;
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            if (eagerload) {
                page = resourceService.findAllWithEagerRelationships(pageable);
            } else {
                page = resourceService.findAll(pageable);
            }
        } else {
            page = resourceService.findAllResourcesCreatedByCurrentUser(pageable);
        }


        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /resources/:id} : get the "id" resource.
     *
     * @param id the id of the resourceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resourceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resources/{id}")
    public ResponseEntity<ResourceDTO> getResource(@PathVariable Long id) {
        log.debug("REST request to get Resource : {}", id);
        Optional<ResourceDTO> resourceDTO = resourceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(resourceDTO);
    }

    /**
     * {@code DELETE  /resources/:id} : delete the "id" resource.
     *
     * @param id the id of the resourceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resources/{id}")
    public ResponseEntity<Void> deleteResource(@PathVariable Long id) {
        log.debug("REST request to delete Resource : {}", id);
        resourceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
