package com.expresscode.coursetracker.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Course.
 */
@Entity
@Table(name = "course")
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "course_name", nullable = false)
    private String courseName;
    
    @NotNull
    @Column(name = "author", nullable = false)
    private String author;
    
    @ManyToOne
    @JoinColumn(name = "course_provider_id")
    private CourseProvider courseProvider;

    @ManyToMany(mappedBy = "courses")
    @JsonIgnore
    private Set<CourseCategory> courseCategorys = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }
    
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getAuthor() {
        return author;
    }
    
    public void setAuthor(String author) {
        this.author = author;
    }

    public CourseProvider getCourseProvider() {
        return courseProvider;
    }

    public void setCourseProvider(CourseProvider courseProvider) {
        this.courseProvider = courseProvider;
    }

    public Set<CourseCategory> getCourseCategorys() {
        return courseCategorys;
    }

    public void setCourseCategorys(Set<CourseCategory> courseCategorys) {
        this.courseCategorys = courseCategorys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Course course = (Course) o;
        if(course.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, course.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Course{" +
            "id=" + id +
            ", courseName='" + courseName + "'" +
            ", author='" + author + "'" +
            '}';
    }
}
