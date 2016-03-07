package com.expresscode.coursetracker.web.rest;

import com.expresscode.coursetracker.Application;
import com.expresscode.coursetracker.domain.CourseProvider;
import com.expresscode.coursetracker.repository.CourseProviderRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the CourseProviderResource REST controller.
 *
 * @see CourseProviderResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CourseProviderResourceIntTest {

    private static final String DEFAULT_COURSE_PROVIDER_NAME = "AAAAA";
    private static final String UPDATED_COURSE_PROVIDER_NAME = "BBBBB";

    @Inject
    private CourseProviderRepository courseProviderRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCourseProviderMockMvc;

    private CourseProvider courseProvider;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CourseProviderResource courseProviderResource = new CourseProviderResource();
        ReflectionTestUtils.setField(courseProviderResource, "courseProviderRepository", courseProviderRepository);
        this.restCourseProviderMockMvc = MockMvcBuilders.standaloneSetup(courseProviderResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        courseProvider = new CourseProvider();
        courseProvider.setCourseProviderName(DEFAULT_COURSE_PROVIDER_NAME);
    }

    @Test
    @Transactional
    public void createCourseProvider() throws Exception {
        int databaseSizeBeforeCreate = courseProviderRepository.findAll().size();

        // Create the CourseProvider

        restCourseProviderMockMvc.perform(post("/api/courseProviders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(courseProvider)))
                .andExpect(status().isCreated());

        // Validate the CourseProvider in the database
        List<CourseProvider> courseProviders = courseProviderRepository.findAll();
        assertThat(courseProviders).hasSize(databaseSizeBeforeCreate + 1);
        CourseProvider testCourseProvider = courseProviders.get(courseProviders.size() - 1);
        assertThat(testCourseProvider.getCourseProviderName()).isEqualTo(DEFAULT_COURSE_PROVIDER_NAME);
    }

    @Test
    @Transactional
    public void checkCourseProviderNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseProviderRepository.findAll().size();
        // set the field null
        courseProvider.setCourseProviderName(null);

        // Create the CourseProvider, which fails.

        restCourseProviderMockMvc.perform(post("/api/courseProviders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(courseProvider)))
                .andExpect(status().isBadRequest());

        List<CourseProvider> courseProviders = courseProviderRepository.findAll();
        assertThat(courseProviders).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCourseProviders() throws Exception {
        // Initialize the database
        courseProviderRepository.saveAndFlush(courseProvider);

        // Get all the courseProviders
        restCourseProviderMockMvc.perform(get("/api/courseProviders?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(courseProvider.getId().intValue())))
                .andExpect(jsonPath("$.[*].courseProviderName").value(hasItem(DEFAULT_COURSE_PROVIDER_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCourseProvider() throws Exception {
        // Initialize the database
        courseProviderRepository.saveAndFlush(courseProvider);

        // Get the courseProvider
        restCourseProviderMockMvc.perform(get("/api/courseProviders/{id}", courseProvider.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(courseProvider.getId().intValue()))
            .andExpect(jsonPath("$.courseProviderName").value(DEFAULT_COURSE_PROVIDER_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCourseProvider() throws Exception {
        // Get the courseProvider
        restCourseProviderMockMvc.perform(get("/api/courseProviders/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourseProvider() throws Exception {
        // Initialize the database
        courseProviderRepository.saveAndFlush(courseProvider);

		int databaseSizeBeforeUpdate = courseProviderRepository.findAll().size();

        // Update the courseProvider
        courseProvider.setCourseProviderName(UPDATED_COURSE_PROVIDER_NAME);

        restCourseProviderMockMvc.perform(put("/api/courseProviders")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(courseProvider)))
                .andExpect(status().isOk());

        // Validate the CourseProvider in the database
        List<CourseProvider> courseProviders = courseProviderRepository.findAll();
        assertThat(courseProviders).hasSize(databaseSizeBeforeUpdate);
        CourseProvider testCourseProvider = courseProviders.get(courseProviders.size() - 1);
        assertThat(testCourseProvider.getCourseProviderName()).isEqualTo(UPDATED_COURSE_PROVIDER_NAME);
    }

    @Test
    @Transactional
    public void deleteCourseProvider() throws Exception {
        // Initialize the database
        courseProviderRepository.saveAndFlush(courseProvider);

		int databaseSizeBeforeDelete = courseProviderRepository.findAll().size();

        // Get the courseProvider
        restCourseProviderMockMvc.perform(delete("/api/courseProviders/{id}", courseProvider.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CourseProvider> courseProviders = courseProviderRepository.findAll();
        assertThat(courseProviders).hasSize(databaseSizeBeforeDelete - 1);
    }
}
