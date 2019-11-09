package com.dirversity.service.impl;

import com.dirversity.service.ResourceTypeService;
import com.dirversity.domain.ResourceType;
import com.dirversity.repository.ResourceTypeRepository;
import com.dirversity.service.dto.ResourceTypeDTO;
import com.dirversity.service.mapper.ResourceTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ResourceType}.
 */
@Service
@Transactional
public class ResourceTypeServiceImpl implements ResourceTypeService {

    private final Logger log = LoggerFactory.getLogger(ResourceTypeServiceImpl.class);

    private final ResourceTypeRepository resourceTypeRepository;

    private final ResourceTypeMapper resourceTypeMapper;

    public ResourceTypeServiceImpl(ResourceTypeRepository resourceTypeRepository, ResourceTypeMapper resourceTypeMapper) {
        this.resourceTypeRepository = resourceTypeRepository;
        this.resourceTypeMapper = resourceTypeMapper;
    }

    /**
     * Save a resourceType.
     *
     * @param resourceTypeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ResourceTypeDTO save(ResourceTypeDTO resourceTypeDTO) {
        log.debug("Request to save ResourceType : {}", resourceTypeDTO);
        ResourceType resourceType = resourceTypeMapper.toEntity(resourceTypeDTO);
        resourceType = resourceTypeRepository.save(resourceType);
        return resourceTypeMapper.toDto(resourceType);
    }

    /**
     * Get all the resourceTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ResourceTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ResourceTypes");
        return resourceTypeRepository.findAll(pageable)
            .map(resourceTypeMapper::toDto);
    }


    /**
     * Get one resourceType by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ResourceTypeDTO> findOne(Long id) {
        log.debug("Request to get ResourceType : {}", id);
        return resourceTypeRepository.findById(id)
            .map(resourceTypeMapper::toDto);
    }

    /**
     * Delete the resourceType by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ResourceType : {}", id);
        resourceTypeRepository.deleteById(id);
    }
}
