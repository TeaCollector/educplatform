package ru.rtstudy.educplatformsecurity.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.rtstudy.educplatformsecurity.dto.response.GradeDtoResponse;
import ru.rtstudy.educplatformsecurity.model.Grade;

import java.util.List;
import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade, Long> {

    @Query("""
            select g
            from Grade g
            join fetch g.lesson l
            where l.course.id = :courseId
            """)
    Optional<List<Grade>> findAllByCourseId(Long courseId);

    @Query("""
            select g
            from Grade g
            join fetch g.lesson l
            where l.id = :lessonId
            """)
    Optional<List<Grade>> findAllByLessonId(Long lessonId);

    @Query("""
            select new GradeDtoResponse(g.lesson.id, t.description, g.grade, g.rework, g.studentAnswer, g.mentorAnswer)
            from Grade g
            join g.lesson l
            join l.taskId t
            where g.id = :gradeId
            """)
    Optional<GradeDtoResponse> getGradeById(Long gradeId);

}
