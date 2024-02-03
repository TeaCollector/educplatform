package ru.rtstudy.educplatformsecurity.api;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rtstudy.educplatformsecurity.dto.request.SignInRequest;
import ru.rtstudy.educplatformsecurity.dto.request.SignUpRequest;
import ru.rtstudy.educplatformsecurity.dto.response.TokenDto;
import ru.rtstudy.educplatformsecurity.dto.response.UserDtoResponse;

//TODO: Зачем тут интерфейс, если анатации можно навесить непосредсвтенно на функции
@Tag(name = "Authentication Controller", description = "Authentication Controller API")
@RequestMapping("/api/v1/auth")
public interface AuthenticationApi {
    @Operation(summary = "Регистрация пользователя")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "200",
            description = "Успешная регистрация пользователя",
            content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserDtoResponse.class))
            })
    )
    @PostMapping("signup")
    ResponseEntity<UserDtoResponse> signup(@RequestBody SignUpRequest request);

    @Operation(summary = "Авторизация пользователя")
    @ApiResponses(value =
    @ApiResponse(
            responseCode = "200",
            description = "Успешная авторизация пользователя",
            content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TokenDto.class))
            })
    )
    @PostMapping("signin")
    ResponseEntity<TokenDto> signIn(@RequestBody SignInRequest request);

    // TODO: Переименовать функцию forAuthentication()
    @Hidden
    @Operation(summary = "Служебный метод для модуля MinIO")
    @GetMapping("check")
    Boolean verification();

    // TODO: Переименовать функцию forAuthenticationToDelete()
    @Hidden
    @Operation(summary = "Служебный метод для модуля MinIO")
    @GetMapping("check-for-delete")
    Boolean deleteVerification(@RequestParam(value = "file-name") String fileName);
}
