package ru.rtstudy.educplatformsecurity.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import ru.rtstudy.educplatformsecurity.api.TaskApi;
import ru.rtstudy.educplatformsecurity.api.responsebuilder.TaskResponseBuilder;
import ru.rtstudy.educplatformsecurity.dto.mapper.impl.TaskMapper;
import ru.rtstudy.educplatformsecurity.dto.response.TaskDto;
import ru.rtstudy.educplatformsecurity.model.Task;
import ru.rtstudy.educplatformsecurity.service.TaskService;

@RestController
@RequiredArgsConstructor
public class TaskController implements TaskApi {

    private final TaskResponseBuilder responseBuilder;

    @Override
    public ResponseEntity<TaskDto> getTask(Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBuilder.getTask(id));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public ResponseEntity<TaskDto> createTask(TaskDto taskDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseBuilder.createTask(taskDto));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public ResponseEntity<TaskDto> updateTask(Long id, TaskDto taskDto) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(responseBuilder.updateTask(id, taskDto));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public ResponseEntity<HttpStatus> deleteTask(Long taskId) {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(responseBuilder.deleteTask(taskId));
    }
}
