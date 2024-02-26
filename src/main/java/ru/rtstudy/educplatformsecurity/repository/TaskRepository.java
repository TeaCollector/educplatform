package ru.rtstudy.educplatformsecurity.repository;

import jakarta.persistence.ManyToOne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.rtstudy.educplatformsecurity.dto.request.TaskDto;
import ru.rtstudy.educplatformsecurity.model.Lesson;
import ru.rtstudy.educplatformsecurity.model.Task;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("""
            select new TaskDto(t.id, t.description) 
            from Task t 
            where t.id = :id
            """)
    Optional<TaskDto> getTaskById(@Param(value = "id") Long id);

    @Query("""
            select l
            from Lesson l
            where l.id = :lessonId
            """)
    Optional<Lesson> getLessonById(Long lessonId);

    @Query("""
            select l.course.id
            from Task t join Lesson l on t.id = l.id
            where t.id = :taskId
            """)
    Long findCourseByTaskId(Long taskId);

    @Modifying
    @Query("""
            update Lesson l
            set l.taskId = null 
            where l.id = :taskId
            """)
    void lessonSetToNull(Long taskId);
}
