package ru.rtstudy.educplatformsecurity.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rtstudy.educplatformsecurity.responsebuilder.AuthenticationResponseBuilder;
import ru.rtstudy.educplatformsecurity.dto.request.SignInRequest;
import ru.rtstudy.educplatformsecurity.dto.request.SignUpRequest;
import ru.rtstudy.educplatformsecurity.dto.response.TokenDto;
import ru.rtstudy.educplatformsecurity.dto.response.UserDtoResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication Controller", description = "Authentication Controller API")
public class AuthenticationController {

    private final AuthenticationResponseBuilder responseBuilder;

    @Operation(summary = "Регистрация пользователя")
    @PostMapping("signup")
    public ResponseEntity<UserDtoResponse> signup(@RequestBody SignUpRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseBuilder.signup(request));
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("signin")
    public ResponseEntity<TokenDto> signIn(@RequestBody SignInRequest request) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(responseBuilder.signIn(request));
    }

    @Hidden
    @Operation(summary = "Служебный метод для модуля MinIO")
    @GetMapping("check")
    public Boolean verification() {
        return responseBuilder.verificationRequest();
    }

    @Hidden
    @Operation(summary = "Служебный метод для модуля MinIO")
    @GetMapping("check-for-delete")
    public Boolean deleteVerification(@RequestParam(value = "file-name") String fileName) {
        return responseBuilder.verificationToDelete(fileName);
    }
}
