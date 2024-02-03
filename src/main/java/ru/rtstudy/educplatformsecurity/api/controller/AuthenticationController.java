package ru.rtstudy.educplatformsecurity.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.rtstudy.educplatformsecurity.api.AuthenticationApi;
import ru.rtstudy.educplatformsecurity.api.responsebuilder.AuthenticationResponseBuilder;
import ru.rtstudy.educplatformsecurity.dto.request.SignInRequest;
import ru.rtstudy.educplatformsecurity.dto.request.SignUpRequest;
import ru.rtstudy.educplatformsecurity.dto.response.TokenDto;
import ru.rtstudy.educplatformsecurity.dto.response.UserDtoResponse;
import ru.rtstudy.educplatformsecurity.model.User;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationApi {

    private final AuthenticationResponseBuilder responseBuilder;

    @Override
    public ResponseEntity<UserDtoResponse> signup(SignUpRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseBuilder.signup(request));
    }

    @Override
    public ResponseEntity<TokenDto> signIn(SignInRequest request) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(responseBuilder.signIn(request));
    }
    @Override
    public Boolean verification() {
        return responseBuilder.verificationRequest();
    }
    @Override
    public Boolean deleteVerification(String fileName) {
        return responseBuilder.verificationToDelete(fileName);
    }
}
