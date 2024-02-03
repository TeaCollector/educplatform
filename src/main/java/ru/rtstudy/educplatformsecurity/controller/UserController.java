package ru.rtstudy.educplatformsecurity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rtstudy.educplatformsecurity.responsebuilder.UserResponseBuilder;
import ru.rtstudy.educplatformsecurity.dto.request.UserUpdateDto;
import ru.rtstudy.educplatformsecurity.dto.response.UserDtoResponse;

@RestController
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "User Controller API")
@RequestMapping("api/v1/users")
public class UserController {

    private final UserResponseBuilder responseBuilder;

    @Operation(summary = "Получить информацию о пользователе")
    @GetMapping
    public ResponseEntity<UserDtoResponse> getUserInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBuilder.findUser());
    }

    @Operation(summary = "Изменить информацию о пользователе")
    @PutMapping
    public ResponseEntity<UserUpdateDto> updateUser(@RequestBody UserUpdateDto userUpdateDto) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(responseBuilder.updateUser(userUpdateDto));

    }
}
