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
import ru.rtstudy.educplatformsecurity.dto.response.SignUpDto;
import ru.rtstudy.educplatformsecurity.dto.response.TokenDto;

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
                    schema = @Schema(implementation = SignUpDto.class))
            })
    )
    @PostMapping("signup")
    ResponseEntity<SignUpDto> signup(@RequestBody SignUpRequest request);

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

    @Hidden
    @Operation(summary = "Служебный метод для модуля MinIO")
    @GetMapping("check")
    Boolean forAuthentication();

    @Hidden
    @Operation(summary = "Служебный метод для модуля MinIO")
    @GetMapping("check-for-delete")
    Boolean forAuthenticationToDelete(@RequestParam(value = "file-name") String fileName);
}
