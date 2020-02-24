package com.dirversity.web.rest;

import com.dirversity.DirversityApp;
import com.dirversity.domain.ContentModule;
import com.dirversity.repository.ContentModuleRepository;
import com.dirversity.service.ContentModuleService;
import com.dirversity.service.dto.ContentModuleDTO;
import com.dirversity.service.mapper.ContentModuleMapper;
import com.dirversity.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.dirversity.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ContentModuleResource} REST controller.
 */
@SpringBootTest(classes = DirversityApp.class)
public class ContentModuleResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ContentModuleRepository contentModuleRepository;

    @Autowired
    private ContentModuleMapper contentModuleMapper;

    @Autowired
    private ContentModuleService contentModuleService;

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

    private MockMvc restContentModuleMockMvc;

    private ContentModule contentModule;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContentModuleResource contentModuleResource = new ContentModuleResource(contentModuleService);
        this.restContentModuleMockMvc = MockMvcBuilders.standaloneSetup(contentModuleResource)
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
    public static ContentModule createEntity(EntityManager em) {
        ContentModule contentModule = new ContentModule()
            .name(DEFAULT_NAME)
            .order(DEFAULT_ORDER)
            .description(DEFAULT_DESCRIPTION);
        return contentModule;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContentModule createUpdatedEntity(EntityManager em) {
        ContentModule contentModule = new ContentModule()
            .name(UPDATED_NAME)
            .order(UPDATED_ORDER)
            .description(UPDATED_DESCRIPTION);
        return contentModule;
    }

    @BeforeEach
    public void initTest() {
        contentModule = createEntity(em);
    }

    @Test
    @Transactional
    public void createContentModule() throws Exception {
        int databaseSizeBeforeCreate = contentModuleRepository.findAll().size();

        // Create the ContentModule
        ContentModuleDTO contentModuleDTO = contentModuleMapper.toDto(contentModule);
        restContentModuleMockMvc.perform(post("/api/content-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentModuleDTO)))
            .andExpect(status().isCreated());

        // Validate the ContentModule in the database
        List<ContentModule> contentModuleList = contentModuleRepository.findAll();
        assertThat(contentModuleList).hasSize(databaseSizeBeforeCreate + 1);
        ContentModule testContentModule = contentModuleList.get(contentModuleList.size() - 1);
        assertThat(testContentModule.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testContentModule.getOrder()).isEqualTo(DEFAULT_ORDER);
        assertThat(testContentModule.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createContentModuleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contentModuleRepository.findAll().size();

        // Create the ContentModule with an existing ID
        contentModule.setId(1L);
        ContentModuleDTO contentModuleDTO = contentModuleMapper.toDto(contentModule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContentModuleMockMvc.perform(post("/api/content-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentModuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContentModule in the database
        List<ContentModule> contentModuleList = contentModuleRepository.findAll();
        assertThat(contentModuleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentModuleRepository.findAll().size();
        // set the field null
        contentModule.setName(null);

        // Create the ContentModule, which fails.
        ContentModuleDTO contentModuleDTO = contentModuleMapper.toDto(contentModule);

        restContentModuleMockMvc.perform(post("/api/content-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentModuleDTO)))
            .andExpect(status().isBadRequest());

        List<ContentModule> contentModuleList = contentModuleRepository.findAll();
        assertThat(contentModuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkOrderIsRequired() throws Exception {
        int databaseSizeBeforeTest = contentModuleRepository.findAll().size();
        // set the field null
        contentModule.setOrder(null);

        // Create the ContentModule, which fails.
        ContentModuleDTO contentModuleDTO = contentModuleMapper.toDto(contentModule);

        restContentModuleMockMvc.perform(post("/api/content-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentModuleDTO)))
            .andExpect(status().isBadRequest());

        List<ContentModule> contentModuleList = contentModuleRepository.findAll();
        assertThat(contentModuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContentModules() throws Exception {
        // Initialize the database
        contentModuleRepository.saveAndFlush(contentModule);

        // Get all the contentModuleList
        restContentModuleMockMvc.perform(get("/api/content-modules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contentModule.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getContentModule() throws Exception {
        // Initialize the database
        contentModuleRepository.saveAndFlush(contentModule);

        // Get the contentModule
        restContentModuleMockMvc.perform(get("/api/content-modules/{id}", contentModule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contentModule.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingContentModule() throws Exception {
        // Get the contentModule
        restContentModuleMockMvc.perform(get("/api/content-modules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContentModule() throws Exception {
        // Initialize the database
        contentModuleRepository.saveAndFlush(contentModule);

        int databaseSizeBeforeUpdate = contentModuleRepository.findAll().size();

        // Update the contentModule
        ContentModule updatedContentModule = contentModuleRepository.findById(contentModule.getId()).get();
        // Disconnect from session so that the updates on updatedContentModule are not directly saved in db
        em.detach(updatedContentModule);
        updatedContentModule
            .name(UPDATED_NAME)
            .order(UPDATED_ORDER)
            .description(UPDATED_DESCRIPTION);
        ContentModuleDTO contentModuleDTO = contentModuleMapper.toDto(updatedContentModule);

        restContentModuleMockMvc.perform(put("/api/content-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentModuleDTO)))
            .andExpect(status().isOk());

        // Validate the ContentModule in the database
        List<ContentModule> contentModuleList = contentModuleRepository.findAll();
        assertThat(contentModuleList).hasSize(databaseSizeBeforeUpdate);
        ContentModule testContentModule = contentModuleList.get(contentModuleList.size() - 1);
        assertThat(testContentModule.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContentModule.getOrder()).isEqualTo(UPDATED_ORDER);
        assertThat(testContentModule.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingContentModule() throws Exception {
        int databaseSizeBeforeUpdate = contentModuleRepository.findAll().size();

        // Create the ContentModule
        ContentModuleDTO contentModuleDTO = contentModuleMapper.toDto(contentModule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContentModuleMockMvc.perform(put("/api/content-modules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentModuleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ContentModule in the database
        List<ContentModule> contentModuleList = contentModuleRepository.findAll();
        assertThat(contentModuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContentModule() throws Exception {
        // Initialize the database
        contentModuleRepository.saveAndFlush(contentModule);

        int databaseSizeBeforeDelete = contentModuleRepository.findAll().size();

        // Delete the contentModule
        restContentModuleMockMvc.perform(delete("/api/content-modules/{id}", contentModule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContentModule> contentModuleList = contentModuleRepository.findAll();
        assertThat(contentModuleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContentModule.class);
        ContentModule contentModule1 = new ContentModule();
        contentModule1.setId(1L);
        ContentModule contentModule2 = new ContentModule();
        contentModule2.setId(contentModule1.getId());
        assertThat(contentModule1).isEqualTo(contentModule2);
        contentModule2.setId(2L);
        assertThat(contentModule1).isNotEqualTo(contentModule2);
        contentModule1.setId(null);
        assertThat(contentModule1).isNotEqualTo(contentModule2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContentModuleDTO.class);
        ContentModuleDTO contentModuleDTO1 = new ContentModuleDTO();
        contentModuleDTO1.setId(1L);
        ContentModuleDTO contentModuleDTO2 = new ContentModuleDTO();
        assertThat(contentModuleDTO1).isNotEqualTo(contentModuleDTO2);
        contentModuleDTO2.setId(contentModuleDTO1.getId());
        assertThat(contentModuleDTO1).isEqualTo(contentModuleDTO2);
        contentModuleDTO2.setId(2L);
        assertThat(contentModuleDTO1).isNotEqualTo(contentModuleDTO2);
        contentModuleDTO1.setId(null);
        assertThat(contentModuleDTO1).isNotEqualTo(contentModuleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(contentModuleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(contentModuleMapper.fromId(null)).isNull();
    }
}
