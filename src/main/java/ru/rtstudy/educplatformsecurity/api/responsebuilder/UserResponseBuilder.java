package ru.rtstudy.educplatformsecurity.api.responsebuilder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rtstudy.educplatformsecurity.dto.mapper.impl.UserMapper;
import ru.rtstudy.educplatformsecurity.dto.request.UserUpdateDto;
import ru.rtstudy.educplatformsecurity.dto.response.UserDtoResponse;
import ru.rtstudy.educplatformsecurity.service.UserService;

@Service
@RequiredArgsConstructor
public class UserResponseBuilder {

    private final UserService userService;
    private final UserMapper mapper;

    public UserDtoResponse findUser() {
        return mapper.toDto(userService.findUser());
    }

    public UserUpdateDto updateUser(UserUpdateDto userUpdateDto) {
        return userService.updateUser(userUpdateDto);
    }
}
