package ru.rtstudy.educplatformsecurity.api.responsebuilder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rtstudy.educplatformsecurity.auth.AuthenticationService;
import ru.rtstudy.educplatformsecurity.dto.request.SignInRequest;
import ru.rtstudy.educplatformsecurity.dto.request.SignUpRequest;
import ru.rtstudy.educplatformsecurity.dto.response.TokenDto;
import ru.rtstudy.educplatformsecurity.dto.response.UserDtoResponse;
import ru.rtstudy.educplatformsecurity.model.User;
import ru.rtstudy.educplatformsecurity.service.CategoryService;
import ru.rtstudy.educplatformsecurity.service.LessonService;
import ru.rtstudy.educplatformsecurity.util.Util;

@Service
@RequiredArgsConstructor
public class AuthenticationResponseBuilder {

    private final AuthenticationService authenticationService;
    private final LessonService lessonService;
    private final CategoryService categoryService;
    private final Util util;

    public UserDtoResponse signup(SignUpRequest signUpRequest) {
        return authenticationService.signUp(signUpRequest);
    }

    public TokenDto signIn(SignInRequest signInRequest) {
        return authenticationService.signIn(signInRequest);
    }

    public Boolean verificationRequest() {
        User user = util.findUserFromContext();
        return authenticationService.isAuthor(user.getId());

    }

    public Boolean verificationToDelete(String fileName) {
        User user = util.findUserFromContext();
        if (authenticationService.isAuthor(user.getId()) &&
            authenticationService.hasCredentialToDelete(fileName)) {
            lessonService.deleteFile(fileName);
            return true;
        } else {
            return false;
        }
    }
}
