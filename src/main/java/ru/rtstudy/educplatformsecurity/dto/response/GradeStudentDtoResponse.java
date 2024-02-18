package ru.rtstudy.educplatformsecurity.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "Grade with Student Info DTO")
@Builder(toBuilder = true)
public record GradeStudentDtoResponse(
        @Schema(description = "Имя пользователя, выполнившего задание", example = "Пример")
        String firstName,
        @Schema(description = "Фамилия пользователя, выполнившего задание", example = "Примерочный")
        String lastName,
        @Schema(description = "Идентификатор урока, к которому выполнено задание", example = "1")
        Long lessonId,
        @Schema(description = "Задание к уроку", example = "Сколько уровней буферного кэша есть в PostgreSQL?")
        String description,
        @Schema(description = "Оценка ментора к заданию пользователя", type = "integer", example = "3")
        Byte grade,
        @Schema(description = "Указатель на необходимость исправления своего ответа: true/false", example = "true")
        Boolean rework,
        @Schema(description = "Ответ студента на задание", example = "Существует два вида буферного кэша")
        String studentAnswer,
        @Schema(description = "Комментарий ментора к ответу студента", example = "Нет, дурачьё, подумай еще...")
        String mentorAnswer
) {
}
