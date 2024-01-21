package ru.rtstudy.educplatformsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.rtstudy.educplatformsecurity.model.User;
import ru.rtstudy.educplatformsecurity.model.UserCourse;

import java.util.List;
import java.util.Optional;

public interface UserCourseRepository extends JpaRepository<UserCourse, Long> {

    Optional<List<UserCourse>> findAllByUserAndMentorCourse(User user, boolean mentorForCourse);

    @Query("""
            select count(us) > 0
            from UserCourse us
            where us.user.id = :userId
                AND us.course.id = :courseId
                AND us.mentorCourse = true
            """)
    boolean isMentorForCourse(Long userId, Long courseId);

    @Query("""
            select count(us) > 0
            from UserCourse us
            join Lesson l
            with l.id = :lessonId
            where us.user.id = :userId
                AND us.course.id = l.course.id
                AND us.mentorCourse = true
            """)
    boolean isMentorForLesson(Long userId, Long lessonId);

}
