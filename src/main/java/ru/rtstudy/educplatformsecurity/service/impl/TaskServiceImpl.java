package ru.rtstudy.educplatformsecurity.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rtstudy.educplatformsecurity.dto.response.TaskDto;
import ru.rtstudy.educplatformsecurity.exception.LessonNotFoundException;
import ru.rtstudy.educplatformsecurity.exception.NotCourseAuthorException;
import ru.rtstudy.educplatformsecurity.exception.TaskNotFoundException;
import ru.rtstudy.educplatformsecurity.model.Lesson;
import ru.rtstudy.educplatformsecurity.model.Task;
import ru.rtstudy.educplatformsecurity.repository.TaskRepository;
import ru.rtstudy.educplatformsecurity.service.CourseService;
import ru.rtstudy.educplatformsecurity.service.TaskService;


@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final CourseService courseService;

    @Override
    public TaskDto getTask(Long id) {
        return taskRepository.getTaskById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task was not found."));
    }

    @Override
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
    public Task updateTask(Long taskId, Task task) {
        Task toUpdate = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task was not found"));
        Long courseId = taskRepository.findCourseByTaskId(toUpdate.getId());
        boolean isAuthor = courseService.isAuthor(courseId);
        if (isAuthor) {
            toUpdate.setDescription(task.getDescription());
        } else {
            throw new NotCourseAuthorException("You are not course author.");
        }
        return task;
    }

    @Override
    public void deleteTask(Long taskId) {
        Task taskToDelete = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task was not found"));
        Long courseId = taskRepository.findCourseByTaskId(taskToDelete.getId());
        boolean isAuthor = courseService.isAuthor(courseId);
        if (isAuthor) {
            taskRepository.deleteById(taskId);
        } else {
            throw new NotCourseAuthorException("You are not course author.");
        }
    }
}
