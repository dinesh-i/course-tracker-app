package com.expresscode.coursetracker.repository;

import com.expresscode.coursetracker.domain.Title;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Title entity.
 */
public interface TitleRepository extends JpaRepository<Title,Long> {

    @Query("select distinct title from Title title left join fetch title.books left join fetch title.videos")
    List<Title> findAllWithEagerRelationships();

    @Query("select title from Title title left join fetch title.books left join fetch title.videos where title.id =:id")
    Title findOneWithEagerRelationships(@Param("id") Long id);

}
