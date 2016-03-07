package com.expresscode.coursetracker.repository;

import com.expresscode.coursetracker.domain.UserCourseTracker;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UserCourseTracker entity.
 */
public interface UserCourseTrackerRepository extends JpaRepository<UserCourseTracker,Long> {

}
