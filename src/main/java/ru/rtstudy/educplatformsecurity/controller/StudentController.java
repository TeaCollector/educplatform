package ru.rtstudy.educplatformsecurity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rtstudy.educplatformsecurity.service.UserCourseService;

@RestController
@RequestMapping("api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final UserCourseService userCourseService;

    @PostMapping("start/{course_id}")
    public ResponseEntity<HttpStatus> enterOnCourse(@PathVariable(name = "course_id") Long id) {
        userCourseService.enterOnCourse(id);
        return ResponseEntity
                .ok(HttpStatus.valueOf(201));
    }

}
