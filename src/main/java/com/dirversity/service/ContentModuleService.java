package com.dirversity.service;

import com.dirversity.service.dto.ContentModuleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.dirversity.domain.ContentModule}.
 */
public interface ContentModuleService {

    /**
     * Save a contentModule.
     *
     * @param contentModuleDTO the entity to save.
     * @return the persisted entity.
     */
    ContentModuleDTO save(ContentModuleDTO contentModuleDTO);

    /**
     * Get all the contentModules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ContentModuleDTO> findAll(Pageable pageable);


    Page<ContentModuleDTO> findAllForCurriculum(Pageable pageable, Long curriculumId);

    /**
     * Get the "id" contentModule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContentModuleDTO> findOne(Long id);

    /**
     * Delete the "id" contentModule.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
