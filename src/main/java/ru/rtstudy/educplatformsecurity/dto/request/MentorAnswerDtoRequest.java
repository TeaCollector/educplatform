package ru.rtstudy.educplatformsecurity.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "Mentor Review DTO")
public record MentorAnswerDtoRequest(
        @Schema(description = "Оценка ментора к заданию пользователя", type = "integer", example = "3")
        Byte grade,
        @Schema(description = "Указатель на необходимость исправления своего ответа: true/false", example = "true")
        Boolean rework,
        @Schema(description = "Комментарий ментора к ответу студента", example = "Нет, дурачьё, подумай еще...")
        String mentorAnswer
) {
}
