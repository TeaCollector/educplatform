package ru.rtstudy.educplatformsecurity.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Task DTO")
public record TaskDto(
        @Schema(description = "Идентификатор урока, к которому относится задание", example = "1")
        Long id,
        @Schema(description = "Задание к уроку", example = "Сколько уровней буферного кэша есть в PostgreSQL?")
        String description
) {
}
