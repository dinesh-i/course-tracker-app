package com.expresscode.coursetracker.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A CourseCategory.
 */
@Entity
@Table(name = "course_category")
public class CourseCategory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "course_category_name", nullable = false)
    private String courseCategoryName;
    
    @ManyToMany
    @JoinTable(name = "course_category_course",
               joinColumns = @JoinColumn(name="course_categorys_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="courses_id", referencedColumnName="ID"))
    private Set<Course> courses = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseCategoryName() {
        return courseCategoryName;
    }
    
    public void setCourseCategoryName(String courseCategoryName) {
        this.courseCategoryName = courseCategoryName;
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
        CourseCategory courseCategory = (CourseCategory) o;
        if(courseCategory.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, courseCategory.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CourseCategory{" +
            "id=" + id +
            ", courseCategoryName='" + courseCategoryName + "'" +
            '}';
    }
}
