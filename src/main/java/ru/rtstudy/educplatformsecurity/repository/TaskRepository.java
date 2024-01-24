package ru.rtstudy.educplatformsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.rtstudy.educplatformsecurity.dto.response.TaskDto;
import ru.rtstudy.educplatformsecurity.model.Lesson;
import ru.rtstudy.educplatformsecurity.model.Task;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("""
            select new TaskDto(t.description) from Task t where t.id = :id
            """)
    Optional<TaskDto> getTaskById(@Param(value = "id") Long id);

    @Query("""
            select l
            from Lesson l
            where l.id = :lessonId
            """)
    Optional<Lesson> getLessonById(Long lessonId);
}
