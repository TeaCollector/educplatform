package ru.rtstudy.educplatformsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.rtstudy.educplatformsecurity.dto.response.GradeDtoResponse;
import ru.rtstudy.educplatformsecurity.model.Grade;
import ru.rtstudy.educplatformsecurity.model.User;

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
            set g.grade = :grade,
                g.rework = :rework,
                g.mentorAnswer = :mentorAnswer,
                g.mentor = :mentor
            where g.id = :gradeId
            """)
    void addMentorReview(Long gradeId, Byte grade, Boolean rework, String mentorAnswer, User mentor);

    @Modifying
    @Query("""
            update Grade g
            set g.grade = :grade,
                g.rework = :rework,
                g.mentorAnswer = :mentorAnswer
            where g.id = :gradeId
            """)
    void updateMentorReview(Long gradeId, Byte grade, Boolean rework, String mentorAnswer);

    @Query("""
           select count(g)
           from Grade g
           where g.mentor.id = :userId
           """)
    int countAllAnswersByMentorUserId(Long userId);
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
