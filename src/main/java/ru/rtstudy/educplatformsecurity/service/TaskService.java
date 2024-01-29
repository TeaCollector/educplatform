package ru.rtstudy.educplatformsecurity.service;

import ru.rtstudy.educplatformsecurity.dto.response.TaskDto;
import ru.rtstudy.educplatformsecurity.model.Task;

public interface TaskService {

    TaskDto getTask(Long id);

    Task createTask(TaskDto task);

    Task updateTask(Long taskId, Task task);

    void deleteTask(Long id);

}
