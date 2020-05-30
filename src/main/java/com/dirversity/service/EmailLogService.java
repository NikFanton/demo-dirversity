package com.dirversity.service;

import com.dirversity.service.dto.EmailLogDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.dirversity.domain.EmailLog}.
 */
public interface EmailLogService {

    /**
     * Save a emailLog.
     *
     * @param emailLogDTO the entity to save.
     * @return the persisted entity.
     */
    EmailLogDTO save(EmailLogDTO emailLogDTO);

    /**
     * Get all the emailLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EmailLogDTO> findAll(Pageable pageable);

    /**
     * Get all the emailLogs with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<EmailLogDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" emailLog.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmailLogDTO> findOne(Long id);

    /**
     * Delete the "id" emailLog.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
