package com.expresscode.coursetracker.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.expresscode.coursetracker.domain.Video;
import com.expresscode.coursetracker.repository.VideoRepository;
import com.expresscode.coursetracker.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Video.
 */
@RestController
@RequestMapping("/api")
public class VideoResource {

    private final Logger log = LoggerFactory.getLogger(VideoResource.class);
        
    @Inject
    private VideoRepository videoRepository;
    
    /**
     * POST  /videos -> Create a new video.
     */
    @RequestMapping(value = "/videos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Video> createVideo(@Valid @RequestBody Video video) throws URISyntaxException {
        log.debug("REST request to save Video : {}", video);
        if (video.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("video", "idexists", "A new video cannot already have an ID")).body(null);
        }
        Video result = videoRepository.save(video);
        return ResponseEntity.created(new URI("/api/videos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("video", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /videos -> Updates an existing video.
     */
    @RequestMapping(value = "/videos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Video> updateVideo(@Valid @RequestBody Video video) throws URISyntaxException {
        log.debug("REST request to update Video : {}", video);
        if (video.getId() == null) {
            return createVideo(video);
        }
        Video result = videoRepository.save(video);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("video", video.getId().toString()))
            .body(result);
    }

    /**
     * GET  /videos -> get all the videos.
     */
    @RequestMapping(value = "/videos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Video> getAllVideos() {
        log.debug("REST request to get all Videos");
        return videoRepository.findAll();
            }

    /**
     * GET  /videos/:id -> get the "id" video.
     */
    @RequestMapping(value = "/videos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Video> getVideo(@PathVariable Long id) {
        log.debug("REST request to get Video : {}", id);
        Video video = videoRepository.findOne(id);
        return Optional.ofNullable(video)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /videos/:id -> delete the "id" video.
     */
    @RequestMapping(value = "/videos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id) {
        log.debug("REST request to delete Video : {}", id);
        videoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("video", id.toString())).build();
    }
}
