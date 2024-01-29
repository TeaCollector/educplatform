package ru.rtstudy.educplatformsecurity.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import ru.rtstudy.educplatformsecurity.api.TaskApi;
import ru.rtstudy.educplatformsecurity.dto.mapper.impl.TaskMapper;
import ru.rtstudy.educplatformsecurity.dto.response.TaskDto;
import ru.rtstudy.educplatformsecurity.model.Task;
import ru.rtstudy.educplatformsecurity.service.TaskService;

@RestController
@RequiredArgsConstructor
public class TaskController implements TaskApi {

    private final TaskService taskService;
    private final TaskMapper mapper;

    @Override
    public ResponseEntity<TaskDto> getTask(Long id) {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(taskService.getTask(id));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public ResponseEntity<TaskDto> createTask(TaskDto taskDto) {
        taskService.createTask(taskDto);
        return ResponseEntity
                .status(HttpStatusCode.valueOf(201))
                .body(taskDto);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public ResponseEntity<TaskDto> updateTask(Long id, TaskDto taskDto) {
        Task task = mapper.toEntity(taskDto);
        taskDto = mapper.toDto(taskService.updateTask(id, task));
        return ResponseEntity
                .status(HttpStatus.valueOf(200))
                .body(taskDto);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public ResponseEntity<HttpStatus> deleteTask(Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity
                .ok(HttpStatus.valueOf(204));
    }
}
