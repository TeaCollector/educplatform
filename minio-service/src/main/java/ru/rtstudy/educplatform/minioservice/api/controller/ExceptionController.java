package ru.rtstudy.educplatform.minioservice.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import reactor.core.publisher.Mono;
import ru.rtstudy.educplatform.minioservice.exception.*;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = {
            NotAuthenticatedException.class,
            NotAuthorException.class,
            NotLessonAuthorException.class})
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public Mono<ErrorMessage> authenticationException(RuntimeException exception, WebRequest request) {
        return Mono.just(ErrorMessage.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .description(exception.getMessage())
                .currentTime(LocalDateTime.now())
                .endpoint(request.getDescription(false))
                .build());
    }

    @ExceptionHandler(value = {
            NotSupportedExtension.class})
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Mono<ErrorMessage> extensionException(RuntimeException exception, WebRequest request) {
        return Mono.just(ErrorMessage.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .description(exception.getMessage())
                .currentTime(LocalDateTime.now())
                .endpoint(request.getDescription(false))
                .build());
    }
}
