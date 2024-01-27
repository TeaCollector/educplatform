package ru.rtstudy.educplatformsecurity.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AllArgsConstructor
@Schema(description = "Response to Sign Up DTO")
public class SignUpDto {

    @Schema(description = "Электронная почта зарегистрированного пользователя", example = "newuser@testmail.com")
    private String email;
    @Schema(description = "Имя зарегистрированного пользователя", example = "Пример")
    private String firstName;
    @Schema(description = "Фамилия зарегистрированного пользователя", example = "Примерочный")
    private String lastName;
    @Schema(description = "Роль зарегистрированного пользователя", example = "ROLE_STUDENT")
    private String role;
}
