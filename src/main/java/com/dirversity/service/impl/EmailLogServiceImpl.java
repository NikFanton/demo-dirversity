package com.dirversity.service.impl;

import com.dirversity.service.EmailLogService;
import com.dirversity.domain.EmailLog;
import com.dirversity.repository.EmailLogRepository;
import com.dirversity.service.dto.EmailLogDTO;
import com.dirversity.service.mapper.EmailLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EmailLog}.
 */
@Service
@Transactional
public class EmailLogServiceImpl implements EmailLogService {

    private final Logger log = LoggerFactory.getLogger(EmailLogServiceImpl.class);

    private final EmailLogRepository emailLogRepository;

    private final EmailLogMapper emailLogMapper;

    public EmailLogServiceImpl(EmailLogRepository emailLogRepository, EmailLogMapper emailLogMapper) {
        this.emailLogRepository = emailLogRepository;
        this.emailLogMapper = emailLogMapper;
    }

    /**
     * Save a emailLog.
     *
     * @param emailLogDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EmailLogDTO save(EmailLogDTO emailLogDTO) {
        log.debug("Request to save EmailLog : {}", emailLogDTO);
        EmailLog emailLog = emailLogMapper.toEntity(emailLogDTO);
        emailLog = emailLogRepository.save(emailLog);
        return emailLogMapper.toDto(emailLog);
    }

    /**
     * Get all the emailLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EmailLogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmailLogs");
        return emailLogRepository.findAll(pageable)
            .map(emailLogMapper::toDto);
    }

    /**
     * Get all the emailLogs with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<EmailLogDTO> findAllWithEagerRelationships(Pageable pageable) {
        return emailLogRepository.findAllWithEagerRelationships(pageable).map(emailLogMapper::toDto);
    }
    

    /**
     * Get one emailLog by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EmailLogDTO> findOne(Long id) {
        log.debug("Request to get EmailLog : {}", id);
        return emailLogRepository.findOneWithEagerRelationships(id)
            .map(emailLogMapper::toDto);
    }

    /**
     * Delete the emailLog by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmailLog : {}", id);
        emailLogRepository.deleteById(id);
    }
}
