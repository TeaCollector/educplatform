package ru.rtstudy.educplatformsecurity.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to Sign In DTO")
public class SignInRequest {
    @Schema(description = "Электронная почта пользователя", example = "newuser@testmail.com")
    private String email;
    @Schema(description = "Пароль пользователя", example = "secretpassword")
    private String password;
}
