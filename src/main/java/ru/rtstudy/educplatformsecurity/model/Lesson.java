package ru.rtstudy.educplatformsecurity.model;

import jakarta.persistence.*;
import lombok.*;
import ru.rtstudy.educplatformsecurity.model.constant.CreateUpdateTime;

@Entity(name = "Lesson")
@Table(name = "lessons")
@NoArgsConstructor
@Setter
@Getter
@ToString
@AllArgsConstructor
@Builder(toBuilder = true)
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "file_name")
    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private Task taskId;

    @Embedded
    private CreateUpdateTime time;

    @PreRemove
    private void preRemove() {
        this.taskId = null;
    }
}
