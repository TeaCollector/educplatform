package ru.rtstudy.educplatformsecurity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.rtstudy.educplatformsecurity.responsebuilder.TaskResponseBuilder;
import ru.rtstudy.educplatformsecurity.dto.response.TaskDto;

@RestController
@RequiredArgsConstructor
@Tag(name = "Task Controller", description = "Task Controller API")
@RequestMapping("api/v1/tasks")
public class TaskController {

    private final TaskResponseBuilder responseBuilder;

    @Operation(summary = "Получить задание по его идентификатору")
    @GetMapping("{task_id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable(name = "task_id")
                                           @Parameter(name = "task_id", description = "Идентификатор задания") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBuilder.getTask(id));
    }

    @Operation(summary = "Создать новое задание")
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseBuilder.createTask(taskDto));
    }

    @Operation(summary = "Изменить задание по его идентификатору")
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    @PutMapping("{task_id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable(name = "task_id")
                                              @Parameter(name = "task_id", description = "Идентификатор задания") Long id,
                                              @RequestBody TaskDto taskDto) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(responseBuilder.updateTask(id, taskDto));
    }

    @Operation(summary = "Удалить задание по его идентификатору")
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    @DeleteMapping("{task_id}")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable(name = "task_id")
                                                 @Parameter(name = "task_id", description = "Идентификатор задания") Long taskId) {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(responseBuilder.deleteTask(taskId));
    }
}
