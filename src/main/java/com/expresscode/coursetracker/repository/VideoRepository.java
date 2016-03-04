package com.expresscode.coursetracker.repository;

import com.expresscode.coursetracker.domain.Video;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Video entity.
 */
public interface VideoRepository extends JpaRepository<Video,Long> {

}
