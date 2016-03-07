package com.expresscode.coursetracker.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.expresscode.coursetracker.domain.CourseProvider;
import com.expresscode.coursetracker.repository.CourseProviderRepository;
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
 * REST controller for managing CourseProvider.
 */
@RestController
@RequestMapping("/api")
public class CourseProviderResource {

    private final Logger log = LoggerFactory.getLogger(CourseProviderResource.class);
        
    @Inject
    private CourseProviderRepository courseProviderRepository;
    
    /**
     * POST  /courseProviders -> Create a new courseProvider.
     */
    @RequestMapping(value = "/courseProviders",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CourseProvider> createCourseProvider(@Valid @RequestBody CourseProvider courseProvider) throws URISyntaxException {
        log.debug("REST request to save CourseProvider : {}", courseProvider);
        if (courseProvider.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("courseProvider", "idexists", "A new courseProvider cannot already have an ID")).body(null);
        }
        CourseProvider result = courseProviderRepository.save(courseProvider);
        return ResponseEntity.created(new URI("/api/courseProviders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("courseProvider", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /courseProviders -> Updates an existing courseProvider.
     */
    @RequestMapping(value = "/courseProviders",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CourseProvider> updateCourseProvider(@Valid @RequestBody CourseProvider courseProvider) throws URISyntaxException {
        log.debug("REST request to update CourseProvider : {}", courseProvider);
        if (courseProvider.getId() == null) {
            return createCourseProvider(courseProvider);
        }
        CourseProvider result = courseProviderRepository.save(courseProvider);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("courseProvider", courseProvider.getId().toString()))
            .body(result);
    }

    /**
     * GET  /courseProviders -> get all the courseProviders.
     */
    @RequestMapping(value = "/courseProviders",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CourseProvider> getAllCourseProviders() {
        log.debug("REST request to get all CourseProviders");
        return courseProviderRepository.findAll();
            }

    /**
     * GET  /courseProviders/:id -> get the "id" courseProvider.
     */
    @RequestMapping(value = "/courseProviders/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CourseProvider> getCourseProvider(@PathVariable Long id) {
        log.debug("REST request to get CourseProvider : {}", id);
        CourseProvider courseProvider = courseProviderRepository.findOne(id);
        return Optional.ofNullable(courseProvider)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /courseProviders/:id -> delete the "id" courseProvider.
     */
    @RequestMapping(value = "/courseProviders/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCourseProvider(@PathVariable Long id) {
        log.debug("REST request to delete CourseProvider : {}", id);
        courseProviderRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("courseProvider", id.toString())).build();
    }
}
