package com.dirversity.service.impl;

import com.dirversity.service.UserGroupService;
import com.dirversity.domain.UserGroup;
import com.dirversity.repository.UserGroupRepository;
import com.dirversity.service.dto.UserGroupDTO;
import com.dirversity.service.mapper.UserGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserGroup}.
 */
@Service
@Transactional
public class UserGroupServiceImpl implements UserGroupService {

    private final Logger log = LoggerFactory.getLogger(UserGroupServiceImpl.class);

    private final UserGroupRepository userGroupRepository;

    private final UserGroupMapper userGroupMapper;

    public UserGroupServiceImpl(UserGroupRepository userGroupRepository, UserGroupMapper userGroupMapper) {
        this.userGroupRepository = userGroupRepository;
        this.userGroupMapper = userGroupMapper;
    }

    /**
     * Save a userGroup.
     *
     * @param userGroupDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UserGroupDTO save(UserGroupDTO userGroupDTO) {
        log.debug("Request to save UserGroup : {}", userGroupDTO);
        UserGroup userGroup = userGroupMapper.toEntity(userGroupDTO);
        userGroup = userGroupRepository.save(userGroup);
        return userGroupMapper.toDto(userGroup);
    }

    /**
     * Get all the userGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserGroups");
        return userGroupRepository.findAll(pageable)
            .map(userGroupMapper::toDto);
    }

    /**
     * Get all the userGroups with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<UserGroupDTO> findAllWithEagerRelationships(Pageable pageable) {
        return userGroupRepository.findAllWithEagerRelationships(pageable).map(userGroupMapper::toDto);
    }
    

    /**
     * Get one userGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserGroupDTO> findOne(Long id) {
        log.debug("Request to get UserGroup : {}", id);
        return userGroupRepository.findOneWithEagerRelationships(id)
            .map(userGroupMapper::toDto);
    }

    /**
     * Delete the userGroup by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserGroup : {}", id);
        userGroupRepository.deleteById(id);
    }
}
