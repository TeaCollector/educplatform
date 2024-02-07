package ru.rtstudy.educplatformsecurity.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import ru.rtstudy.educplatformsecurity.exception.ErrorMessage;
import ru.rtstudy.educplatformsecurity.exception.author.NotCourseAuthorException;
import ru.rtstudy.educplatformsecurity.exception.author.NotEnoughScoreToAuthorException;
import ru.rtstudy.educplatformsecurity.exception.entity.*;
import ru.rtstudy.educplatformsecurity.exception.mentor.MentorAnswerAlreadyExistsException;
import ru.rtstudy.educplatformsecurity.exception.mentor.NoCompletedTasksException;
import ru.rtstudy.educplatformsecurity.exception.mentor.NotEnoughScoreToMentorException;
import ru.rtstudy.educplatformsecurity.exception.student.AlreadyMentorException;
import ru.rtstudy.educplatformsecurity.exception.student.EnterOnCourseException;
import ru.rtstudy.educplatformsecurity.exception.student.ResolveAllTaskException;
import ru.rtstudy.educplatformsecurity.exception.student.UserNotMentorException;
import ru.rtstudy.educplatformsecurity.exception.user.*;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = {
            CourseNotFoundException.class,
            LessonNotFoundException.class,
            AnswersNotFoundException.class,
            TaskNotFoundException.class,
            UserNotFoundException.class,
            UserAlreadyExistsException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> resourceNotFoundException(RuntimeException ex, WebRequest request) {
        log.error("EXCEPTION CATCHED: {}", ex.getMessage());
        return ResponseEntity
                .ok(ErrorMessage.builder()
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .description(ex.getMessage())
                        .currentTime(LocalDateTime.now())
                        .endpoint(request.getDescription(false))
                        .build());
    }

    @ExceptionHandler(value = {
            CategoryNotExistsException.class,
            DifficultNotExistsException.class
    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<ErrorMessage> resourceNotExistsException(RuntimeException ex, WebRequest request) {
        return ResponseEntity
                .ok(ErrorMessage.builder()
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .description(ex.getMessage())
                        .currentTime(LocalDateTime.now())
                        .endpoint(request.getDescription(false))
                        .build());
    }

    @ExceptionHandler(value = {
            NotEnoughScoreToMentorException.class,
            ResolveAllTaskException.class,
            EnterOnCourseException.class
    })
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> studentsException(RuntimeException ex, WebRequest request) {
        return ResponseEntity
                .ok(ErrorMessage.builder()
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .description(ex.getMessage())
                        .currentTime(LocalDateTime.now())
                        .endpoint(request.getDescription(false))
                        .build());
    }

    @ExceptionHandler(value = {
            UserNotMentorException.class,
            MentorAnswerAlreadyExistsException.class,
            AlreadyMentorException.class,
            NotEnoughScoreToAuthorException.class,
            GradeNotFoundException.class,
            NoCompletedTasksException.class,
            NotCourseAuthorException.class
    })
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> mentorsException(RuntimeException ex, WebRequest request) {
        return ResponseEntity
                .ok(ErrorMessage.builder()
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .description(ex.getMessage())
                        .currentTime(LocalDateTime.now())
                        .endpoint(request.getDescription(false))
                        .build());
    }
}