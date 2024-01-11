package ru.rtstudy.educplatformsecurity.auth;

import ru.rtstudy.educplatformsecurity.dto.request.SignInRequest;
import ru.rtstudy.educplatformsecurity.dto.request.SignUpRequest;
import ru.rtstudy.educplatformsecurity.dto.response.SignUpResponseDto;
import ru.rtstudy.educplatformsecurity.dto.response.TokenResponseDto;

public interface AuthenticationService {

    SignUpResponseDto signUp(SignUpRequest request);

    TokenResponseDto signIn(SignInRequest request);

}
