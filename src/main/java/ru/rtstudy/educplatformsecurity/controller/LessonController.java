package ru.rtstudy.educplatformsecurity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.rtstudy.educplatformsecurity.dto.request.LessonDtoRequest;
import ru.rtstudy.educplatformsecurity.dto.response.LessonDto;
import ru.rtstudy.educplatformsecurity.model.Lesson;
import ru.rtstudy.educplatformsecurity.service.LessonService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/lessons")
public class LessonController {

    private final LessonService lessonService;

    @GetMapping("{id}")
    public ResponseEntity<LessonDto> getLessonById(@PathVariable(name = "id") Long id) {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(lessonService.findLessonById(id));
    }

    @PostMapping
    public ResponseEntity<LessonDto> createLesson(@RequestBody LessonDto lessonDto) {
        lessonService.createLesson(lessonDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(lessonDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatusCode> deleteLesson(@PathVariable(name = "id") Long id) {
        lessonService.deleteLesson(id);
        return ResponseEntity
                .ok(HttpStatusCode.valueOf(204));
    }
}