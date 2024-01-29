package ru.rtstudy.educplatformsecurity.auth.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.rtstudy.educplatformsecurity.auth.AuthenticationService;
import ru.rtstudy.educplatformsecurity.auth.JwtService;
import ru.rtstudy.educplatformsecurity.dto.request.SignInRequest;
import ru.rtstudy.educplatformsecurity.dto.request.SignUpRequest;
import ru.rtstudy.educplatformsecurity.dto.response.TokenDto;
import ru.rtstudy.educplatformsecurity.dto.response.UserDtoResponse;
import ru.rtstudy.educplatformsecurity.exception.UserNotFoundException;
import ru.rtstudy.educplatformsecurity.model.constant.Role;
import ru.rtstudy.educplatformsecurity.model.User;
import ru.rtstudy.educplatformsecurity.repository.UserRepository;
import ru.rtstudy.educplatformsecurity.util.Util;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final Util util;

    @Override
    public UserDtoResponse signUp(SignUpRequest request) {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_STUDENT)
                .build();
        userRepository.save(user);

        return UserDtoResponse.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(user.getRole())
                .build();
    }

    @Override
    public TokenDto signIn(SignInRequest request) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()))
                .getPrincipal();
        User user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        String jwt = jwtService.generateToken(user);
        return TokenDto.builder()
                .token(jwt)
                .build();
    }

    @Override
    public boolean hasCredentialToDelete(String fileName) {
        Long userId = util.findUserFromContext().getId();
        boolean b = userRepository.hasCredential(fileName, userId);
        log.info("FILE NAME IS: {} AND HAS CREDENTIAL: {}", fileName, b);
        return b;
    }

    @Override
    public boolean isAuthor(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found."))
                .getRole()
                .equals(Role.ROLE_AUTHOR);
    }
}
