package ru.rtstudy.educplatformsecurity.dto.mapper.impl;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.rtstudy.educplatformsecurity.dto.mapper.Mappable;
import ru.rtstudy.educplatformsecurity.dto.request.TaskDto;
import ru.rtstudy.educplatformsecurity.dto.response.TaskDtoResponse;
import ru.rtstudy.educplatformsecurity.model.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper extends Mappable<Task, TaskDto> {

    default TaskDtoResponse toDtoResponse(Task entity) {
        return TaskDtoResponse.builder()
                .taskId(entity.getId())
                .description(entity.getDescription())
                .build();
    }
}
