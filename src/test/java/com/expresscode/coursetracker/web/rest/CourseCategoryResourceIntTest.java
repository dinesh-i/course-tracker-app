package com.expresscode.coursetracker.web.rest;

import com.expresscode.coursetracker.Application;
import com.expresscode.coursetracker.domain.CourseCategory;
import com.expresscode.coursetracker.repository.CourseCategoryRepository;

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
 * Test class for the CourseCategoryResource REST controller.
 *
 * @see CourseCategoryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class CourseCategoryResourceIntTest {

    private static final String DEFAULT_COURSE_CATEGORY_NAME = "AAAAA";
    private static final String UPDATED_COURSE_CATEGORY_NAME = "BBBBB";

    @Inject
    private CourseCategoryRepository courseCategoryRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restCourseCategoryMockMvc;

    private CourseCategory courseCategory;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CourseCategoryResource courseCategoryResource = new CourseCategoryResource();
        ReflectionTestUtils.setField(courseCategoryResource, "courseCategoryRepository", courseCategoryRepository);
        this.restCourseCategoryMockMvc = MockMvcBuilders.standaloneSetup(courseCategoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        courseCategory = new CourseCategory();
        courseCategory.setCourseCategoryName(DEFAULT_COURSE_CATEGORY_NAME);
    }

    @Test
    @Transactional
    public void createCourseCategory() throws Exception {
        int databaseSizeBeforeCreate = courseCategoryRepository.findAll().size();

        // Create the CourseCategory

        restCourseCategoryMockMvc.perform(post("/api/courseCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(courseCategory)))
                .andExpect(status().isCreated());

        // Validate the CourseCategory in the database
        List<CourseCategory> courseCategorys = courseCategoryRepository.findAll();
        assertThat(courseCategorys).hasSize(databaseSizeBeforeCreate + 1);
        CourseCategory testCourseCategory = courseCategorys.get(courseCategorys.size() - 1);
        assertThat(testCourseCategory.getCourseCategoryName()).isEqualTo(DEFAULT_COURSE_CATEGORY_NAME);
    }

    @Test
    @Transactional
    public void checkCourseCategoryNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = courseCategoryRepository.findAll().size();
        // set the field null
        courseCategory.setCourseCategoryName(null);

        // Create the CourseCategory, which fails.

        restCourseCategoryMockMvc.perform(post("/api/courseCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(courseCategory)))
                .andExpect(status().isBadRequest());

        List<CourseCategory> courseCategorys = courseCategoryRepository.findAll();
        assertThat(courseCategorys).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCourseCategorys() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        // Get all the courseCategorys
        restCourseCategoryMockMvc.perform(get("/api/courseCategorys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(courseCategory.getId().intValue())))
                .andExpect(jsonPath("$.[*].courseCategoryName").value(hasItem(DEFAULT_COURSE_CATEGORY_NAME.toString())));
    }

    @Test
    @Transactional
    public void getCourseCategory() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

        // Get the courseCategory
        restCourseCategoryMockMvc.perform(get("/api/courseCategorys/{id}", courseCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(courseCategory.getId().intValue()))
            .andExpect(jsonPath("$.courseCategoryName").value(DEFAULT_COURSE_CATEGORY_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCourseCategory() throws Exception {
        // Get the courseCategory
        restCourseCategoryMockMvc.perform(get("/api/courseCategorys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCourseCategory() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

		int databaseSizeBeforeUpdate = courseCategoryRepository.findAll().size();

        // Update the courseCategory
        courseCategory.setCourseCategoryName(UPDATED_COURSE_CATEGORY_NAME);

        restCourseCategoryMockMvc.perform(put("/api/courseCategorys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(courseCategory)))
                .andExpect(status().isOk());

        // Validate the CourseCategory in the database
        List<CourseCategory> courseCategorys = courseCategoryRepository.findAll();
        assertThat(courseCategorys).hasSize(databaseSizeBeforeUpdate);
        CourseCategory testCourseCategory = courseCategorys.get(courseCategorys.size() - 1);
        assertThat(testCourseCategory.getCourseCategoryName()).isEqualTo(UPDATED_COURSE_CATEGORY_NAME);
    }

    @Test
    @Transactional
    public void deleteCourseCategory() throws Exception {
        // Initialize the database
        courseCategoryRepository.saveAndFlush(courseCategory);

		int databaseSizeBeforeDelete = courseCategoryRepository.findAll().size();

        // Get the courseCategory
        restCourseCategoryMockMvc.perform(delete("/api/courseCategorys/{id}", courseCategory.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<CourseCategory> courseCategorys = courseCategoryRepository.findAll();
        assertThat(courseCategorys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
