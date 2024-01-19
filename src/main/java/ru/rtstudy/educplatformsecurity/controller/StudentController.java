package ru.rtstudy.educplatformsecurity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rtstudy.educplatformsecurity.dto.request.StudentAnswerDto;
import ru.rtstudy.educplatformsecurity.dto.response.StudentAnswerAtAllLesson;
import ru.rtstudy.educplatformsecurity.service.GradeService;
import ru.rtstudy.educplatformsecurity.service.UserCourseService;

import java.util.List;

@RestController
@RequestMapping("api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final UserCourseService userCourseService;
    private final GradeService gradeService;

    @PostMapping("start/{course_id}")
    public ResponseEntity<HttpStatus> enterOnCourse(@PathVariable(name = "course_id") Long id) {
        userCourseService.enterOnCourse(id);
        return ResponseEntity
                .ok(HttpStatus.valueOf(201));
    }

    @PostMapping("finish/{course_id}")
    public ResponseEntity<HttpStatus> finishCourse(@PathVariable(name = "course_id") Long id) {
        userCourseService.finishCourse(id);
        return ResponseEntity
                .ok(HttpStatus.valueOf(201));
    }

    @PostMapping
    public ResponseEntity<StudentAnswerDto> sendAnswer(@RequestBody StudentAnswerDto studentAnswerDto) {
        gradeService.sendAnswer(studentAnswerDto);
        return ResponseEntity
                .status(HttpStatus.valueOf(201))
                .body(studentAnswerDto);
    }

    @GetMapping
    public ResponseEntity<List<StudentAnswerAtAllLesson>> receiveAllStudentsAnswer() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(gradeService.findAllStudentAnswer());
    }


}
