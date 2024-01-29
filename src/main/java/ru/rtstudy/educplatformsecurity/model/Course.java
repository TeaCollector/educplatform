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

    @ToString.Include
    @Column(name = "title")
    private String title;

    @ToString.Include
    @Column(name = "description")
    private String description;

    @ToString.Include
    @Column(name = "duration")
    private short duration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "difficult_id", referencedColumnName = "id")
    private Difficult difficult;

    @OneToMany(mappedBy = "course")
    private Set<UserCourse> coursesUser = new HashSet<>();

    @OneToMany(mappedBy = "course")
    private Set<Lesson> lessons = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User courseAuthor;

    @Embedded
    private CreateUpdateTime time;

    @PreRemove
    private void preRemove() {
        lessons.forEach(lesson -> lesson.setCourse(null));
    }
}
