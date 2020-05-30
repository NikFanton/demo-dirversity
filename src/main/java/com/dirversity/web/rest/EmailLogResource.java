package com.dirversity.web.rest;

import com.dirversity.service.EmailLogService;
import com.dirversity.web.rest.errors.BadRequestAlertException;
import com.dirversity.service.dto.EmailLogDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.dirversity.domain.EmailLog}.
 */
@RestController
@RequestMapping("/api")
public class EmailLogResource {

    private final Logger log = LoggerFactory.getLogger(EmailLogResource.class);

    private final EmailLogService emailLogService;

    public EmailLogResource(EmailLogService emailLogService) {
        this.emailLogService = emailLogService;
    }

    /**
     * {@code GET  /email-logs} : get all the emailLogs.
     *

     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emailLogs in body.
     */
    @GetMapping("/email-logs")
    public ResponseEntity<List<EmailLogDTO>> getAllEmailLogs(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of EmailLogs");
        Page<EmailLogDTO> page;
        if (eagerload) {
            page = emailLogService.findAllWithEagerRelationships(pageable);
        } else {
            page = emailLogService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /email-logs/:id} : get the "id" emailLog.
     *
     * @param id the id of the emailLogDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emailLogDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/email-logs/{id}")
    public ResponseEntity<EmailLogDTO> getEmailLog(@PathVariable Long id) {
        log.debug("REST request to get EmailLog : {}", id);
        Optional<EmailLogDTO> emailLogDTO = emailLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emailLogDTO);
    }
}
