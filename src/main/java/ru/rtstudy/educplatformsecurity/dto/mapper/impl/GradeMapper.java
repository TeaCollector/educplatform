package ru.rtstudy.educplatformsecurity.dto.mapper.impl;

import org.mapstruct.Mapper;
import ru.rtstudy.educplatformsecurity.dto.mapper.Mappable;
import ru.rtstudy.educplatformsecurity.dto.response.GradeStudentDtoResponse;
import ru.rtstudy.educplatformsecurity.model.Grade;

@Mapper(componentModel = "spring")
public interface GradeMapper extends Mappable<Grade, GradeStudentDtoResponse> {

    default GradeStudentDtoResponse toDto(Grade grade) {
        return GradeStudentDtoResponse.builder()
                .firstName(grade.getStudent().getFirstName())
                .lastName(grade.getStudent().getLastName())
                .lessonId(grade.getLesson().getId())
                .description(grade.getLesson().getTaskId().getDescription())
                .grade(grade.getGrade())
                .rework(grade.isRework())
                .studentAnswer(grade.getStudentAnswer())
                .mentorAnswer(grade.getMentorAnswer())
                .build();
    }
}
