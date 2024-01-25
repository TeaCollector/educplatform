package ru.rtstudy.educplatformsecurity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.rtstudy.educplatformsecurity.dto.mapper.impl.TaskMapper;
import ru.rtstudy.educplatformsecurity.dto.response.TaskDto;
import ru.rtstudy.educplatformsecurity.model.Task;
import ru.rtstudy.educplatformsecurity.service.TaskService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper mapper;

    @GetMapping("{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable(name = "id") Long id) {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(taskService.getTask(id));
    }

    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        taskService.createTask(taskDto);
        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(taskDto);
    }

    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    @PutMapping("{task_id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable(name = "task_id") Long id,
                                                 @RequestBody TaskDto taskDto) {
        Task task = mapper.toEntity(taskDto);
        taskDto = mapper.toDto(taskService.updateTask(id, task));
        return ResponseEntity
                .status(HttpStatus.valueOf(201))
                .body(taskDto);
    }

    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    @DeleteMapping("{task_id}")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable(name = "task_id") Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity
                .ok(HttpStatus.valueOf(204));
    }
}
