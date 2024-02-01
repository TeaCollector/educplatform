package ru.rtstudy.educplatformsecurity.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import ru.rtstudy.educplatformsecurity.api.MentorApi;
import ru.rtstudy.educplatformsecurity.api.responsebuilder.MentorResponseBuilder;
import ru.rtstudy.educplatformsecurity.dto.request.MentorAnswerDtoRequest;
import ru.rtstudy.educplatformsecurity.dto.response.GradeDtoResponse;
import ru.rtstudy.educplatformsecurity.dto.response.GradeStudentDtoResponse;
import ru.rtstudy.educplatformsecurity.service.MentorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_MENTOR')")
public class MentorController implements MentorApi {

    private final MentorResponseBuilder responseBuilder;

    @Override
    public ResponseEntity<List<GradeDtoResponse>> getAllAnswersForMentorCourses() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBuilder.getAllAnswersForMentorCourses());
    }

    @Override
    public ResponseEntity<List<GradeStudentDtoResponse>> getAllStudentAnswersForCourse(Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBuilder.getAllAnswersForCourse(id));
    }

    @Override
    public ResponseEntity<List<GradeStudentDtoResponse>> getAllStudentAnswersForLesson(Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBuilder.getAllAnswersForLesson(id));
    }

    @Override
    public ResponseEntity<MentorAnswerDtoRequest> reviewStudentAnswer(Long id, MentorAnswerDtoRequest mentorAnswerDtoRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBuilder.reviewStudentAnswer(id, mentorAnswerDtoRequest));
    }

    @Override
    public ResponseEntity<MentorAnswerDtoRequest> updateMentorAnswer(Long id, MentorAnswerDtoRequest mentorAnswerDtoRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBuilder.updateMentorAnswer(id, mentorAnswerDtoRequest));
    }

    @Override
    public ResponseEntity<HttpStatus> upgradeMentorToAuthor() {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseBuilder.upgradeToAuthor());
    }
}
