package com.expresscode.coursetracker.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.expresscode.coursetracker.domain.UserCourseTracker;
import com.expresscode.coursetracker.repository.UserCourseTrackerRepository;
import com.expresscode.coursetracker.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserCourseTracker.
 */
@RestController
@RequestMapping("/api")
public class UserCourseTrackerResource {

    private final Logger log = LoggerFactory.getLogger(UserCourseTrackerResource.class);
        
    @Inject
    private UserCourseTrackerRepository userCourseTrackerRepository;
    
    /**
     * POST  /userCourseTrackers -> Create a new userCourseTracker.
     */
    @RequestMapping(value = "/userCourseTrackers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserCourseTracker> createUserCourseTracker(@RequestBody UserCourseTracker userCourseTracker) throws URISyntaxException {
        log.debug("REST request to save UserCourseTracker : {}", userCourseTracker);
        if (userCourseTracker.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("userCourseTracker", "idexists", "A new userCourseTracker cannot already have an ID")).body(null);
        }
        UserCourseTracker result = userCourseTrackerRepository.save(userCourseTracker);
        return ResponseEntity.created(new URI("/api/userCourseTrackers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("userCourseTracker", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /userCourseTrackers -> Updates an existing userCourseTracker.
     */
    @RequestMapping(value = "/userCourseTrackers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserCourseTracker> updateUserCourseTracker(@RequestBody UserCourseTracker userCourseTracker) throws URISyntaxException {
        log.debug("REST request to update UserCourseTracker : {}", userCourseTracker);
        if (userCourseTracker.getId() == null) {
            return createUserCourseTracker(userCourseTracker);
        }
        UserCourseTracker result = userCourseTrackerRepository.save(userCourseTracker);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("userCourseTracker", userCourseTracker.getId().toString()))
            .body(result);
    }

    /**
     * GET  /userCourseTrackers -> get all the userCourseTrackers.
     */
    @RequestMapping(value = "/userCourseTrackers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<UserCourseTracker> getAllUserCourseTrackers() {
        log.debug("REST request to get all UserCourseTrackers");
        return userCourseTrackerRepository.findAll();
            }

    /**
     * GET  /userCourseTrackers/:id -> get the "id" userCourseTracker.
     */
    @RequestMapping(value = "/userCourseTrackers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UserCourseTracker> getUserCourseTracker(@PathVariable Long id) {
        log.debug("REST request to get UserCourseTracker : {}", id);
        UserCourseTracker userCourseTracker = userCourseTrackerRepository.findOne(id);
        return Optional.ofNullable(userCourseTracker)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /userCourseTrackers/:id -> delete the "id" userCourseTracker.
     */
    @RequestMapping(value = "/userCourseTrackers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteUserCourseTracker(@PathVariable Long id) {
        log.debug("REST request to delete UserCourseTracker : {}", id);
        userCourseTrackerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("userCourseTracker", id.toString())).build();
    }
}
