package ru.rtstudy.educplatformsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.rtstudy.educplatformsecurity.dto.response.AllStudentAnswers;
import ru.rtstudy.educplatformsecurity.model.Grade;

import java.util.List;
import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade, Long> {

    @Query("""
            select new AllStudentAnswers(t.description,
             g.grade, 
             g.rework, 
             g.studentAnswer, 
             g.mentorAnswer) 
            from Grade g 
            join Lesson l on g.lesson.id = l.id
            join Task t on l.taskId.id = t.id
            where g.student.id = :id
            """)
    Optional<List<AllStudentAnswers>> getAllStudentAnswer(Long id);

    @Query("""
            select new AllStudentAnswers(t.description,
             g.grade, 
             g.rework, 
             g.studentAnswer, 
             g.mentorAnswer) 
            from Grade g 
            join Lesson l on g.lesson.id = l.id
            join Task t on l.taskId.id = t.id
            where g.student.id = :userId
            and l.course.id = :courseId
            """)
    Optional<List<AllStudentAnswers>> findAllStudentsAnswerForCourse(Long courseId, Long userId);

    @Modifying
    @Query("""
            update Grade g
            set g.studentAnswer = :studentAnswer
            where g.lesson.id = :id
            and g.student.id = :studentId

            """)
    void changeAnswer(Long id, String studentAnswer, Long studentId);

    @Query("""
            select l.id
            from Lesson l
            where l.course.id = :courseId
            """)
    Optional<List<Long>> getAllLessonsId(Long courseId);

    @Query(value = """
            select g 
            from Grade g
            where g.lesson.id in :lessonsIds
            and g.rework = false
            and g.grade > 0
            and g.student.id = :userId
            """)
    Optional<List<Grade>> getGrades(@Param(value = "lessonsIds") List<Long> lessonsIds,
                                    @Param(value = "userId") Long userId);
}
