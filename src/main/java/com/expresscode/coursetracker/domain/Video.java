package com.expresscode.coursetracker.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Video.
 */
@Entity
@Table(name = "video")
public class Video implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "video_name", nullable = false)
    private String videoName;
    
    @Column(name = "author")
    private String author;
    
    @Column(name = "is_completed")
    private Boolean isCompleted;
    
    @ManyToMany(mappedBy = "videos")
    @JsonIgnore
    private Set<Title> titles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVideoName() {
        return videoName;
    }
    
    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }
    
    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Set<Title> getTitles() {
        return titles;
    }

    public void setTitles(Set<Title> titles) {
        this.titles = titles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Video video = (Video) o;
        if(video.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, video.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Video{" +
            "id=" + id +
            ", videoName='" + videoName + "'" +
            ", author='" + author + "'" +
            ", isCompleted='" + isCompleted + "'" +
            '}';
    }
}
