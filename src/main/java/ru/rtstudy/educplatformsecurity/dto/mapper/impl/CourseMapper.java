package ru.rtstudy.educplatformsecurity.dto.mapper.impl;

import org.mapstruct.Mapper;
import ru.rtstudy.educplatformsecurity.dto.mapper.Mappable;
import ru.rtstudy.educplatformsecurity.dto.request.CourseDtoRequest;
import ru.rtstudy.educplatformsecurity.model.Category;
import ru.rtstudy.educplatformsecurity.model.Course;
import ru.rtstudy.educplatformsecurity.model.Difficult;
import ru.rtstudy.educplatformsecurity.model.constant.DifficultLevel;

@Mapper(componentModel = "spring")
public interface CourseMapper extends Mappable<Course, CourseDtoRequest> {

    default Course toEntity(CourseDtoRequest dtoRequest) {

        Difficult difficult = Difficult.builder()
                .difficultLevel(DifficultLevel.valueOf(dtoRequest.difficult()))
                .build();

        Category category = Category.builder()
                .title(dtoRequest.category())
                .build();

        return Course.builder()
                .title(dtoRequest.title())
                .description(dtoRequest.description())
                .difficult(difficult)
                .category(category)
                .duration(dtoRequest.duration())
                .build();

    }

    default CourseDtoRequest toDto(Course course) {
        return CourseDtoRequest.builder()
                .difficult(course.getDifficult().getDifficultLevel().toString())
                .category(course.getCategory().getTitle())
                .title(course.getTitle())
                .description(course.getDescription())
                .duration(course.getDuration())
                .build();
    }
}
