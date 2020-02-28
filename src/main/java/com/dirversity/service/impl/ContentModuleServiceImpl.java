package com.dirversity.service.impl;

import com.dirversity.service.ContentModuleService;
import com.dirversity.domain.ContentModule;
import com.dirversity.repository.ContentModuleRepository;
import com.dirversity.service.dto.ContentModuleDTO;
import com.dirversity.service.mapper.ContentModuleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ContentModule}.
 */
@Service
@Transactional
public class ContentModuleServiceImpl implements ContentModuleService {

    private final Logger log = LoggerFactory.getLogger(ContentModuleServiceImpl.class);

    private final ContentModuleRepository contentModuleRepository;

    private final ContentModuleMapper contentModuleMapper;

    public ContentModuleServiceImpl(ContentModuleRepository contentModuleRepository, ContentModuleMapper contentModuleMapper) {
        this.contentModuleRepository = contentModuleRepository;
        this.contentModuleMapper = contentModuleMapper;
    }

    /**
     * Save a contentModule.
     *
     * @param contentModuleDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ContentModuleDTO save(ContentModuleDTO contentModuleDTO) {
        log.debug("Request to save ContentModule : {}", contentModuleDTO);
        ContentModule contentModule = contentModuleMapper.toEntity(contentModuleDTO);
        contentModule = contentModuleRepository.save(contentModule);
        return contentModuleMapper.toDto(contentModule);
    }

    /**
     * Get all the contentModules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ContentModuleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ContentModules");
        return contentModuleRepository.findAll(pageable)
            .map(contentModuleMapper::toDto);
    }

    @Override
    public Page<ContentModuleDTO> findAllForCurriculum(Pageable pageable, Long curriculumId) {
        log.debug("Request to get all ContentModules for specific Curriculum");
        return contentModuleRepository.findAllForCurriculum(pageable, curriculumId)
            .map(contentModuleMapper::toDto);
    }

    /**
     * Get one contentModule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ContentModuleDTO> findOne(Long id) {
        log.debug("Request to get ContentModule : {}", id);
        return contentModuleRepository.findById(id)
            .map(contentModuleMapper::toDto);
    }

    /**
     * Delete the contentModule by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContentModule : {}", id);
        contentModuleRepository.deleteById(id);
    }
}
