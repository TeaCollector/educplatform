package ru.rtstudy.educplatformsecurity.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.rtstudy.educplatformsecurity.dto.request.UserUpdateDto;
import ru.rtstudy.educplatformsecurity.dto.response.CourseShortDescriptionDto;
import ru.rtstudy.educplatformsecurity.model.User;
import ru.rtstudy.educplatformsecurity.model.constant.Role;

import java.util.List;

public interface UserService {

    UserDetailsService userDetailsService();

    void changeUserRole(Long id, Role role);

    User findUser();

    UserUpdateDto updateUser(UserUpdateDto userUpdateDto);

    List<CourseShortDescriptionDto> getAllStartedCourse();
}
