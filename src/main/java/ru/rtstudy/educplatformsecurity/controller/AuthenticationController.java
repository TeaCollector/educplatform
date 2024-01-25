package ru.rtstudy.educplatformsecurity.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rtstudy.educplatformsecurity.auth.AuthenticationService;
import ru.rtstudy.educplatformsecurity.dto.request.SignInRequest;
import ru.rtstudy.educplatformsecurity.dto.request.SignUpRequest;
import ru.rtstudy.educplatformsecurity.dto.response.SignUpDto;
import ru.rtstudy.educplatformsecurity.dto.response.TokenDto;
import ru.rtstudy.educplatformsecurity.model.User;
import ru.rtstudy.educplatformsecurity.service.LessonService;
import ru.rtstudy.educplatformsecurity.util.Util;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final LessonService lessonService;
    private final Util util;

    @PostMapping("signup")
    public ResponseEntity<SignUpDto> signup(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signUp(request));
    }

    @PostMapping("signin")
    public ResponseEntity<TokenDto> signIn(@RequestBody SignInRequest request) {
        return ResponseEntity.ok(authenticationService.signIn(request));
    }

    @GetMapping("check")
    public Boolean forAuthentication() {
        User user = util.findUserFromContext();
        return authenticationService.isAuthor(user.getId());
    }

    @GetMapping("check-for-delete")
    public Boolean forAuthenticationToDelete(@RequestParam(value = "file-name") String fileName) {
        User user = util.findUserFromContext();
        log.info("User name: {}", user.getEmail());
        if (authenticationService.isAuthor(user.getId()) &&
            authenticationService.hasCredentialToDelete(fileName)) {
            lessonService.deleteFile(fileName);
            return true;
        } else {
            return false;
        }
    }
}
