package com.dirversity.service;

import com.dirversity.service.dto.CurriculumDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.dirversity.domain.Curriculum}.
 */
public interface CurriculumService {

    /**
     * Save a curriculum.
     *
     * @param curriculumDTO the entity to save.
     * @return the persisted entity.
     */
    CurriculumDTO save(CurriculumDTO curriculumDTO);

    /**
     * Get all the curricula.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CurriculumDTO> findAll(Pageable pageable);

    /**
     * Get all the curricula with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<CurriculumDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" curriculum.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CurriculumDTO> findOne(Long id);

    /**
     * Delete the "id" curriculum.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
