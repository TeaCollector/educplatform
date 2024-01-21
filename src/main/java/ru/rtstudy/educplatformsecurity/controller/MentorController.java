package ru.rtstudy.educplatformsecurity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rtstudy.educplatformsecurity.dto.response.GradeDtoResponse;
import ru.rtstudy.educplatformsecurity.dto.response.GradeStudentDtoResponse;
import ru.rtstudy.educplatformsecurity.service.MentorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/api/v1/mentors")
public class MentorController {

    private final MentorService mentorService;

    @GetMapping
    ResponseEntity<List<GradeDtoResponse>> getAllAnswersForMentorCourses(){
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
}
