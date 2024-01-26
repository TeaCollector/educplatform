package ru.rtstudy.educplatformsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import ru.rtstudy.educplatformsecurity.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
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
            where us.user.id = :userId
                AND us.course.id = :courseId
                AND us.mentorCourse = true
            """)
    boolean isMentorForCourse(Long userId, Long courseId);

    @Query("""
            select count(us) > 0
            from UserCourse us
            join Lesson l
            on l.id = :lessonId
            where us.user.id = :userId
                AND us.course.id = l.course.id
                AND us.mentorCourse = true
            """)
    boolean isMentorForLesson(Long userId, Long lessonId);

    @Modifying
    @Query("""
            update UserCourse us
            set us.endCourse = true, us.finishCourse = current_timestamp 
            where us.user.id = :userId
            and us.course.id = :courseId
            """)
    void finishCourse(Long userId, Long courseId);


    @Modifying
    @Query("""
            update UserCourse us
            set us.mentorCourse = true
            where us.user.id = :userId
            and us.course.id = :courseId
            """)
    void upgradeToMentor(Long userId, Long courseId);


    @Query("""
            select count(uc) > 0
            from UserCourse uc
            where uc.course.id = :courseId
            and uc.user.id = :userId
            """)
    boolean isMentorForLesson(Long userId, Long lessonId);

    @Modifying
    @Query("""
            update UserCourse us
            set us.endCourse = true, us.finishCourse = current_timestamp 
            where us.user.id = :userId
            and us.course.id = :courseId
            """)
    void finishCourse(Long userId, Long courseId);

    boolean onCourse(Long courseId, Long userId);
}

    @Modifying
    @Query("""
            update UserCourse us
            set us.mentorCourse = true
            where us.user.id = :userId
            and us.course.id = :courseId
            """)
    void upgradeToMentor(Long userId, Long courseId);
}