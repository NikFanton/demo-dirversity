package com.dirversity.service.impl;

import com.dirversity.service.CurriculumTagService;
import com.dirversity.domain.CurriculumTag;
import com.dirversity.repository.CurriculumTagRepository;
import com.dirversity.service.dto.CurriculumTagDTO;
import com.dirversity.service.mapper.CurriculumTagMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CurriculumTag}.
 */
@Service
@Transactional
public class CurriculumTagServiceImpl implements CurriculumTagService {

    private final Logger log = LoggerFactory.getLogger(CurriculumTagServiceImpl.class);

    private final CurriculumTagRepository curriculumTagRepository;

    private final CurriculumTagMapper curriculumTagMapper;

    public CurriculumTagServiceImpl(CurriculumTagRepository curriculumTagRepository, CurriculumTagMapper curriculumTagMapper) {
        this.curriculumTagRepository = curriculumTagRepository;
        this.curriculumTagMapper = curriculumTagMapper;
    }

    /**
     * Save a curriculumTag.
     *
     * @param curriculumTagDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CurriculumTagDTO save(CurriculumTagDTO curriculumTagDTO) {
        log.debug("Request to save CurriculumTag : {}", curriculumTagDTO);
        CurriculumTag curriculumTag = curriculumTagMapper.toEntity(curriculumTagDTO);
        curriculumTag = curriculumTagRepository.save(curriculumTag);
        return curriculumTagMapper.toDto(curriculumTag);
    }

    /**
     * Get all the curriculumTags.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CurriculumTagDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CurriculumTags");
        return curriculumTagRepository.findAll(pageable)
            .map(curriculumTagMapper::toDto);
    }


    /**
     * Get one curriculumTag by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CurriculumTagDTO> findOne(Long id) {
        log.debug("Request to get CurriculumTag : {}", id);
        return curriculumTagRepository.findById(id)
            .map(curriculumTagMapper::toDto);
    }

    /**
     * Delete the curriculumTag by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CurriculumTag : {}", id);
        curriculumTagRepository.deleteById(id);
    }
}
