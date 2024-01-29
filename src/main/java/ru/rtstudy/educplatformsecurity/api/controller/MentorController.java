package ru.rtstudy.educplatformsecurity.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import ru.rtstudy.educplatformsecurity.api.MentorApi;
import ru.rtstudy.educplatformsecurity.dto.request.MentorAnswerDtoRequest;
import ru.rtstudy.educplatformsecurity.dto.response.GradeDtoResponse;
import ru.rtstudy.educplatformsecurity.dto.response.GradeStudentDtoResponse;
import ru.rtstudy.educplatformsecurity.service.MentorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_MENTOR')")
public class MentorController implements MentorApi {

    private final MentorService mentorService;

    @Override
    public ResponseEntity<List<GradeDtoResponse>> getAllAnswersForMentorCourses() {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(mentorService.getAllAnswersForMentorCourses());
    }

    @Override
    public ResponseEntity<List<GradeStudentDtoResponse>> getAllStudentAnswersForCourse(Long id) {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(mentorService.getAllAnswersForCourse(id));
    }

    @Override
    public ResponseEntity<List<GradeStudentDtoResponse>> getAllStudentAnswersForLesson(Long id) {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(mentorService.getAllAnswersForLesson(id));
    }

    @Override
    public ResponseEntity<MentorAnswerDtoRequest> reviewStudentAnswer(Long id, MentorAnswerDtoRequest mentorAnswerDtoRequest) {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(201))
                .body(mentorService.reviewStudentAnswer(id, mentorAnswerDtoRequest));
    }

    @Override
    public ResponseEntity<MentorAnswerDtoRequest> updateMentorAnswer(Long id, MentorAnswerDtoRequest mentorAnswerDtoRequest) {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(mentorService.updateMentorAnswer(id, mentorAnswerDtoRequest));
    }

    @Override
    public ResponseEntity<HttpStatus> upgradeMentorToAuthor() {
        mentorService.upgradeToAuthor();
        return ResponseEntity
                .ok(HttpStatus.valueOf(201));
    }
}
