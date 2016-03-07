package com.expresscode.coursetracker.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.expresscode.coursetracker.domain.CourseCategory;
import com.expresscode.coursetracker.repository.CourseCategoryRepository;
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
 * REST controller for managing CourseCategory.
 */
@RestController
@RequestMapping("/api")
public class CourseCategoryResource {

    private final Logger log = LoggerFactory.getLogger(CourseCategoryResource.class);
        
    @Inject
    private CourseCategoryRepository courseCategoryRepository;
    
    /**
     * POST  /courseCategorys -> Create a new courseCategory.
     */
    @RequestMapping(value = "/courseCategorys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CourseCategory> createCourseCategory(@Valid @RequestBody CourseCategory courseCategory) throws URISyntaxException {
        log.debug("REST request to save CourseCategory : {}", courseCategory);
        if (courseCategory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("courseCategory", "idexists", "A new courseCategory cannot already have an ID")).body(null);
        }
        CourseCategory result = courseCategoryRepository.save(courseCategory);
        return ResponseEntity.created(new URI("/api/courseCategorys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("courseCategory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /courseCategorys -> Updates an existing courseCategory.
     */
    @RequestMapping(value = "/courseCategorys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CourseCategory> updateCourseCategory(@Valid @RequestBody CourseCategory courseCategory) throws URISyntaxException {
        log.debug("REST request to update CourseCategory : {}", courseCategory);
        if (courseCategory.getId() == null) {
            return createCourseCategory(courseCategory);
        }
        CourseCategory result = courseCategoryRepository.save(courseCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("courseCategory", courseCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /courseCategorys -> get all the courseCategorys.
     */
    @RequestMapping(value = "/courseCategorys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CourseCategory> getAllCourseCategorys() {
        log.debug("REST request to get all CourseCategorys");
        return courseCategoryRepository.findAllWithEagerRelationships();
            }

    /**
     * GET  /courseCategorys/:id -> get the "id" courseCategory.
     */
    @RequestMapping(value = "/courseCategorys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CourseCategory> getCourseCategory(@PathVariable Long id) {
        log.debug("REST request to get CourseCategory : {}", id);
        CourseCategory courseCategory = courseCategoryRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(courseCategory)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /courseCategorys/:id -> delete the "id" courseCategory.
     */
    @RequestMapping(value = "/courseCategorys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCourseCategory(@PathVariable Long id) {
        log.debug("REST request to delete CourseCategory : {}", id);
        courseCategoryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("courseCategory", id.toString())).build();
    }
}
