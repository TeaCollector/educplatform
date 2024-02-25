package ru.rtstudy.educplatformsecurity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Schema(description = "Task DTO to update")
@Builder
public record UpdateTaskDto(

        @Schema(description = "Задание к уроку", example = "Сколько уровней буферного кэша есть в PostgreSQL?")
        @NotBlank(message = "Description must be filled")
        String description

) {}
