package com.dirversity.web.rest;

import com.dirversity.DirversityApp;
import com.dirversity.domain.Curriculum;
import com.dirversity.repository.CurriculumRepository;
import com.dirversity.service.CurriculumService;
import com.dirversity.service.dto.CurriculumDTO;
import com.dirversity.service.mapper.CurriculumMapper;
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
 * Integration tests for the {@link CurriculumResource} REST controller.
 */
@SpringBootTest(classes = DirversityApp.class)
public class CurriculumResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_EXPLANATORY_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_EXPLANATORY_NOTE = "BBBBBBBBBB";

    @Autowired
    private CurriculumRepository curriculumRepository;

    @Mock
    private CurriculumRepository curriculumRepositoryMock;

    @Autowired
    private CurriculumMapper curriculumMapper;

    @Mock
    private CurriculumService curriculumServiceMock;

    @Autowired
    private CurriculumService curriculumService;

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

    private MockMvc restCurriculumMockMvc;

    private Curriculum curriculum;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CurriculumResource curriculumResource = new CurriculumResource(curriculumService);
        this.restCurriculumMockMvc = MockMvcBuilders.standaloneSetup(curriculumResource)
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
    public static Curriculum createEntity(EntityManager em) {
        Curriculum curriculum = new Curriculum()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .explanatoryNote(DEFAULT_EXPLANATORY_NOTE);
        return curriculum;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Curriculum createUpdatedEntity(EntityManager em) {
        Curriculum curriculum = new Curriculum()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .explanatoryNote(UPDATED_EXPLANATORY_NOTE);
        return curriculum;
    }

    @BeforeEach
    public void initTest() {
        curriculum = createEntity(em);
    }

    @Test
    @Transactional
    public void createCurriculum() throws Exception {
        int databaseSizeBeforeCreate = curriculumRepository.findAll().size();

        // Create the Curriculum
        CurriculumDTO curriculumDTO = curriculumMapper.toDto(curriculum);
        restCurriculumMockMvc.perform(post("/api/curricula")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(curriculumDTO)))
            .andExpect(status().isCreated());

        // Validate the Curriculum in the database
        List<Curriculum> curriculumList = curriculumRepository.findAll();
        assertThat(curriculumList).hasSize(databaseSizeBeforeCreate + 1);
        Curriculum testCurriculum = curriculumList.get(curriculumList.size() - 1);
        assertThat(testCurriculum.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCurriculum.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCurriculum.getExplanatoryNote()).isEqualTo(DEFAULT_EXPLANATORY_NOTE);
    }

    @Test
    @Transactional
    public void createCurriculumWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = curriculumRepository.findAll().size();

        // Create the Curriculum with an existing ID
        curriculum.setId(1L);
        CurriculumDTO curriculumDTO = curriculumMapper.toDto(curriculum);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCurriculumMockMvc.perform(post("/api/curricula")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(curriculumDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Curriculum in the database
        List<Curriculum> curriculumList = curriculumRepository.findAll();
        assertThat(curriculumList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = curriculumRepository.findAll().size();
        // set the field null
        curriculum.setName(null);

        // Create the Curriculum, which fails.
        CurriculumDTO curriculumDTO = curriculumMapper.toDto(curriculum);

        restCurriculumMockMvc.perform(post("/api/curricula")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(curriculumDTO)))
            .andExpect(status().isBadRequest());

        List<Curriculum> curriculumList = curriculumRepository.findAll();
        assertThat(curriculumList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCurricula() throws Exception {
        // Initialize the database
        curriculumRepository.saveAndFlush(curriculum);

        // Get all the curriculumList
        restCurriculumMockMvc.perform(get("/api/curricula?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(curriculum.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].explanatoryNote").value(hasItem(DEFAULT_EXPLANATORY_NOTE)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllCurriculaWithEagerRelationshipsIsEnabled() throws Exception {
        CurriculumResource curriculumResource = new CurriculumResource(curriculumServiceMock);
        when(curriculumServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restCurriculumMockMvc = MockMvcBuilders.standaloneSetup(curriculumResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restCurriculumMockMvc.perform(get("/api/curricula?eagerload=true"))
        .andExpect(status().isOk());

        verify(curriculumServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllCurriculaWithEagerRelationshipsIsNotEnabled() throws Exception {
        CurriculumResource curriculumResource = new CurriculumResource(curriculumServiceMock);
            when(curriculumServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restCurriculumMockMvc = MockMvcBuilders.standaloneSetup(curriculumResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restCurriculumMockMvc.perform(get("/api/curricula?eagerload=true"))
        .andExpect(status().isOk());

            verify(curriculumServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getCurriculum() throws Exception {
        // Initialize the database
        curriculumRepository.saveAndFlush(curriculum);

        // Get the curriculum
        restCurriculumMockMvc.perform(get("/api/curricula/{id}", curriculum.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(curriculum.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.explanatoryNote").value(DEFAULT_EXPLANATORY_NOTE));
    }

    @Test
    @Transactional
    public void getNonExistingCurriculum() throws Exception {
        // Get the curriculum
        restCurriculumMockMvc.perform(get("/api/curricula/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCurriculum() throws Exception {
        // Initialize the database
        curriculumRepository.saveAndFlush(curriculum);

        int databaseSizeBeforeUpdate = curriculumRepository.findAll().size();

        // Update the curriculum
        Curriculum updatedCurriculum = curriculumRepository.findById(curriculum.getId()).get();
        // Disconnect from session so that the updates on updatedCurriculum are not directly saved in db
        em.detach(updatedCurriculum);
        updatedCurriculum
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .explanatoryNote(UPDATED_EXPLANATORY_NOTE);
        CurriculumDTO curriculumDTO = curriculumMapper.toDto(updatedCurriculum);

        restCurriculumMockMvc.perform(put("/api/curricula")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(curriculumDTO)))
            .andExpect(status().isOk());

        // Validate the Curriculum in the database
        List<Curriculum> curriculumList = curriculumRepository.findAll();
        assertThat(curriculumList).hasSize(databaseSizeBeforeUpdate);
        Curriculum testCurriculum = curriculumList.get(curriculumList.size() - 1);
        assertThat(testCurriculum.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCurriculum.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCurriculum.getExplanatoryNote()).isEqualTo(UPDATED_EXPLANATORY_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingCurriculum() throws Exception {
        int databaseSizeBeforeUpdate = curriculumRepository.findAll().size();

        // Create the Curriculum
        CurriculumDTO curriculumDTO = curriculumMapper.toDto(curriculum);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurriculumMockMvc.perform(put("/api/curricula")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(curriculumDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Curriculum in the database
        List<Curriculum> curriculumList = curriculumRepository.findAll();
        assertThat(curriculumList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCurriculum() throws Exception {
        // Initialize the database
        curriculumRepository.saveAndFlush(curriculum);

        int databaseSizeBeforeDelete = curriculumRepository.findAll().size();

        // Delete the curriculum
        restCurriculumMockMvc.perform(delete("/api/curricula/{id}", curriculum.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Curriculum> curriculumList = curriculumRepository.findAll();
        assertThat(curriculumList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Curriculum.class);
        Curriculum curriculum1 = new Curriculum();
        curriculum1.setId(1L);
        Curriculum curriculum2 = new Curriculum();
        curriculum2.setId(curriculum1.getId());
        assertThat(curriculum1).isEqualTo(curriculum2);
        curriculum2.setId(2L);
        assertThat(curriculum1).isNotEqualTo(curriculum2);
        curriculum1.setId(null);
        assertThat(curriculum1).isNotEqualTo(curriculum2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurriculumDTO.class);
        CurriculumDTO curriculumDTO1 = new CurriculumDTO();
        curriculumDTO1.setId(1L);
        CurriculumDTO curriculumDTO2 = new CurriculumDTO();
        assertThat(curriculumDTO1).isNotEqualTo(curriculumDTO2);
        curriculumDTO2.setId(curriculumDTO1.getId());
        assertThat(curriculumDTO1).isEqualTo(curriculumDTO2);
        curriculumDTO2.setId(2L);
        assertThat(curriculumDTO1).isNotEqualTo(curriculumDTO2);
        curriculumDTO1.setId(null);
        assertThat(curriculumDTO1).isNotEqualTo(curriculumDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(curriculumMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(curriculumMapper.fromId(null)).isNull();
    }
}
