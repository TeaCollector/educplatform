package ru.rtstudy.educplatformsecurity.model;


import jakarta.persistence.*;
import lombok.*;
import ru.rtstudy.educplatformsecurity.model.constant.CreateUpdateTime;

@Table(name = "grades")
@Entity(name = "Grade")
@Setter
@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
@NoArgsConstructor
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private User student;

    @ManyToOne
    @JoinColumn(name = "mentor_id", referencedColumnName = "id")
    private User mentor;

    @ManyToOne
    @JoinColumn(name = "lesson_id", referencedColumnName = "id")
    private Lesson lesson;

    @Column(name = "grade")
    private byte grade;

    @Column(name = "rework")
    private boolean rework;

    @Column(name = "student_answer")
    private String studentAnswer;

    @Column(name = "mentor_answer")
    private String mentorAnswer;

    @Embedded
    private CreateUpdateTime time;
}
