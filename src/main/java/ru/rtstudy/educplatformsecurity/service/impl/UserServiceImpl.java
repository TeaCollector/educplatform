package ru.rtstudy.educplatformsecurity.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.rtstudy.educplatformsecurity.exception.UserNotFoundException;
import ru.rtstudy.educplatformsecurity.model.constant.Role;
import ru.rtstudy.educplatformsecurity.repository.UserRepository;
import ru.rtstudy.educplatformsecurity.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

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

}
