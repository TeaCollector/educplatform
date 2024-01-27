package ru.rtstudy.educplatformsecurity.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "Student Answer For Task DTO")
public record StudentAnswerDto(
        @Schema(description = "Идентификатор урока, к которому выполнено задание", example = "1")
        Long lessonId,
        @Schema(description = "Ответ студента на задание", example = "Существует два вида буферного кэша")
        String studentAnswer) {
}
