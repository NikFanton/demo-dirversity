package com.dirversity.service;

import com.dirversity.domain.Email;
import com.dirversity.service.dto.EmailDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.dirversity.domain.Email}.
 */
public interface EmailService {

    /**
     * Save a email.
     *
     * @param emailDTO the entity to save.
     * @return the persisted entity.
     */
    EmailDTO save(EmailDTO emailDTO);

    /**
     * Get all the emails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EmailDTO> findAll(Pageable pageable);

    /**
     * Get all the emails with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<EmailDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" email.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmailDTO> findOne(Long id);

    /**
     * Get all the emails ready to be sent based on {@link com.dirversity.domain.Email#shareDateTime}.
     *
     * @param rangeInMillis the range in milliseconds.
     * @return the list of entities.
     */
    List<Email> findEmailReadyToBeSentNow(int rangeInMillis);

    /**
     * Get the "id" email.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Email> findOneEmail(Long id);

    /**
     * Delete the "id" email.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
