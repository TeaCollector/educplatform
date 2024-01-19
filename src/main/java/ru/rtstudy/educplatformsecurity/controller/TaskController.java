package ru.rtstudy.educplatformsecurity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        Task task = mapper.toEntity(taskDto);
        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(mapper.toDto(taskService.createTask(task)));
    }

    @PutMapping("{id}")
    public ResponseEntity<HttpStatus> updateTask(@PathVariable(name = "id") Long id,
                                                 @RequestBody TaskDto taskDto) {
        Task task = mapper.toEntity(taskDto);
        taskService.updateTask(id, task);
        return ResponseEntity.ok(HttpStatus.valueOf(201));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable(name = "id") Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok(HttpStatus.valueOf(204));
    }
}
