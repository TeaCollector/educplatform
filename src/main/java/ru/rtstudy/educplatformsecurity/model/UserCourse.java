package ru.rtstudy.educplatformsecurity.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "users-courses")
@Entity(name = "UserCourse")
@Setter
@Getter
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ToString.Include
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ToString.Include
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    @Column(name = "end_course")
    private boolean endCourse;

    @Column(name = "mentor_course")
    private boolean mentorCourse;

    @Column(name = "begin_course")
    private LocalDateTime beginCourse;

    @Column(name = "finish_course")
    private LocalDateTime finishCourse;
}