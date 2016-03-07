package com.expresscode.coursetracker.web.rest;

import com.expresscode.coursetracker.Application;
import com.expresscode.coursetracker.domain.UserCourseTracker;
import com.expresscode.coursetracker.repository.UserCourseTrackerRepository;

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
 * Test class for the UserCourseTrackerResource REST controller.
 *
 * @see UserCourseTrackerResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class UserCourseTrackerResourceIntTest {


    @Inject
    private UserCourseTrackerRepository userCourseTrackerRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restUserCourseTrackerMockMvc;

    private UserCourseTracker userCourseTracker;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserCourseTrackerResource userCourseTrackerResource = new UserCourseTrackerResource();
        ReflectionTestUtils.setField(userCourseTrackerResource, "userCourseTrackerRepository", userCourseTrackerRepository);
        this.restUserCourseTrackerMockMvc = MockMvcBuilders.standaloneSetup(userCourseTrackerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        userCourseTracker = new UserCourseTracker();
    }

    @Test
    @Transactional
    public void createUserCourseTracker() throws Exception {
        int databaseSizeBeforeCreate = userCourseTrackerRepository.findAll().size();

        // Create the UserCourseTracker

        restUserCourseTrackerMockMvc.perform(post("/api/userCourseTrackers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userCourseTracker)))
                .andExpect(status().isCreated());

        // Validate the UserCourseTracker in the database
        List<UserCourseTracker> userCourseTrackers = userCourseTrackerRepository.findAll();
        assertThat(userCourseTrackers).hasSize(databaseSizeBeforeCreate + 1);
        UserCourseTracker testUserCourseTracker = userCourseTrackers.get(userCourseTrackers.size() - 1);
    }

    @Test
    @Transactional
    public void getAllUserCourseTrackers() throws Exception {
        // Initialize the database
        userCourseTrackerRepository.saveAndFlush(userCourseTracker);

        // Get all the userCourseTrackers
        restUserCourseTrackerMockMvc.perform(get("/api/userCourseTrackers?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(userCourseTracker.getId().intValue())));
    }

    @Test
    @Transactional
    public void getUserCourseTracker() throws Exception {
        // Initialize the database
        userCourseTrackerRepository.saveAndFlush(userCourseTracker);

        // Get the userCourseTracker
        restUserCourseTrackerMockMvc.perform(get("/api/userCourseTrackers/{id}", userCourseTracker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(userCourseTracker.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUserCourseTracker() throws Exception {
        // Get the userCourseTracker
        restUserCourseTrackerMockMvc.perform(get("/api/userCourseTrackers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserCourseTracker() throws Exception {
        // Initialize the database
        userCourseTrackerRepository.saveAndFlush(userCourseTracker);

		int databaseSizeBeforeUpdate = userCourseTrackerRepository.findAll().size();

        // Update the userCourseTracker

        restUserCourseTrackerMockMvc.perform(put("/api/userCourseTrackers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(userCourseTracker)))
                .andExpect(status().isOk());

        // Validate the UserCourseTracker in the database
        List<UserCourseTracker> userCourseTrackers = userCourseTrackerRepository.findAll();
        assertThat(userCourseTrackers).hasSize(databaseSizeBeforeUpdate);
        UserCourseTracker testUserCourseTracker = userCourseTrackers.get(userCourseTrackers.size() - 1);
    }

    @Test
    @Transactional
    public void deleteUserCourseTracker() throws Exception {
        // Initialize the database
        userCourseTrackerRepository.saveAndFlush(userCourseTracker);

		int databaseSizeBeforeDelete = userCourseTrackerRepository.findAll().size();

        // Get the userCourseTracker
        restUserCourseTrackerMockMvc.perform(delete("/api/userCourseTrackers/{id}", userCourseTracker.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<UserCourseTracker> userCourseTrackers = userCourseTrackerRepository.findAll();
        assertThat(userCourseTrackers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
