package com.dirversity.web.rest;

import com.dirversity.DirversityApp;
import com.dirversity.domain.UserGroupType;
import com.dirversity.repository.UserGroupTypeRepository;
import com.dirversity.service.UserGroupTypeService;
import com.dirversity.service.dto.UserGroupTypeDTO;
import com.dirversity.service.mapper.UserGroupTypeMapper;
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
 * Integration tests for the {@link UserGroupTypeResource} REST controller.
 */
@SpringBootTest(classes = DirversityApp.class)
public class UserGroupTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private UserGroupTypeRepository userGroupTypeRepository;

    @Autowired
    private UserGroupTypeMapper userGroupTypeMapper;

    @Autowired
    private UserGroupTypeService userGroupTypeService;

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

    private MockMvc restUserGroupTypeMockMvc;

    private UserGroupType userGroupType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserGroupTypeResource userGroupTypeResource = new UserGroupTypeResource(userGroupTypeService);
        this.restUserGroupTypeMockMvc = MockMvcBuilders.standaloneSetup(userGroupTypeResource)
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
    public static UserGroupType createEntity(EntityManager em) {
        UserGroupType userGroupType = new UserGroupType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return userGroupType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserGroupType createUpdatedEntity(EntityManager em) {
        UserGroupType userGroupType = new UserGroupType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return userGroupType;
    }

    @BeforeEach
    public void initTest() {
        userGroupType = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserGroupType() throws Exception {
        int databaseSizeBeforeCreate = userGroupTypeRepository.findAll().size();

        // Create the UserGroupType
        UserGroupTypeDTO userGroupTypeDTO = userGroupTypeMapper.toDto(userGroupType);
        restUserGroupTypeMockMvc.perform(post("/api/user-group-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userGroupTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the UserGroupType in the database
        List<UserGroupType> userGroupTypeList = userGroupTypeRepository.findAll();
        assertThat(userGroupTypeList).hasSize(databaseSizeBeforeCreate + 1);
        UserGroupType testUserGroupType = userGroupTypeList.get(userGroupTypeList.size() - 1);
        assertThat(testUserGroupType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUserGroupType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createUserGroupTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userGroupTypeRepository.findAll().size();

        // Create the UserGroupType with an existing ID
        userGroupType.setId(1L);
        UserGroupTypeDTO userGroupTypeDTO = userGroupTypeMapper.toDto(userGroupType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserGroupTypeMockMvc.perform(post("/api/user-group-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userGroupTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserGroupType in the database
        List<UserGroupType> userGroupTypeList = userGroupTypeRepository.findAll();
        assertThat(userGroupTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userGroupTypeRepository.findAll().size();
        // set the field null
        userGroupType.setName(null);

        // Create the UserGroupType, which fails.
        UserGroupTypeDTO userGroupTypeDTO = userGroupTypeMapper.toDto(userGroupType);

        restUserGroupTypeMockMvc.perform(post("/api/user-group-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userGroupTypeDTO)))
            .andExpect(status().isBadRequest());

        List<UserGroupType> userGroupTypeList = userGroupTypeRepository.findAll();
        assertThat(userGroupTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserGroupTypes() throws Exception {
        // Initialize the database
        userGroupTypeRepository.saveAndFlush(userGroupType);

        // Get all the userGroupTypeList
        restUserGroupTypeMockMvc.perform(get("/api/user-group-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userGroupType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getUserGroupType() throws Exception {
        // Initialize the database
        userGroupTypeRepository.saveAndFlush(userGroupType);

        // Get the userGroupType
        restUserGroupTypeMockMvc.perform(get("/api/user-group-types/{id}", userGroupType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userGroupType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingUserGroupType() throws Exception {
        // Get the userGroupType
        restUserGroupTypeMockMvc.perform(get("/api/user-group-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserGroupType() throws Exception {
        // Initialize the database
        userGroupTypeRepository.saveAndFlush(userGroupType);

        int databaseSizeBeforeUpdate = userGroupTypeRepository.findAll().size();

        // Update the userGroupType
        UserGroupType updatedUserGroupType = userGroupTypeRepository.findById(userGroupType.getId()).get();
        // Disconnect from session so that the updates on updatedUserGroupType are not directly saved in db
        em.detach(updatedUserGroupType);
        updatedUserGroupType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        UserGroupTypeDTO userGroupTypeDTO = userGroupTypeMapper.toDto(updatedUserGroupType);

        restUserGroupTypeMockMvc.perform(put("/api/user-group-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userGroupTypeDTO)))
            .andExpect(status().isOk());

        // Validate the UserGroupType in the database
        List<UserGroupType> userGroupTypeList = userGroupTypeRepository.findAll();
        assertThat(userGroupTypeList).hasSize(databaseSizeBeforeUpdate);
        UserGroupType testUserGroupType = userGroupTypeList.get(userGroupTypeList.size() - 1);
        assertThat(testUserGroupType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUserGroupType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingUserGroupType() throws Exception {
        int databaseSizeBeforeUpdate = userGroupTypeRepository.findAll().size();

        // Create the UserGroupType
        UserGroupTypeDTO userGroupTypeDTO = userGroupTypeMapper.toDto(userGroupType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserGroupTypeMockMvc.perform(put("/api/user-group-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userGroupTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserGroupType in the database
        List<UserGroupType> userGroupTypeList = userGroupTypeRepository.findAll();
        assertThat(userGroupTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserGroupType() throws Exception {
        // Initialize the database
        userGroupTypeRepository.saveAndFlush(userGroupType);

        int databaseSizeBeforeDelete = userGroupTypeRepository.findAll().size();

        // Delete the userGroupType
        restUserGroupTypeMockMvc.perform(delete("/api/user-group-types/{id}", userGroupType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserGroupType> userGroupTypeList = userGroupTypeRepository.findAll();
        assertThat(userGroupTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserGroupType.class);
        UserGroupType userGroupType1 = new UserGroupType();
        userGroupType1.setId(1L);
        UserGroupType userGroupType2 = new UserGroupType();
        userGroupType2.setId(userGroupType1.getId());
        assertThat(userGroupType1).isEqualTo(userGroupType2);
        userGroupType2.setId(2L);
        assertThat(userGroupType1).isNotEqualTo(userGroupType2);
        userGroupType1.setId(null);
        assertThat(userGroupType1).isNotEqualTo(userGroupType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserGroupTypeDTO.class);
        UserGroupTypeDTO userGroupTypeDTO1 = new UserGroupTypeDTO();
        userGroupTypeDTO1.setId(1L);
        UserGroupTypeDTO userGroupTypeDTO2 = new UserGroupTypeDTO();
        assertThat(userGroupTypeDTO1).isNotEqualTo(userGroupTypeDTO2);
        userGroupTypeDTO2.setId(userGroupTypeDTO1.getId());
        assertThat(userGroupTypeDTO1).isEqualTo(userGroupTypeDTO2);
        userGroupTypeDTO2.setId(2L);
        assertThat(userGroupTypeDTO1).isNotEqualTo(userGroupTypeDTO2);
        userGroupTypeDTO1.setId(null);
        assertThat(userGroupTypeDTO1).isNotEqualTo(userGroupTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userGroupTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userGroupTypeMapper.fromId(null)).isNull();
    }
}
