package ru.rtstudy.educplatformsecurity.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.rtstudy.educplatformsecurity.dto.request.MentorAnswerDtoRequest;
import ru.rtstudy.educplatformsecurity.dto.response.GradeDtoResponse;
import ru.rtstudy.educplatformsecurity.dto.response.GradeStudentDtoResponse;
import ru.rtstudy.educplatformsecurity.service.MentorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mentors")
@PreAuthorize("hasRole('ROLE_MENTOR')")

public class MentorController {

    private final MentorService mentorService;

    @GetMapping
    ResponseEntity<List<GradeDtoResponse>> getAllAnswersForMentorCourses() {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(mentorService.getAllAnswersForMentorCourses());
    }

    @GetMapping("/course/{course_id}")
    ResponseEntity<List<GradeStudentDtoResponse>> getAllStudentAnswersForCourse(@PathVariable(name = "course_id") Long id) {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(mentorService.getAllAnswersForCourse(id));
    }

    @GetMapping("/lessons/{lesson_id}")
    ResponseEntity<List<GradeStudentDtoResponse>> getAllStudentAnswersForLesson(@PathVariable(name = "lesson_id") Long id) {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(mentorService.getAllAnswersForLesson(id));
    }

    @PutMapping("/grades/{grade_id}")
    ResponseEntity<MentorAnswerDtoRequest> reviewStudentAnswer(@PathVariable(name = "grade_id") Long id,
                                                               @RequestBody MentorAnswerDtoRequest mentorAnswerDtoRequest) {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(201))
                .body(mentorService.reviewStudentAnswer(id, mentorAnswerDtoRequest));
    }

    @PatchMapping("/grades/{grade_id}")
    ResponseEntity<MentorAnswerDtoRequest> updateMentorAnswer(@PathVariable(name = "grade_id") Long id,
                                                              @RequestBody MentorAnswerDtoRequest mentorAnswerDtoRequest) {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(204))
                .body(mentorService.updateMentorAnswer(id, mentorAnswerDtoRequest));
    }

    @PostMapping("/upgrade-to-author")
    ResponseEntity<HttpStatus> updateMentorAnswer() {
        mentorService.upgradeToAuthor();
        return ResponseEntity
                .ok(HttpStatus.valueOf(200));
    }
}
