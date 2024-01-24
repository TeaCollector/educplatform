package ru.rtstudy.educplatformsecurity.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rtstudy.educplatformsecurity.dto.response.TaskDto;
import ru.rtstudy.educplatformsecurity.exception.LessonNotFoundException;
import ru.rtstudy.educplatformsecurity.exception.TaskNotFoundException;
import ru.rtstudy.educplatformsecurity.model.Lesson;
import ru.rtstudy.educplatformsecurity.model.Task;
import ru.rtstudy.educplatformsecurity.repository.TaskRepository;
import ru.rtstudy.educplatformsecurity.service.TaskService;


@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public TaskDto getTask(Long id) {
        return taskRepository.getTaskById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task was not found."));
    }

    @Override
    @Transactional
    public Task createTask(TaskDto taskDto) {
        Task task = Task.builder()
                .description(taskDto.description())
                .build();

        task = taskRepository.save(task);
        Lesson lesson = taskRepository.getLessonById(taskDto.lessonId())
                .orElseThrow(() -> new LessonNotFoundException("Lesson was not found."));
        lesson.setTaskId(task);
        return task;
    }

    @Override
    @Transactional
    public void updateTask(Long id, Task task) {
        Task toUpdate = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task was not found"));
        toUpdate.setDescription(task.getDescription());
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
