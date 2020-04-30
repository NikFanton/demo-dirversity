package com.dirversity.web.rest;

import com.dirversity.security.SecurityUtils;
import com.dirversity.service.EmailService;
import com.dirversity.service.MailService;
import com.dirversity.service.UserService;
import com.dirversity.service.dto.EmailDTO;
import com.dirversity.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * REST controller for managing {@link com.dirversity.domain.Email}.
 */
@RestController
@RequestMapping("/api")
public class EmailResource {

    private final Logger log = LoggerFactory.getLogger(EmailResource.class);

    private static final String ENTITY_NAME = "email";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmailService emailService;

    private final MailService mailService;

    private final UserService userService;

    public EmailResource(EmailService emailService, MailService mailService, UserService userService) {
        this.emailService = emailService;
        this.mailService = mailService;
        this.userService = userService;
    }

    /**
     * {@code POST  /emails} : Create a new email.
     *
     * @param emailDTO the emailDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emailDTO, or with status {@code 400 (Bad Request)} if the email has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/emails")
    public ResponseEntity<EmailDTO> createEmail(@RequestBody EmailDTO emailDTO) throws URISyntaxException {
        log.debug("REST request to save Email : {}", emailDTO);
        if (emailDTO.getId() != null) {
            throw new BadRequestAlertException("A new email cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmailDTO result = emailService.save(emailDTO);
        return ResponseEntity.created(new URI("/api/emails/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /emails} : Updates an existing email.
     *
     * @param emailDTO the emailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emailDTO,
     * or with status {@code 400 (Bad Request)} if the emailDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/emails")
    public ResponseEntity<EmailDTO> updateEmail(@RequestBody EmailDTO emailDTO) throws URISyntaxException {
        log.debug("REST request to update Email : {}", emailDTO);
        if (emailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmailDTO result = emailService.save(emailDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emailDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /emails} : get all the emails.
     *

     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emails in body.
     */
    @GetMapping("/emails")
    public ResponseEntity<List<EmailDTO>> getAllEmails(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Emails");
        Page<EmailDTO> page;
        if (eagerload) {
            page = emailService.findAllWithEagerRelationships(pageable);
        } else {
            page = emailService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    @PostMapping("/emails/{id}/send")
    public void sendResourceEmail(@PathVariable Long id) {
        Locale locale = Locale.ENGLISH;
        log.debug("REST request to get Email : {}", id);
        SecurityUtils.getCurrentUserLogin()
            .map(userService::findUserByLogin)
            .map(Optional::get)
            .ifPresent(user -> emailService.findOneEmail(id)
                .ifPresent(email -> mailService.sendResourceEmailToEachUser(email, user, locale))
            );
    }

    /**
     * {@code GET  /emails/:id} : get the "id" email.
     *
     * @param id the id of the emailDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emailDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/emails/{id}")
    public ResponseEntity<EmailDTO> getEmail(@PathVariable Long id) {
        log.debug("REST request to get Email : {}", id);
        Optional<EmailDTO> emailDTO = emailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emailDTO);
    }

    /**
     * {@code DELETE  /emails/:id} : delete the "id" email.
     *
     * @param id the id of the emailDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/emails/{id}")
    public ResponseEntity<Void> deleteEmail(@PathVariable Long id) {
        log.debug("REST request to delete Email : {}", id);
        emailService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
