package com.dirversity.web.rest;

import com.dirversity.DirversityApp;
import com.dirversity.domain.CurriculumTag;
import com.dirversity.repository.CurriculumTagRepository;
import com.dirversity.service.CurriculumTagService;
import com.dirversity.service.dto.CurriculumTagDTO;
import com.dirversity.service.mapper.CurriculumTagMapper;
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
 * Integration tests for the {@link CurriculumTagResource} REST controller.
 */
@SpringBootTest(classes = DirversityApp.class)
public class CurriculumTagResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CurriculumTagRepository curriculumTagRepository;

    @Autowired
    private CurriculumTagMapper curriculumTagMapper;

    @Autowired
    private CurriculumTagService curriculumTagService;

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

    private MockMvc restCurriculumTagMockMvc;

    private CurriculumTag curriculumTag;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CurriculumTagResource curriculumTagResource = new CurriculumTagResource(curriculumTagService);
        this.restCurriculumTagMockMvc = MockMvcBuilders.standaloneSetup(curriculumTagResource)
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
    public static CurriculumTag createEntity(EntityManager em) {
        CurriculumTag curriculumTag = new CurriculumTag()
            .name(DEFAULT_NAME);
        return curriculumTag;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CurriculumTag createUpdatedEntity(EntityManager em) {
        CurriculumTag curriculumTag = new CurriculumTag()
            .name(UPDATED_NAME);
        return curriculumTag;
    }

    @BeforeEach
    public void initTest() {
        curriculumTag = createEntity(em);
    }

    @Test
    @Transactional
    public void createCurriculumTag() throws Exception {
        int databaseSizeBeforeCreate = curriculumTagRepository.findAll().size();

        // Create the CurriculumTag
        CurriculumTagDTO curriculumTagDTO = curriculumTagMapper.toDto(curriculumTag);
        restCurriculumTagMockMvc.perform(post("/api/curriculum-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(curriculumTagDTO)))
            .andExpect(status().isCreated());

        // Validate the CurriculumTag in the database
        List<CurriculumTag> curriculumTagList = curriculumTagRepository.findAll();
        assertThat(curriculumTagList).hasSize(databaseSizeBeforeCreate + 1);
        CurriculumTag testCurriculumTag = curriculumTagList.get(curriculumTagList.size() - 1);
        assertThat(testCurriculumTag.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createCurriculumTagWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = curriculumTagRepository.findAll().size();

        // Create the CurriculumTag with an existing ID
        curriculumTag.setId(1L);
        CurriculumTagDTO curriculumTagDTO = curriculumTagMapper.toDto(curriculumTag);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCurriculumTagMockMvc.perform(post("/api/curriculum-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(curriculumTagDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CurriculumTag in the database
        List<CurriculumTag> curriculumTagList = curriculumTagRepository.findAll();
        assertThat(curriculumTagList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = curriculumTagRepository.findAll().size();
        // set the field null
        curriculumTag.setName(null);

        // Create the CurriculumTag, which fails.
        CurriculumTagDTO curriculumTagDTO = curriculumTagMapper.toDto(curriculumTag);

        restCurriculumTagMockMvc.perform(post("/api/curriculum-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(curriculumTagDTO)))
            .andExpect(status().isBadRequest());

        List<CurriculumTag> curriculumTagList = curriculumTagRepository.findAll();
        assertThat(curriculumTagList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCurriculumTags() throws Exception {
        // Initialize the database
        curriculumTagRepository.saveAndFlush(curriculumTag);

        // Get all the curriculumTagList
        restCurriculumTagMockMvc.perform(get("/api/curriculum-tags?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(curriculumTag.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getCurriculumTag() throws Exception {
        // Initialize the database
        curriculumTagRepository.saveAndFlush(curriculumTag);

        // Get the curriculumTag
        restCurriculumTagMockMvc.perform(get("/api/curriculum-tags/{id}", curriculumTag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(curriculumTag.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingCurriculumTag() throws Exception {
        // Get the curriculumTag
        restCurriculumTagMockMvc.perform(get("/api/curriculum-tags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCurriculumTag() throws Exception {
        // Initialize the database
        curriculumTagRepository.saveAndFlush(curriculumTag);

        int databaseSizeBeforeUpdate = curriculumTagRepository.findAll().size();

        // Update the curriculumTag
        CurriculumTag updatedCurriculumTag = curriculumTagRepository.findById(curriculumTag.getId()).get();
        // Disconnect from session so that the updates on updatedCurriculumTag are not directly saved in db
        em.detach(updatedCurriculumTag);
        updatedCurriculumTag
            .name(UPDATED_NAME);
        CurriculumTagDTO curriculumTagDTO = curriculumTagMapper.toDto(updatedCurriculumTag);

        restCurriculumTagMockMvc.perform(put("/api/curriculum-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(curriculumTagDTO)))
            .andExpect(status().isOk());

        // Validate the CurriculumTag in the database
        List<CurriculumTag> curriculumTagList = curriculumTagRepository.findAll();
        assertThat(curriculumTagList).hasSize(databaseSizeBeforeUpdate);
        CurriculumTag testCurriculumTag = curriculumTagList.get(curriculumTagList.size() - 1);
        assertThat(testCurriculumTag.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCurriculumTag() throws Exception {
        int databaseSizeBeforeUpdate = curriculumTagRepository.findAll().size();

        // Create the CurriculumTag
        CurriculumTagDTO curriculumTagDTO = curriculumTagMapper.toDto(curriculumTag);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCurriculumTagMockMvc.perform(put("/api/curriculum-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(curriculumTagDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CurriculumTag in the database
        List<CurriculumTag> curriculumTagList = curriculumTagRepository.findAll();
        assertThat(curriculumTagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCurriculumTag() throws Exception {
        // Initialize the database
        curriculumTagRepository.saveAndFlush(curriculumTag);

        int databaseSizeBeforeDelete = curriculumTagRepository.findAll().size();

        // Delete the curriculumTag
        restCurriculumTagMockMvc.perform(delete("/api/curriculum-tags/{id}", curriculumTag.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CurriculumTag> curriculumTagList = curriculumTagRepository.findAll();
        assertThat(curriculumTagList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurriculumTag.class);
        CurriculumTag curriculumTag1 = new CurriculumTag();
        curriculumTag1.setId(1L);
        CurriculumTag curriculumTag2 = new CurriculumTag();
        curriculumTag2.setId(curriculumTag1.getId());
        assertThat(curriculumTag1).isEqualTo(curriculumTag2);
        curriculumTag2.setId(2L);
        assertThat(curriculumTag1).isNotEqualTo(curriculumTag2);
        curriculumTag1.setId(null);
        assertThat(curriculumTag1).isNotEqualTo(curriculumTag2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CurriculumTagDTO.class);
        CurriculumTagDTO curriculumTagDTO1 = new CurriculumTagDTO();
        curriculumTagDTO1.setId(1L);
        CurriculumTagDTO curriculumTagDTO2 = new CurriculumTagDTO();
        assertThat(curriculumTagDTO1).isNotEqualTo(curriculumTagDTO2);
        curriculumTagDTO2.setId(curriculumTagDTO1.getId());
        assertThat(curriculumTagDTO1).isEqualTo(curriculumTagDTO2);
        curriculumTagDTO2.setId(2L);
        assertThat(curriculumTagDTO1).isNotEqualTo(curriculumTagDTO2);
        curriculumTagDTO1.setId(null);
        assertThat(curriculumTagDTO1).isNotEqualTo(curriculumTagDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(curriculumTagMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(curriculumTagMapper.fromId(null)).isNull();
    }
}
