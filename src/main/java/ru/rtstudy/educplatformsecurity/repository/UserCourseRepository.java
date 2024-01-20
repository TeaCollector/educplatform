package ru.rtstudy.educplatformsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.rtstudy.educplatformsecurity.model.UserCourse;

public interface UserCourseRepository extends JpaRepository<UserCourse, Long> {


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
}
