package ru.rtstudy.educplatformsecurity.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "Task DTO")
public record TaskDtoResponse(

        @Schema(description = "Идентификатор задания", example = "1")
        Long taskId,

        @Schema(description = "Задание к уроку", example = "Сколько уровней буферного кэша есть в PostgreSQL?")
        String description
) {
}
