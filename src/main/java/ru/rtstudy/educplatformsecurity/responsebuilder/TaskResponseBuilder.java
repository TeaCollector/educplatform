package ru.rtstudy.educplatformsecurity.responsebuilder;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.rtstudy.educplatformsecurity.dto.mapper.impl.TaskMapper;
import ru.rtstudy.educplatformsecurity.dto.request.UpdateTaskDto;
import ru.rtstudy.educplatformsecurity.dto.request.TaskDto;
import ru.rtstudy.educplatformsecurity.dto.response.TaskDtoResponse;
import ru.rtstudy.educplatformsecurity.model.Task;
import ru.rtstudy.educplatformsecurity.service.TaskService;

@Service
@RequiredArgsConstructor
public class TaskResponseBuilder {

    private final TaskService taskService;
    private final TaskMapper mapper;


    public ResponseEntity<TaskDto> getTask(Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(taskService.getTask(id));
    }

    public ResponseEntity<TaskDtoResponse> createTask(TaskDto taskDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.toDtoResponse(taskService.createTask(taskDto)));
    }

    public ResponseEntity<TaskDtoResponse> updateTask(Long id, UpdateTaskDto taskDto) {
        Task task = Task.builder()
                .description(taskDto.description())
                .build();
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(mapper.toDtoResponse(taskService.updateTask(id, task)));
    }

    public ResponseEntity<HttpStatus> deleteTask(Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(HttpStatus.NO_CONTENT);
    }
}
