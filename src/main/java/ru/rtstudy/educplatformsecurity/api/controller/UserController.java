package ru.rtstudy.educplatformsecurity.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.rtstudy.educplatformsecurity.api.UserApi;
import ru.rtstudy.educplatformsecurity.dto.mapper.impl.UserMapper;
import ru.rtstudy.educplatformsecurity.dto.request.UserUpdateDto;
import ru.rtstudy.educplatformsecurity.dto.response.UserDtoResponse;
import ru.rtstudy.educplatformsecurity.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;
    private final UserMapper mapper;

    @Override
    public ResponseEntity<UserDtoResponse> getUserInfo() {
        UserDtoResponse userDtoResponse = mapper.toDto(userService.findUserById());
        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(userDtoResponse);
    }

    @Override
    public ResponseEntity<UserUpdateDto> updateUser(UserUpdateDto userUpdateDto) {
        userService.updateUser(userUpdateDto);
        return ResponseEntity
                .status(HttpStatusCode.valueOf(202))
                .body(userUpdateDto);

    }
}
