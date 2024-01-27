package ru.rtstudy.educplatformsecurity.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import ru.rtstudy.educplatformsecurity.exception.*;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = {
            CourseNotFoundException.class,
            LessonNotFoundException.class,
            AnswersNotFoundException.class,
            TaskNotFoundException.class,
            UserNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> resourceNotFoundException(RuntimeException ex, WebRequest request) {
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
            ResolveAllTaskException.class
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
}