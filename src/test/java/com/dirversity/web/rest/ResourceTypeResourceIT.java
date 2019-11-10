package com.dirversity.web.rest;

import com.dirversity.DirversityApp;
import com.dirversity.domain.ResourceType;
import com.dirversity.repository.ResourceTypeRepository;
import com.dirversity.service.ResourceTypeService;
import com.dirversity.service.dto.ResourceTypeDTO;
import com.dirversity.service.mapper.ResourceTypeMapper;
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
 * Integration tests for the {@link ResourceTypeResource} REST controller.
 */
@SpringBootTest(classes = DirversityApp.class)
public class ResourceTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ResourceTypeRepository resourceTypeRepository;

    @Autowired
    private ResourceTypeMapper resourceTypeMapper;

    @Autowired
    private ResourceTypeService resourceTypeService;

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

    private MockMvc restResourceTypeMockMvc;

    private ResourceType resourceType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResourceTypeResource resourceTypeResource = new ResourceTypeResource(resourceTypeService);
        this.restResourceTypeMockMvc = MockMvcBuilders.standaloneSetup(resourceTypeResource)
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
    public static ResourceType createEntity(EntityManager em) {
        ResourceType resourceType = new ResourceType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return resourceType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResourceType createUpdatedEntity(EntityManager em) {
        ResourceType resourceType = new ResourceType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return resourceType;
    }

    @BeforeEach
    public void initTest() {
        resourceType = createEntity(em);
    }

    @Test
    @Transactional
    public void createResourceType() throws Exception {
        int databaseSizeBeforeCreate = resourceTypeRepository.findAll().size();

        // Create the ResourceType
        ResourceTypeDTO resourceTypeDTO = resourceTypeMapper.toDto(resourceType);
        restResourceTypeMockMvc.perform(post("/api/resource-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the ResourceType in the database
        List<ResourceType> resourceTypeList = resourceTypeRepository.findAll();
        assertThat(resourceTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ResourceType testResourceType = resourceTypeList.get(resourceTypeList.size() - 1);
        assertThat(testResourceType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testResourceType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createResourceTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resourceTypeRepository.findAll().size();

        // Create the ResourceType with an existing ID
        resourceType.setId(1L);
        ResourceTypeDTO resourceTypeDTO = resourceTypeMapper.toDto(resourceType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResourceTypeMockMvc.perform(post("/api/resource-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ResourceType in the database
        List<ResourceType> resourceTypeList = resourceTypeRepository.findAll();
        assertThat(resourceTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceTypeRepository.findAll().size();
        // set the field null
        resourceType.setName(null);

        // Create the ResourceType, which fails.
        ResourceTypeDTO resourceTypeDTO = resourceTypeMapper.toDto(resourceType);

        restResourceTypeMockMvc.perform(post("/api/resource-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceTypeDTO)))
            .andExpect(status().isBadRequest());

        List<ResourceType> resourceTypeList = resourceTypeRepository.findAll();
        assertThat(resourceTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResourceTypes() throws Exception {
        // Initialize the database
        resourceTypeRepository.saveAndFlush(resourceType);

        // Get all the resourceTypeList
        restResourceTypeMockMvc.perform(get("/api/resource-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resourceType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getResourceType() throws Exception {
        // Initialize the database
        resourceTypeRepository.saveAndFlush(resourceType);

        // Get the resourceType
        restResourceTypeMockMvc.perform(get("/api/resource-types/{id}", resourceType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resourceType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingResourceType() throws Exception {
        // Get the resourceType
        restResourceTypeMockMvc.perform(get("/api/resource-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResourceType() throws Exception {
        // Initialize the database
        resourceTypeRepository.saveAndFlush(resourceType);

        int databaseSizeBeforeUpdate = resourceTypeRepository.findAll().size();

        // Update the resourceType
        ResourceType updatedResourceType = resourceTypeRepository.findById(resourceType.getId()).get();
        // Disconnect from session so that the updates on updatedResourceType are not directly saved in db
        em.detach(updatedResourceType);
        updatedResourceType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        ResourceTypeDTO resourceTypeDTO = resourceTypeMapper.toDto(updatedResourceType);

        restResourceTypeMockMvc.perform(put("/api/resource-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceTypeDTO)))
            .andExpect(status().isOk());

        // Validate the ResourceType in the database
        List<ResourceType> resourceTypeList = resourceTypeRepository.findAll();
        assertThat(resourceTypeList).hasSize(databaseSizeBeforeUpdate);
        ResourceType testResourceType = resourceTypeList.get(resourceTypeList.size() - 1);
        assertThat(testResourceType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testResourceType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingResourceType() throws Exception {
        int databaseSizeBeforeUpdate = resourceTypeRepository.findAll().size();

        // Create the ResourceType
        ResourceTypeDTO resourceTypeDTO = resourceTypeMapper.toDto(resourceType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResourceTypeMockMvc.perform(put("/api/resource-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resourceTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ResourceType in the database
        List<ResourceType> resourceTypeList = resourceTypeRepository.findAll();
        assertThat(resourceTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResourceType() throws Exception {
        // Initialize the database
        resourceTypeRepository.saveAndFlush(resourceType);

        int databaseSizeBeforeDelete = resourceTypeRepository.findAll().size();

        // Delete the resourceType
        restResourceTypeMockMvc.perform(delete("/api/resource-types/{id}", resourceType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResourceType> resourceTypeList = resourceTypeRepository.findAll();
        assertThat(resourceTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResourceType.class);
        ResourceType resourceType1 = new ResourceType();
        resourceType1.setId(1L);
        ResourceType resourceType2 = new ResourceType();
        resourceType2.setId(resourceType1.getId());
        assertThat(resourceType1).isEqualTo(resourceType2);
        resourceType2.setId(2L);
        assertThat(resourceType1).isNotEqualTo(resourceType2);
        resourceType1.setId(null);
        assertThat(resourceType1).isNotEqualTo(resourceType2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResourceTypeDTO.class);
        ResourceTypeDTO resourceTypeDTO1 = new ResourceTypeDTO();
        resourceTypeDTO1.setId(1L);
        ResourceTypeDTO resourceTypeDTO2 = new ResourceTypeDTO();
        assertThat(resourceTypeDTO1).isNotEqualTo(resourceTypeDTO2);
        resourceTypeDTO2.setId(resourceTypeDTO1.getId());
        assertThat(resourceTypeDTO1).isEqualTo(resourceTypeDTO2);
        resourceTypeDTO2.setId(2L);
        assertThat(resourceTypeDTO1).isNotEqualTo(resourceTypeDTO2);
        resourceTypeDTO1.setId(null);
        assertThat(resourceTypeDTO1).isNotEqualTo(resourceTypeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(resourceTypeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(resourceTypeMapper.fromId(null)).isNull();
    }
}
