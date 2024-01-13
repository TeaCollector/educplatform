package ru.rtstudy.educplatformsecurity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rtstudy.educplatformsecurity.model.Task;
import ru.rtstudy.educplatformsecurity.service.TaskService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("{id}")
    public ResponseEntity<Task> getTask(@PathVariable(name = "id") Long id) {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(taskService.getTask(id));
    }
}
