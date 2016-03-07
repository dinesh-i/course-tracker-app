package com.expresscode.coursetracker.repository;

import com.expresscode.coursetracker.domain.Course;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Course entity.
 */
public interface CourseRepository extends JpaRepository<Course,Long> {

}
