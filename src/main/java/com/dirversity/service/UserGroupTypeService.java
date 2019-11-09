package com.dirversity.service;

import com.dirversity.service.dto.UserGroupTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.dirversity.domain.UserGroupType}.
 */
public interface UserGroupTypeService {

    /**
     * Save a userGroupType.
     *
     * @param userGroupTypeDTO the entity to save.
     * @return the persisted entity.
     */
    UserGroupTypeDTO save(UserGroupTypeDTO userGroupTypeDTO);

    /**
     * Get all the userGroupTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserGroupTypeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userGroupType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserGroupTypeDTO> findOne(Long id);

    /**
     * Delete the "id" userGroupType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
