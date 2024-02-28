package ru.rtstudy.educplatformsecurity.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rtstudy.educplatformsecurity.dto.request.TaskDto;
import ru.rtstudy.educplatformsecurity.exception.entity.LessonNotFoundException;
import ru.rtstudy.educplatformsecurity.exception.author.NotCourseAuthorException;
import ru.rtstudy.educplatformsecurity.exception.entity.TaskNotFoundException;
import ru.rtstudy.educplatformsecurity.model.Lesson;
import ru.rtstudy.educplatformsecurity.model.Task;
import ru.rtstudy.educplatformsecurity.repository.TaskRepository;
import ru.rtstudy.educplatformsecurity.service.CourseService;
import ru.rtstudy.educplatformsecurity.service.LessonService;
import ru.rtstudy.educplatformsecurity.service.TaskService;
import ru.rtstudy.educplatformsecurity.util.Util;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final CourseService courseService;
    private final LessonService lessonService;
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

        Lesson lesson = lessonService.findById(taskDto.lessonId())
                .orElseThrow(() -> {
                    log.error("Lesson was not found: {}", taskDto.lessonId());
                    return new LessonNotFoundException("Lesson was not found.");
                });
        if (courseService.isAuthor(lesson.getCourse().getId())) {
            task = taskRepository.save(task);
            lesson.setTaskId(task);
            return task;
        } else {
            log.error("{} is not course author", lesson.getCourse().getCourseAuthor().getId());
            throw new NotCourseAuthorException("You are not course author");
        }
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
        if (courseService.isAuthor(courseId)) {
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
        log.info("TASK TO DELETE IS: {}, {}", taskToDelete.getId(), taskToDelete.getDescription());
        Long courseId = taskRepository.findCourseByTaskId(taskToDelete.getId());
        boolean isAuthor = courseService.isAuthor(courseId);
        if (isAuthor) {
            taskRepository.lessonSetToNull(taskId);
            taskRepository.deleteById(taskId);
        } else {
            log.error("{} is not course author: {}", util.findUserFromContext().getEmail(), courseId, new NotCourseAuthorException("You are not course author."));
            throw new NotCourseAuthorException("You are not course author.");
        }
    }
}
