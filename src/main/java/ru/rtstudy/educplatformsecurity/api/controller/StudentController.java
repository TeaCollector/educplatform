package ru.rtstudy.educplatformsecurity.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.rtstudy.educplatformsecurity.api.StudentApi;
import ru.rtstudy.educplatformsecurity.api.responsebuilder.StudentResponseBuilder;
import ru.rtstudy.educplatformsecurity.dto.ChangeStudentAnswerDto;
import ru.rtstudy.educplatformsecurity.dto.request.StudentAnswerDto;
import ru.rtstudy.educplatformsecurity.dto.response.AllStudentAnswers;
import ru.rtstudy.educplatformsecurity.service.GradeService;
import ru.rtstudy.educplatformsecurity.service.UserCourseService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StudentController implements StudentApi {

    private final StudentResponseBuilder responseBuilder;

    @Override
    public ResponseEntity<HttpStatus> enterOnCourse(Long courseId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseBuilder.enterOnCourse(courseId));
    }

    @Override
    public ResponseEntity<HttpStatus> finishCourse(Long courseId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseBuilder.finishCourse(courseId));
    }

    @Override
    public ResponseEntity<StudentAnswerDto> sendAnswer(StudentAnswerDto studentAnswerDto) {
        return ResponseEntity
                .status(HttpStatus.valueOf(201))
                .body(responseBuilder.sendAnswer(studentAnswerDto));
    }

    @Override
    public ResponseEntity<List<AllStudentAnswers>> receiveAllStudentsAnswer() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBuilder.findAllStudentAnswer());
    }

    @Override
    public ResponseEntity<List<AllStudentAnswers>> receiveAllStudentsAnswerForCourse(Long courseId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBuilder.findAllStudentsAnswerForCourse(courseId));

    }

    @Override
    public ResponseEntity<ChangeStudentAnswerDto> changeAnswer(Long id, ChangeStudentAnswerDto studentsAnswerDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBuilder.changeAnswer(id, studentsAnswerDto));
    }

    @Override
    public ResponseEntity<HttpStatus> upgradeToMentor(Long courseId) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseBuilder.upgradeToMentor(courseId));
    }
}