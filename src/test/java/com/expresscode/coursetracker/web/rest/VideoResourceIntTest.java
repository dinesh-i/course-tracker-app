package com.expresscode.coursetracker.web.rest;

import com.expresscode.coursetracker.Application;
import com.expresscode.coursetracker.domain.Video;
import com.expresscode.coursetracker.repository.VideoRepository;

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
 * Test class for the VideoResource REST controller.
 *
 * @see VideoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class VideoResourceIntTest {

    private static final String DEFAULT_VIDEO_NAME = "AAAAA";
    private static final String UPDATED_VIDEO_NAME = "BBBBB";
    private static final String DEFAULT_AUTHOR = "AAAAA";
    private static final String UPDATED_AUTHOR = "BBBBB";

    private static final Boolean DEFAULT_IS_COMPLETED = false;
    private static final Boolean UPDATED_IS_COMPLETED = true;

    @Inject
    private VideoRepository videoRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restVideoMockMvc;

    private Video video;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VideoResource videoResource = new VideoResource();
        ReflectionTestUtils.setField(videoResource, "videoRepository", videoRepository);
        this.restVideoMockMvc = MockMvcBuilders.standaloneSetup(videoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        video = new Video();
        video.setVideoName(DEFAULT_VIDEO_NAME);
        video.setAuthor(DEFAULT_AUTHOR);
        video.setIsCompleted(DEFAULT_IS_COMPLETED);
    }

    @Test
    @Transactional
    public void createVideo() throws Exception {
        int databaseSizeBeforeCreate = videoRepository.findAll().size();

        // Create the Video

        restVideoMockMvc.perform(post("/api/videos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(video)))
                .andExpect(status().isCreated());

        // Validate the Video in the database
        List<Video> videos = videoRepository.findAll();
        assertThat(videos).hasSize(databaseSizeBeforeCreate + 1);
        Video testVideo = videos.get(videos.size() - 1);
        assertThat(testVideo.getVideoName()).isEqualTo(DEFAULT_VIDEO_NAME);
        assertThat(testVideo.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testVideo.getIsCompleted()).isEqualTo(DEFAULT_IS_COMPLETED);
    }

    @Test
    @Transactional
    public void checkVideoNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = videoRepository.findAll().size();
        // set the field null
        video.setVideoName(null);

        // Create the Video, which fails.

        restVideoMockMvc.perform(post("/api/videos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(video)))
                .andExpect(status().isBadRequest());

        List<Video> videos = videoRepository.findAll();
        assertThat(videos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVideos() throws Exception {
        // Initialize the database
        videoRepository.saveAndFlush(video);

        // Get all the videos
        restVideoMockMvc.perform(get("/api/videos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(video.getId().intValue())))
                .andExpect(jsonPath("$.[*].videoName").value(hasItem(DEFAULT_VIDEO_NAME.toString())))
                .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
                .andExpect(jsonPath("$.[*].isCompleted").value(hasItem(DEFAULT_IS_COMPLETED.booleanValue())));
    }

    @Test
    @Transactional
    public void getVideo() throws Exception {
        // Initialize the database
        videoRepository.saveAndFlush(video);

        // Get the video
        restVideoMockMvc.perform(get("/api/videos/{id}", video.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(video.getId().intValue()))
            .andExpect(jsonPath("$.videoName").value(DEFAULT_VIDEO_NAME.toString()))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR.toString()))
            .andExpect(jsonPath("$.isCompleted").value(DEFAULT_IS_COMPLETED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVideo() throws Exception {
        // Get the video
        restVideoMockMvc.perform(get("/api/videos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVideo() throws Exception {
        // Initialize the database
        videoRepository.saveAndFlush(video);

		int databaseSizeBeforeUpdate = videoRepository.findAll().size();

        // Update the video
        video.setVideoName(UPDATED_VIDEO_NAME);
        video.setAuthor(UPDATED_AUTHOR);
        video.setIsCompleted(UPDATED_IS_COMPLETED);

        restVideoMockMvc.perform(put("/api/videos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(video)))
                .andExpect(status().isOk());

        // Validate the Video in the database
        List<Video> videos = videoRepository.findAll();
        assertThat(videos).hasSize(databaseSizeBeforeUpdate);
        Video testVideo = videos.get(videos.size() - 1);
        assertThat(testVideo.getVideoName()).isEqualTo(UPDATED_VIDEO_NAME);
        assertThat(testVideo.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testVideo.getIsCompleted()).isEqualTo(UPDATED_IS_COMPLETED);
    }

    @Test
    @Transactional
    public void deleteVideo() throws Exception {
        // Initialize the database
        videoRepository.saveAndFlush(video);

		int databaseSizeBeforeDelete = videoRepository.findAll().size();

        // Get the video
        restVideoMockMvc.perform(delete("/api/videos/{id}", video.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Video> videos = videoRepository.findAll();
        assertThat(videos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
