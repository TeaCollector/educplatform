package ru.rtstudy.educplatformsecurity.auth;

import ru.rtstudy.educplatformsecurity.dto.request.SignInRequest;
import ru.rtstudy.educplatformsecurity.dto.request.SignUpRequest;
import ru.rtstudy.educplatformsecurity.dto.response.TokenDto;
import ru.rtstudy.educplatformsecurity.dto.response.UserDtoResponse;

public interface AuthenticationService {

    UserDtoResponse signUp(SignUpRequest request);

    TokenDto signIn(SignInRequest request);

    boolean hasCredentialToDelete(String fileName);

    boolean isAuthor(Long userId);
}
