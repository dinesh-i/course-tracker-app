package com.expresscode.coursetracker.repository;

import com.expresscode.coursetracker.domain.CourseCategory;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the CourseCategory entity.
 */
public interface CourseCategoryRepository extends JpaRepository<CourseCategory,Long> {

    @Query("select distinct courseCategory from CourseCategory courseCategory left join fetch courseCategory.courses")
    List<CourseCategory> findAllWithEagerRelationships();

    @Query("select courseCategory from CourseCategory courseCategory left join fetch courseCategory.courses where courseCategory.id =:id")
    CourseCategory findOneWithEagerRelationships(@Param("id") Long id);

}
