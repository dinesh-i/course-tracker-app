package com.expresscode.coursetracker.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A UserCourseTracker.
 */
@Entity
@Table(name = "user_course_tracker")
public class UserCourseTracker implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserCourseTracker userCourseTracker = (UserCourseTracker) o;
        if(userCourseTracker.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userCourseTracker.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserCourseTracker{" +
            "id=" + id +
            '}';
    }
}
