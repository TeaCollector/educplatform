package ru.rtstudy.educplatformsecurity.model;

import jakarta.persistence.*;
import lombok.*;
import ru.rtstudy.educplatformsecurity.model.constant.CreateUpdateTime;

import java.util.HashSet;
import java.util.Set;

@Builder
@Entity(name = "Course")
@Table(name = "courses")
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "difficult_id", referencedColumnName = "id")
    private Difficult difficult;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Set<UserCourse> coursesUser = new HashSet<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
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
