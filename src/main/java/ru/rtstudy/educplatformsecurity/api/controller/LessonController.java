package ru.rtstudy.educplatformsecurity.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import ru.rtstudy.educplatformsecurity.api.LessonApi;
import ru.rtstudy.educplatformsecurity.api.responsebuilder.LessonResponseBuilder;
import ru.rtstudy.educplatformsecurity.dto.mapper.impl.LessonMapper;
import ru.rtstudy.educplatformsecurity.dto.request.LessonDtoRequest;
import ru.rtstudy.educplatformsecurity.dto.response.LessonDtoResponse;
import ru.rtstudy.educplatformsecurity.model.Lesson;
import ru.rtstudy.educplatformsecurity.service.LessonService;

@RestController
@RequiredArgsConstructor
public class LessonController implements LessonApi {

    private final LessonResponseBuilder responseBuilder;

    @Override
    public ResponseEntity<LessonDtoResponse> getLessonById(Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBuilder.findLessonById(id));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public ResponseEntity<LessonDtoResponse> createLesson(LessonDtoRequest lessonDtoRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseBuilder.createLesson(lessonDtoRequest));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public ResponseEntity<LessonDtoResponse> changeLesson(Long lessonId, LessonDtoRequest lessonDtoRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBuilder.updateLesson(lessonDtoRequest, lessonId));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public ResponseEntity<HttpStatus> deleteLesson(Long id) {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(responseBuilder.deleteLesson(id));
    }
}