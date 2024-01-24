package ru.rtstudy.educplatformsecurity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rtstudy.educplatformsecurity.dto.mapper.impl.LessonMapper;
import ru.rtstudy.educplatformsecurity.dto.request.LessonDtoRequest;
import ru.rtstudy.educplatformsecurity.dto.response.LessonDtoResponse;
import ru.rtstudy.educplatformsecurity.model.Lesson;
import ru.rtstudy.educplatformsecurity.service.LessonService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/lessons")
public class LessonController {

    private final LessonService lessonService;
    private final LessonMapper mapper;

    @GetMapping("{id}")
    public ResponseEntity<LessonDtoResponse> getLessonById(@PathVariable(name = "id") Long id) {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(lessonService.findLessonById(id));
    }


    @PostMapping
    public ResponseEntity<LessonDtoResponse> createLesson(@RequestBody LessonDtoRequest lessonDtoRequest) {
        Lesson lesson = lessonService.createLesson(lessonDtoRequest);
        LessonDtoResponse lessonResponse = mapper.fromEntityToResponse(lesson);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(lessonResponse);
    }

    @PutMapping("{lesson_id}")
    public ResponseEntity<LessonDtoRequest> changeLesson(@PathVariable(name = "lesson_id") Long lessonId,
                                                         @RequestBody LessonDtoRequest lessonDtoRequest) {
        lessonService.updateLesson(lessonDtoRequest, lessonId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(lessonDtoRequest);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatusCode> deleteLesson(@PathVariable(name = "id") Long id) {
        lessonService.deleteLesson(id);
        return ResponseEntity
                .ok(HttpStatusCode.valueOf(204));
    }
}