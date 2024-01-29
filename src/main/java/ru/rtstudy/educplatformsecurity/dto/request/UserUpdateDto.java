package ru.rtstudy.educplatformsecurity.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import ru.rtstudy.educplatformsecurity.model.constant.Role;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "User update DTO")
public record UserUpdateDto(@Schema(description = "Имя пользователя", example = "Иван")
                              String firstName,
                              @Schema(description = "Фамилия пользователя", example = "Иванов")
                              String lastName) {
}
