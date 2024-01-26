package ru.rtstudy.educplatformsecurity.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "Request To Create/Update Lesson DTO")
public record LessonDtoRequest(
        @Schema(description = "Название урока", example = "Буферный кэш")
        String title,
        @Schema(description = "Название файла с материалам к уроку", example = "lesson1.mp4") //TODO сделать корректный пример
        String fileName,
        @Schema(description = "Описание урока", example = "На этом уроке вы узнаете что такое буферный кэш, его особенности и как его можно настраивать.")
        String description,
        @Schema(description = "Название курса, к которому относится данный урок", example = "PostgreSQL для профи")
        String courseName
) {
}
