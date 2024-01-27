package ru.rtstudy.educplatformsecurity.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "Request To Change Answer DTO")
public record ChangeStudentAnswerDto(
        @Schema(description = "Ответ студента на задание", example = "Существует два вида буферного кэша")
        String studentAnswer) {
}
