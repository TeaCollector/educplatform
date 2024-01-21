package ru.rtstudy.educplatformsecurity.dto.response;

public record GradeDtoResponse (
        Long lessonId,
        String description,
        byte grade,
        boolean rework,
        String studentAnswer,
        String mentorAnswer
) {
}