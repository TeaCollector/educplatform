package ru.rtstudy.educplatformsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rtstudy.educplatformsecurity.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
