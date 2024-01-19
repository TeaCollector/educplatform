package ru.rtstudy.educplatformsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.rtstudy.educplatformsecurity.dto.response.StudentAnswerAtAllLesson;
import ru.rtstudy.educplatformsecurity.model.Grade;

import java.util.List;
import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade, Long> {


    @Query("""
            select new StudentAnswerAtAllLesson(t.description,
             g.grade, 
             g.rework, 
             g.studentAnswer, 
             g.mentorAnswer) 
            from Grade g 
            join Lesson l on g.lesson.id = l.id
            join Task t on l.taskId.id = t.id
            where g.student.id = :id
            """)
    Optional<List<StudentAnswerAtAllLesson>> getAllStudentAnswer(Long id);

}
