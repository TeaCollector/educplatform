package ru.rtstudy.educplatformsecurity.dto.response;

public record GradeDtoResponse(
        Long lessonId,
        String description,
        Byte grade,
        Boolean rework,
        String studentAnswer,
        String mentorAnswer
) {
}