package ru.rtstudy.educplatformsecurity.dto.response;

import lombok.Builder;

@Builder(toBuilder = true)
public record GradeStudentDtoResponse(
        String firstName,
        String lastName,
        Long lessonId,
        String description,
        byte grade,
        boolean rework,
        String studentAnswer,
        String mentorAnswer
) {
}
