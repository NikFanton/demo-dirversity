package com.dirversity.service.impl;

import com.dirversity.service.UserGroupTypeService;
import com.dirversity.domain.UserGroupType;
import com.dirversity.repository.UserGroupTypeRepository;
import com.dirversity.service.dto.UserGroupTypeDTO;
import com.dirversity.service.mapper.UserGroupTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserGroupType}.
 */
@Service
@Transactional
public class UserGroupTypeServiceImpl implements UserGroupTypeService {

    private final Logger log = LoggerFactory.getLogger(UserGroupTypeServiceImpl.class);

    private final UserGroupTypeRepository userGroupTypeRepository;

    private final UserGroupTypeMapper userGroupTypeMapper;

    public UserGroupTypeServiceImpl(UserGroupTypeRepository userGroupTypeRepository, UserGroupTypeMapper userGroupTypeMapper) {
        this.userGroupTypeRepository = userGroupTypeRepository;
        this.userGroupTypeMapper = userGroupTypeMapper;
    }

    /**
     * Save a userGroupType.
     *
     * @param userGroupTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UserGroupTypeDTO save(UserGroupTypeDTO userGroupTypeDTO) {
        log.debug("Request to save UserGroupType : {}", userGroupTypeDTO);
        UserGroupType userGroupType = userGroupTypeMapper.toEntity(userGroupTypeDTO);
        userGroupType = userGroupTypeRepository.save(userGroupType);
        return userGroupTypeMapper.toDto(userGroupType);
    }

    /**
     * Get all the userGroupTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserGroupTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserGroupTypes");
        return userGroupTypeRepository.findAll(pageable)
            .map(userGroupTypeMapper::toDto);
    }


    /**
     * Get one userGroupType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserGroupTypeDTO> findOne(Long id) {
        log.debug("Request to get UserGroupType : {}", id);
        return userGroupTypeRepository.findById(id)
            .map(userGroupTypeMapper::toDto);
    }

    /**
     * Delete the userGroupType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserGroupType : {}", id);
        userGroupTypeRepository.deleteById(id);
    }
}
