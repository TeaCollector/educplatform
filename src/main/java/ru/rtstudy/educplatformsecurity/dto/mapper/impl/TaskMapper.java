package ru.rtstudy.educplatformsecurity.dto.mapper.impl;

import org.mapstruct.Mapper;
import ru.rtstudy.educplatformsecurity.dto.mapper.Mappable;
import ru.rtstudy.educplatformsecurity.dto.response.TaskDto;
import ru.rtstudy.educplatformsecurity.model.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper extends Mappable<Task, TaskDto> {
}
