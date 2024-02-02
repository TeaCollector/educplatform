package ru.rtstudy.educplatformsecurity.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rtstudy.educplatformsecurity.dto.response.TaskDto;
import ru.rtstudy.educplatformsecurity.exception.exist.LessonNotFoundException;
import ru.rtstudy.educplatformsecurity.exception.user.NotCourseAuthorException;
import ru.rtstudy.educplatformsecurity.exception.exist.TaskNotFoundException;
import ru.rtstudy.educplatformsecurity.model.Lesson;
import ru.rtstudy.educplatformsecurity.model.Task;
import ru.rtstudy.educplatformsecurity.repository.TaskRepository;
import ru.rtstudy.educplatformsecurity.service.CourseService;
import ru.rtstudy.educplatformsecurity.service.TaskService;
import ru.rtstudy.educplatformsecurity.util.Util;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final CourseService courseService;
    private final Util util;

    @Override
    public TaskDto getTask(Long id) {
        log.info("{} get task by long id: {}", util.findUserFromContext().getEmail(), id);
        return taskRepository.getTaskById(id)
                .orElseThrow(() -> {
                    log.error("Not found task: {}", id, new TaskNotFoundException("Task was not found."));
                    return new TaskNotFoundException("Task was not found.");
                });
    }

    @Override
    public Task createTask(TaskDto taskDto) {
        log.info("{} create task: {}", util.findUserFromContext().getEmail(), taskDto);
        Task task = Task.builder()
                .description(taskDto.description())
                .build();

        task = taskRepository.save(task);
        Lesson lesson = taskRepository.getLessonById(taskDto.lessonId())
                .orElseThrow(() -> {
                    log.error("Lesson by task id: {} was not found", new LessonNotFoundException("Lesson was not found."));
                    return new LessonNotFoundException("Lesson was not found.");
                });
        lesson.setTaskId(task);
        return task;
    }

    @Override
    public Task updateTask(Long taskId, Task task) {
        log.info("{} update task id: {} with: {}", util.findUserFromContext().getEmail(), taskId, task);
        Task toUpdate = taskRepository.findById(taskId)
                .orElseThrow(() -> {
                    log.error("Task was not found: {}", taskId, new TaskNotFoundException("Task was not found"));
                    return new TaskNotFoundException("Task was not found");
                });
        Long courseId = taskRepository.findCourseByTaskId(toUpdate.getId());
        boolean isAuthor = courseService.isAuthor(courseId);
        if (isAuthor) {
            toUpdate.setDescription(task.getDescription());
        } else {
            log.error("{} is not course author.", util.findUserFromContext().getEmail());
            throw new NotCourseAuthorException("You are not course author.");
        }
        return task;
    }

    @Override
    public void deleteTask(Long taskId) {
        log.info("{} delete task: {}", util.findUserFromContext().getEmail(), taskId);
        Task taskToDelete = taskRepository.findById(taskId)
                .orElseThrow(() -> {
                    log.error("Task was not found: {}", taskId, new TaskNotFoundException("Task was not found"));
                    return new TaskNotFoundException("Task was not found");
                });
        Long courseId = taskRepository.findCourseByTaskId(taskToDelete.getId());
        boolean isAuthor = courseService.isAuthor(courseId);
        if (isAuthor) {
            taskRepository.deleteById(taskId);
        } else {
            log.error("{} is not course author: {}", util.findUserFromContext().getEmail(), courseId, new NotCourseAuthorException("You are not course author."));
            throw new NotCourseAuthorException("You are not course author.");
        }
    }
}
