package com.expresscode.coursetracker.repository;

import com.expresscode.coursetracker.domain.CourseProvider;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CourseProvider entity.
 */
public interface CourseProviderRepository extends JpaRepository<CourseProvider,Long> {

}
