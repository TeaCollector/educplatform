package ru.rtstudy.educplatformsecurity.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "Request To Change Answer DTO")
public record ChangeStudentAnswerDto(
        @Schema(description = "Ответ студента на задание", example = "Существует два вида буферного кэша")
        @NotBlank(message = "Answer must be filled")
        String studentAnswer) {
}
