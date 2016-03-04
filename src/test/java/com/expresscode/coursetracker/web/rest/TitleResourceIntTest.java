package com.expresscode.coursetracker.web.rest;

import com.expresscode.coursetracker.Application;
import com.expresscode.coursetracker.domain.Title;
import com.expresscode.coursetracker.repository.TitleRepository;

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
 * Test class for the TitleResource REST controller.
 *
 * @see TitleResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TitleResourceIntTest {

    private static final String DEFAULT_TITLE_NAME = "AAAAA";
    private static final String UPDATED_TITLE_NAME = "BBBBB";

    private static final Boolean DEFAULT_IS_COMPLETED = false;
    private static final Boolean UPDATED_IS_COMPLETED = true;

    @Inject
    private TitleRepository titleRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restTitleMockMvc;

    private Title title;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TitleResource titleResource = new TitleResource();
        ReflectionTestUtils.setField(titleResource, "titleRepository", titleRepository);
        this.restTitleMockMvc = MockMvcBuilders.standaloneSetup(titleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        title = new Title();
        title.setTitleName(DEFAULT_TITLE_NAME);
        title.setIsCompleted(DEFAULT_IS_COMPLETED);
    }

    @Test
    @Transactional
    public void createTitle() throws Exception {
        int databaseSizeBeforeCreate = titleRepository.findAll().size();

        // Create the Title

        restTitleMockMvc.perform(post("/api/titles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(title)))
                .andExpect(status().isCreated());

        // Validate the Title in the database
        List<Title> titles = titleRepository.findAll();
        assertThat(titles).hasSize(databaseSizeBeforeCreate + 1);
        Title testTitle = titles.get(titles.size() - 1);
        assertThat(testTitle.getTitleName()).isEqualTo(DEFAULT_TITLE_NAME);
        assertThat(testTitle.getIsCompleted()).isEqualTo(DEFAULT_IS_COMPLETED);
    }

    @Test
    @Transactional
    public void checkTitleNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = titleRepository.findAll().size();
        // set the field null
        title.setTitleName(null);

        // Create the Title, which fails.

        restTitleMockMvc.perform(post("/api/titles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(title)))
                .andExpect(status().isBadRequest());

        List<Title> titles = titleRepository.findAll();
        assertThat(titles).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTitles() throws Exception {
        // Initialize the database
        titleRepository.saveAndFlush(title);

        // Get all the titles
        restTitleMockMvc.perform(get("/api/titles?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(title.getId().intValue())))
                .andExpect(jsonPath("$.[*].titleName").value(hasItem(DEFAULT_TITLE_NAME.toString())))
                .andExpect(jsonPath("$.[*].isCompleted").value(hasItem(DEFAULT_IS_COMPLETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getTitle() throws Exception {
        // Initialize the database
        titleRepository.saveAndFlush(title);

        // Get the title
        restTitleMockMvc.perform(get("/api/titles/{id}", title.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(title.getId().intValue()))
            .andExpect(jsonPath("$.titleName").value(DEFAULT_TITLE_NAME.toString()))
            .andExpect(jsonPath("$.isCompleted").value(DEFAULT_IS_COMPLETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTitle() throws Exception {
        // Get the title
        restTitleMockMvc.perform(get("/api/titles/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTitle() throws Exception {
        // Initialize the database
        titleRepository.saveAndFlush(title);

		int databaseSizeBeforeUpdate = titleRepository.findAll().size();

        // Update the title
        title.setTitleName(UPDATED_TITLE_NAME);
        title.setIsCompleted(UPDATED_IS_COMPLETED);

        restTitleMockMvc.perform(put("/api/titles")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(title)))
                .andExpect(status().isOk());

        // Validate the Title in the database
        List<Title> titles = titleRepository.findAll();
        assertThat(titles).hasSize(databaseSizeBeforeUpdate);
        Title testTitle = titles.get(titles.size() - 1);
        assertThat(testTitle.getTitleName()).isEqualTo(UPDATED_TITLE_NAME);
        assertThat(testTitle.getIsCompleted()).isEqualTo(UPDATED_IS_COMPLETED);
    }

    @Test
    @Transactional
    public void deleteTitle() throws Exception {
        // Initialize the database
        titleRepository.saveAndFlush(title);

		int databaseSizeBeforeDelete = titleRepository.findAll().size();

        // Get the title
        restTitleMockMvc.perform(delete("/api/titles/{id}", title.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Title> titles = titleRepository.findAll();
        assertThat(titles).hasSize(databaseSizeBeforeDelete - 1);
    }
}
