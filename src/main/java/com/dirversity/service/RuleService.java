package com.dirversity.service;

import com.dirversity.service.dto.RuleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.dirversity.domain.Rule}.
 */
public interface RuleService {

    /**
     * Save a rule.
     *
     * @param ruleDTO the entity to save.
     * @return the persisted entity.
     */
    RuleDTO save(RuleDTO ruleDTO);

    /**
     * Get all the rules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RuleDTO> findAll(Pageable pageable);

    /**
     * Get all the rules with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<RuleDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" rule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RuleDTO> findOne(Long id);

    /**
     * Delete the "id" rule.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
