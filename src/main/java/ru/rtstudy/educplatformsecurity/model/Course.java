package ru.rtstudy.educplatformsecurity.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rtstudy.educplatformsecurity.model.constant.CreateUpdateTime;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "Course")
@Table(name = "courses")
@Data
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "duration")
    private short duration;

    @ManyToOne
    @JoinColumn(name = "difficult_id", referencedColumnName = "id")
    private Difficult difficult;

    @OneToMany(mappedBy = "course")
    private Set<UserCourse> coursesUser = new HashSet<>();

    @OneToMany(mappedBy = "course")
    private Set<Lesson> lessons = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id" )
    private User courseAuthor;

    @Embedded
    private CreateUpdateTime time;
}
