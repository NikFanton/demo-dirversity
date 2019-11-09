package com.dirversity.service;

import com.dirversity.service.dto.ResourceTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.dirversity.domain.ResourceType}.
 */
public interface ResourceTypeService {

    /**
     * Save a resourceType.
     *
     * @param resourceTypeDTO the entity to save.
     * @return the persisted entity.
     */
    ResourceTypeDTO save(ResourceTypeDTO resourceTypeDTO);

    /**
     * Get all the resourceTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ResourceTypeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" resourceType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ResourceTypeDTO> findOne(Long id);

    /**
     * Delete the "id" resourceType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
