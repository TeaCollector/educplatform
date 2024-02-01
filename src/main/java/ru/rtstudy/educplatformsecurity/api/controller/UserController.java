package ru.rtstudy.educplatformsecurity.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.rtstudy.educplatformsecurity.api.UserApi;
import ru.rtstudy.educplatformsecurity.api.responsebuilder.UserResponseBuilder;
import ru.rtstudy.educplatformsecurity.dto.request.UserUpdateDto;
import ru.rtstudy.educplatformsecurity.dto.response.UserDtoResponse;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserResponseBuilder responseBuilder;

    @Override
    public ResponseEntity<UserDtoResponse> getUserInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBuilder.findUser());
    }

    @Override
    public ResponseEntity<UserUpdateDto> updateUser(UserUpdateDto userUpdateDto) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(responseBuilder.updateUser(userUpdateDto));

    }
}
