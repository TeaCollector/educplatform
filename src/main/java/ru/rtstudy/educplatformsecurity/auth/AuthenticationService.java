package ru.rtstudy.educplatformsecurity.auth;

import ru.rtstudy.educplatformsecurity.dto.request.SignInRequest;
import ru.rtstudy.educplatformsecurity.dto.request.SignUpRequest;
import ru.rtstudy.educplatformsecurity.dto.response.SignUpDto;
import ru.rtstudy.educplatformsecurity.dto.response.TokenDto;

public interface AuthenticationService {

    SignUpDto signUp(SignUpRequest request);

    TokenDto signIn(SignInRequest request);

    boolean hasCredentialToDelete(String fileName);

    boolean isAuthor(Long userId);
}
