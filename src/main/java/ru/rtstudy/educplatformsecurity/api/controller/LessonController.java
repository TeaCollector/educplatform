package ru.rtstudy.educplatformsecurity.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.rtstudy.educplatformsecurity.api.LessonApi;
import ru.rtstudy.educplatformsecurity.dto.mapper.impl.LessonMapper;
import ru.rtstudy.educplatformsecurity.dto.request.LessonDtoRequest;
import ru.rtstudy.educplatformsecurity.dto.response.LessonDtoResponse;
import ru.rtstudy.educplatformsecurity.model.Lesson;
import ru.rtstudy.educplatformsecurity.service.LessonService;

@RestController
@RequiredArgsConstructor
public class LessonController implements LessonApi {

    private final LessonService lessonService;
    private final LessonMapper mapper;

    @Override
    public ResponseEntity<LessonDtoResponse> getLessonById(Long id) {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(lessonService.findLessonById(id));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public ResponseEntity<LessonDtoResponse> createLesson(LessonDtoRequest lessonDtoRequest) {
        Lesson lesson = lessonService.createLesson(lessonDtoRequest);
        LessonDtoResponse lessonResponse = mapper.fromEntityToResponse(lesson);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(lessonResponse);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public ResponseEntity<LessonDtoRequest> changeLesson(Long lessonId, LessonDtoRequest lessonDtoRequest) {
        lessonService.updateLesson(lessonDtoRequest, lessonId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(lessonDtoRequest);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public ResponseEntity<HttpStatusCode> deleteLesson(Long id) {
        lessonService.deleteLesson(id);
        return ResponseEntity
                .ok(HttpStatusCode.valueOf(204));
    }
}