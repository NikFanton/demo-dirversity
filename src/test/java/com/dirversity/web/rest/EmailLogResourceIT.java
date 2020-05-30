package com.dirversity.web.rest;

import com.dirversity.DirversityApp;
import com.dirversity.domain.EmailLog;
import com.dirversity.domain.Email;
import com.dirversity.repository.EmailLogRepository;
import com.dirversity.service.EmailLogService;
import com.dirversity.service.dto.EmailLogDTO;
import com.dirversity.service.mapper.EmailLogMapper;
import com.dirversity.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.dirversity.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link EmailLogResource} REST controller.
 */
@SpringBootTest(classes = DirversityApp.class)
public class EmailLogResourceIT {

    private static final String DEFAULT_LOG_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_LOG_MESSAGE = "BBBBBBBBBB";

    @Autowired
    private EmailLogRepository emailLogRepository;

    @Mock
    private EmailLogRepository emailLogRepositoryMock;

    @Autowired
    private EmailLogMapper emailLogMapper;

    @Mock
    private EmailLogService emailLogServiceMock;

    @Autowired
    private EmailLogService emailLogService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restEmailLogMockMvc;

    private EmailLog emailLog;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmailLogResource emailLogResource = new EmailLogResource(emailLogService);
        this.restEmailLogMockMvc = MockMvcBuilders.standaloneSetup(emailLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmailLog createEntity(EntityManager em) {
        EmailLog emailLog = new EmailLog()
            .logMessage(DEFAULT_LOG_MESSAGE);
        // Add required entity
        Email email;
        if (TestUtil.findAll(em, Email.class).isEmpty()) {
            email = EmailResourceIT.createEntity(em);
            em.persist(email);
            em.flush();
        } else {
            email = TestUtil.findAll(em, Email.class).get(0);
        }
        emailLog.setEmail(email);
        return emailLog;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmailLog createUpdatedEntity(EntityManager em) {
        EmailLog emailLog = new EmailLog()
            .logMessage(UPDATED_LOG_MESSAGE);
        // Add required entity
        Email email;
        if (TestUtil.findAll(em, Email.class).isEmpty()) {
            email = EmailResourceIT.createUpdatedEntity(em);
            em.persist(email);
            em.flush();
        } else {
            email = TestUtil.findAll(em, Email.class).get(0);
        }
        emailLog.setEmail(email);
        return emailLog;
    }

    @BeforeEach
    public void initTest() {
        emailLog = createEntity(em);
    }

    @Test
    @Transactional
    public void getAllEmailLogs() throws Exception {
        // Initialize the database
        emailLogRepository.saveAndFlush(emailLog);

        // Get all the emailLogList
        restEmailLogMockMvc.perform(get("/api/email-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emailLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].logMessage").value(hasItem(DEFAULT_LOG_MESSAGE)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllEmailLogsWithEagerRelationshipsIsEnabled() throws Exception {
        EmailLogResource emailLogResource = new EmailLogResource(emailLogServiceMock);
        when(emailLogServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restEmailLogMockMvc = MockMvcBuilders.standaloneSetup(emailLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restEmailLogMockMvc.perform(get("/api/email-logs?eagerload=true"))
        .andExpect(status().isOk());

        verify(emailLogServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllEmailLogsWithEagerRelationshipsIsNotEnabled() throws Exception {
        EmailLogResource emailLogResource = new EmailLogResource(emailLogServiceMock);
            when(emailLogServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restEmailLogMockMvc = MockMvcBuilders.standaloneSetup(emailLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restEmailLogMockMvc.perform(get("/api/email-logs?eagerload=true"))
        .andExpect(status().isOk());

            verify(emailLogServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getEmailLog() throws Exception {
        // Initialize the database
        emailLogRepository.saveAndFlush(emailLog);

        // Get the emailLog
        restEmailLogMockMvc.perform(get("/api/email-logs/{id}", emailLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(emailLog.getId().intValue()))
            .andExpect(jsonPath("$.logMessage").value(DEFAULT_LOG_MESSAGE));
    }

    @Test
    @Transactional
    public void getNonExistingEmailLog() throws Exception {
        // Get the emailLog
        restEmailLogMockMvc.perform(get("/api/email-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailLog.class);
        EmailLog emailLog1 = new EmailLog();
        emailLog1.setId(1L);
        EmailLog emailLog2 = new EmailLog();
        emailLog2.setId(emailLog1.getId());
        assertThat(emailLog1).isEqualTo(emailLog2);
        emailLog2.setId(2L);
        assertThat(emailLog1).isNotEqualTo(emailLog2);
        emailLog1.setId(null);
        assertThat(emailLog1).isNotEqualTo(emailLog2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmailLogDTO.class);
        EmailLogDTO emailLogDTO1 = new EmailLogDTO();
        emailLogDTO1.setId(1L);
        EmailLogDTO emailLogDTO2 = new EmailLogDTO();
        assertThat(emailLogDTO1).isNotEqualTo(emailLogDTO2);
        emailLogDTO2.setId(emailLogDTO1.getId());
        assertThat(emailLogDTO1).isEqualTo(emailLogDTO2);
        emailLogDTO2.setId(2L);
        assertThat(emailLogDTO1).isNotEqualTo(emailLogDTO2);
        emailLogDTO1.setId(null);
        assertThat(emailLogDTO1).isNotEqualTo(emailLogDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(emailLogMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(emailLogMapper.fromId(null)).isNull();
    }
}
