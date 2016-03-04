package com.expresscode.coursetracker.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Title.
 */
@Entity
@Table(name = "title")
public class Title implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "title_name", nullable = false)
    private String titleName;
    
    @Column(name = "is_completed")
    private Boolean isCompleted;
    
    @ManyToMany
    @JoinTable(name = "title_book",
               joinColumns = @JoinColumn(name="titles_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="books_id", referencedColumnName="ID"))
    private Set<Book> books = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "title_video",
               joinColumns = @JoinColumn(name="titles_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="videos_id", referencedColumnName="ID"))
    private Set<Video> videos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitleName() {
        return titleName;
    }
    
    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }
    
    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public Set<Video> getVideos() {
        return videos;
    }

    public void setVideos(Set<Video> videos) {
        this.videos = videos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Title title = (Title) o;
        if(title.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, title.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Title{" +
            "id=" + id +
            ", titleName='" + titleName + "'" +
            ", isCompleted='" + isCompleted + "'" +
            '}';
    }
}
