package ru.rtstudy.educplatformsecurity.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;

@Table(name = "grades")
@Entity(name = "Grade")
@Data
@NoArgsConstructor
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private User student;

    @OneToOne
    @JoinColumn(name = "mentor_id", referencedColumnName = "id")
    private User mentor;

    @OneToOne
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

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
