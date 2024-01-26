package ru.rtstudy.educplatformsecurity.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "Request to Sign Up DTO")
public class SignUpRequest {

    @Schema(description = "Полное имя нового пользователя", example = "Тест")
    private String firstName;
    @Schema(description = "Полная фамилия нового пользователя", example = "Тестовый")
    private String lastName;
    @Schema(description = "Электронная почта нового пользователя", example = "newuser@testmail.com")
    private String email;
    @Schema(description = "Пароль нового пользователя", example = "secretpassword")
    private String password;
}
