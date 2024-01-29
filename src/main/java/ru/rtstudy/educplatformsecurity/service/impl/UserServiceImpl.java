package ru.rtstudy.educplatformsecurity.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rtstudy.educplatformsecurity.dto.request.UserUpdateDto;
import ru.rtstudy.educplatformsecurity.exception.UserNotFoundException;
import ru.rtstudy.educplatformsecurity.model.User;
import ru.rtstudy.educplatformsecurity.model.constant.Role;
import ru.rtstudy.educplatformsecurity.repository.UserRepository;
import ru.rtstudy.educplatformsecurity.service.UserService;
import ru.rtstudy.educplatformsecurity.util.Util;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Util util;

    @Override
    public UserDetailsService userDetailsService() {
        return email -> userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public void changeUserRole(Long id, Role newRole) {
        var user = userRepository.getReferenceById(id);
        user.setRole(newRole);
        userRepository.saveAndFlush(user);
    }

    @Override
    public User findUserById() {
        Long userId = util.findUserFromContext().getId();
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
    }

    @Override
    public UserUpdateDto updateUser(UserUpdateDto userUpdateDto) {
        User user = util.findUserFromContext();
        user.setFirstName(userUpdateDto.firstName());
        user.setLastName(userUpdateDto.lastName());
        userRepository.saveAndFlush(user);
        return userUpdateDto;
    }
}