package ru.rtstudy.educplatformsecurity.dto.mapper.impl;

import org.mapstruct.Mapper;
import ru.rtstudy.educplatformsecurity.dto.mapper.Mappable;
import ru.rtstudy.educplatformsecurity.dto.response.LessonDtoResponse;
import ru.rtstudy.educplatformsecurity.model.Lesson;

@Mapper(componentModel = "spring")
public interface LessonMapper extends Mappable<Lesson, LessonDtoResponse> {

    default LessonDtoResponse fromEntityToResponse(Lesson lesson) {
        return LessonDtoResponse.builder()
                .id(lesson.getId())
                .courseId(lesson.getCourse().getId())
                .referenceOnFile(lesson.getFileName())
                .title(lesson.getTitle())
                .description(lesson.getDescription())
                .build();
    }

    default LessonDtoResponse toLessonDtoResponse(Lesson lesson) {
        return LessonDtoResponse.builder()
                .id(lesson.getId())
                .courseId(lesson.getCourse().getId())
                .referenceOnFile(lesson.getFileName())
                .title(lesson.getTitle())
                .description(lesson.getDescription())
                .build();
    }
}
