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

//    default Course toEntity(CourseDtoRequest dtoRequest) {
//
//        Difficult difficult = Difficult.builder()
//                .difficult(DifficultLevel.valueOf(dtoRequest.difficult().toString()))
//                .build();
//
//        Category category = Category.builder()
//                .title(dtoRequest.title())
//                .build();
//
//        return Course.builder()
//                .title(dtoRequest.title())
//                .description(dtoRequest.description())
//                .difficult(difficult)
//                .category(category)
//                .duration(dtoRequest.duration())
//                .build();
//
//    }
}
