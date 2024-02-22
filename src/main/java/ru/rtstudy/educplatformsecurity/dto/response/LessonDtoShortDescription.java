package ru.rtstudy.educplatformsecurity.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Lesson with short description DTO")
public record LessonDtoShortDescription(
        @Schema(description = "Идентификатор урока", example = "1")
        Long id,
        @Schema(description = "Название урока", example = "Буферный кэш")
        String title,
        @Schema(description = "Описание урока", example = "На этом уроке вы узнаете что такое буферный кэш, его особенности и как его можно настраивать.")
        String description
) {
}
