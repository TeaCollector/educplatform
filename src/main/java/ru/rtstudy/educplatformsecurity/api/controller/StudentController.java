package ru.rtstudy.educplatformsecurity.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rtstudy.educplatformsecurity.api.StudentApi;
import ru.rtstudy.educplatformsecurity.dto.ChangeStudentAnswerDto;
import ru.rtstudy.educplatformsecurity.dto.request.StudentAnswerDto;
import ru.rtstudy.educplatformsecurity.dto.response.AllStudentAnswers;
import ru.rtstudy.educplatformsecurity.service.GradeService;
import ru.rtstudy.educplatformsecurity.service.UserCourseService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudentController implements StudentApi {

    private final UserCourseService userCourseService;
    private final GradeService gradeService;

    @Override
    public ResponseEntity<HttpStatus> enterOnCourse(Long courseId) {
        userCourseService.enterOnCourse(courseId);
        return ResponseEntity
                .ok(HttpStatus.valueOf(201));
    }

    @Override
    public ResponseEntity<HttpStatus> finishCourse(Long courseId) {
        gradeService.finishCourse(courseId);
        return ResponseEntity
                .ok(HttpStatus.valueOf(201));
    }

    @Override
    public ResponseEntity<StudentAnswerDto> sendAnswer(StudentAnswerDto studentAnswerDto) {
        gradeService.sendAnswer(studentAnswerDto);
        return ResponseEntity
                .status(HttpStatus.valueOf(201))
                .body(studentAnswerDto);
    }

    @Override
    public ResponseEntity<List<AllStudentAnswers>> receiveAllStudentsAnswer() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(gradeService.findAllStudentAnswer());
    }

    @Override
    public ResponseEntity<List<AllStudentAnswers>> receiveAllStudentsAnswerForCourse(Long courseId) {
        return ResponseEntity
                .status(HttpStatus.valueOf(200))
                .body(gradeService.findAllStudentsAnswerForCourse(courseId));

    }

    @Override
    public ResponseEntity<ChangeStudentAnswerDto> changeAnswer(Long id, ChangeStudentAnswerDto studentsAnswerDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(gradeService.changeAnswer(id, studentsAnswerDto));
    }

    @Override
    public ResponseEntity<HttpStatus> upgradeToMentor(Long courseId) {
        userCourseService.upgradeToMentor(courseId);
        return ResponseEntity
                .ok(HttpStatus.valueOf(201));
    }
}