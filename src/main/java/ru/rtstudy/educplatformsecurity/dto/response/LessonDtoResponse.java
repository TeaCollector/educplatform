package ru.rtstudy.educplatformsecurity.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
@Schema(description = "Lesson With Full Description DTO")
public record LessonDtoResponse(

        @Schema(description = "Идентификатор урока", example = "1")
        Long lessonId,
        @Schema(description = "Название урока", example = "Буферный кэш")
        String title,
        @Schema(description = "Название файла с материалам к уроку", example = "21c8bb28f626d28ffcf.mp4")
        String fileName,
        @Schema(description = "Описание урока", example = "На этом уроке вы узнаете что такое буферный кэш, его особенности и как его можно настраивать.")
        String description,
        @Schema(description = "Название курса, к которому относится данный урок", example = "PostgreSQL для профи")
        String courseName
) {
}
