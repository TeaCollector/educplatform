package ru.rtstudy.educplatformsecurity.responsebuilder;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.rtstudy.educplatformsecurity.dto.mapper.impl.TaskMapper;
import ru.rtstudy.educplatformsecurity.dto.response.TaskDto;
import ru.rtstudy.educplatformsecurity.model.Task;
import ru.rtstudy.educplatformsecurity.service.TaskService;

@Service
@RequiredArgsConstructor
public class TaskResponseBuilder {

    private final TaskService taskService;
    private final TaskMapper mapper;


    public TaskDto getTask(Long id) {
        return taskService.getTask(id);
    }

    public TaskDto createTask(TaskDto taskDto) {
        return mapper.toDto(taskService.createTask(taskDto));
    }

    public TaskDto updateTask(Long id, TaskDto taskDto) {
        Task task = mapper.toEntity(taskDto);
        return mapper.toDto(taskService.updateTask(id, task));
    }

    public HttpStatus deleteTask(Long taskId) {
        taskService.deleteTask(taskId);
        return HttpStatus.NO_CONTENT;
    }
}
