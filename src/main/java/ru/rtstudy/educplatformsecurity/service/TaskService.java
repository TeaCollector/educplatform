package ru.rtstudy.educplatformsecurity.service;

import ru.rtstudy.educplatformsecurity.model.Task;

import java.util.Optional;

public interface TaskService {

    Task getTask(Long id);
}
