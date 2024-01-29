package ru.rtstudy.educplatformsecurity.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "User update DTO")
public record UserUpdateDto(@Schema(description = "Имя пользователя", example = "Иван")
                            String firstName,
                            @Schema(description = "Фамилия пользователя", example = "Иванов")
                            String lastName) {
}
