package com.dirversity.service.impl;

import com.dirversity.service.EmailService;
import com.dirversity.domain.Email;
import com.dirversity.repository.EmailRepository;
import com.dirversity.service.dto.EmailDTO;
import com.dirversity.service.mapper.EmailMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Email}.
 */
@Service
@Transactional
public class EmailServiceImpl implements EmailService {

    private final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final EmailRepository emailRepository;

    private final EmailMapper emailMapper;

    public EmailServiceImpl(EmailRepository emailRepository, EmailMapper emailMapper) {
        this.emailRepository = emailRepository;
        this.emailMapper = emailMapper;
    }

    /**
     * Save a email.
     *
     * @param emailDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public EmailDTO save(EmailDTO emailDTO) {
        log.debug("Request to save Email : {}", emailDTO);
        Email email = emailMapper.toEntity(emailDTO);
        email = emailRepository.save(email);
        return emailMapper.toDto(email);
    }

    /**
     * Get all the emails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EmailDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Emails");
        return emailRepository.findAll(pageable)
            .map(emailMapper::toDto);
    }

    /**
     * Get all the emails with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<EmailDTO> findAllWithEagerRelationships(Pageable pageable) {
        return emailRepository.findAllWithEagerRelationships(pageable).map(emailMapper::toDto);
    }


    /**
     * Get one email by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EmailDTO> findOne(Long id) {
        log.debug("Request to get Email : {}", id);
        return emailRepository.findOneWithEagerRelationships(id)
            .map(emailMapper::toDto);
    }

    /**
     * Get one email by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Email> findOneEmail(Long id) {
        log.debug("Request to get Email : {}", id);
        return emailRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the email by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Email : {}", id);
        emailRepository.deleteById(id);
    }
}
