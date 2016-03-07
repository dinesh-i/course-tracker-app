package com.expresscode.coursetracker.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CourseProvider.
 */
@Entity
@Table(name = "course_provider")
public class CourseProvider implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "course_provider_name", nullable = false)
    private String courseProviderName;
    
    @OneToMany(mappedBy = "courseProvider")
    @JsonIgnore
    private Set<Course> courses = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseProviderName() {
        return courseProviderName;
    }
    
    public void setCourseProviderName(String courseProviderName) {
        this.courseProviderName = courseProviderName;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CourseProvider courseProvider = (CourseProvider) o;
        if(courseProvider.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, courseProvider.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CourseProvider{" +
            "id=" + id +
            ", courseProviderName='" + courseProviderName + "'" +
            '}';
    }
}
