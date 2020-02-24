package com.dirversity.service;

import com.dirversity.service.dto.CurriculumTagDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.dirversity.domain.CurriculumTag}.
 */
public interface CurriculumTagService {

    /**
     * Save a curriculumTag.
     *
     * @param curriculumTagDTO the entity to save.
     * @return the persisted entity.
     */
    CurriculumTagDTO save(CurriculumTagDTO curriculumTagDTO);

    /**
     * Get all the curriculumTags.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CurriculumTagDTO> findAll(Pageable pageable);


    /**
     * Get the "id" curriculumTag.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CurriculumTagDTO> findOne(Long id);

    /**
     * Delete the "id" curriculumTag.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
