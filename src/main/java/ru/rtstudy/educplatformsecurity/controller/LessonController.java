package ru.rtstudy.educplatformsecurity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.rtstudy.educplatformsecurity.responsebuilder.LessonResponseBuilder;
import ru.rtstudy.educplatformsecurity.dto.request.LessonDtoRequest;
import ru.rtstudy.educplatformsecurity.dto.response.LessonDtoResponse;

@RestController
@RequiredArgsConstructor
@Tag(name = "Lesson Controller", description = "Lesson Controller API")
@RequestMapping("api/v1/lessons")
public class LessonController {

    private final LessonResponseBuilder responseBuilder;

    @Operation(summary = "Получить описание урока по его идентификатору")
    @GetMapping("{lesson_id}")
    public ResponseEntity<LessonDtoResponse> getLessonById(@PathVariable(name = "lesson_id")
                                                           @Parameter(name = "lesson_id", description = "Идентификатор урока") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBuilder.findLessonById(id));
    }

    @Operation(summary = "Создать новый урок")
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    @PostMapping
    public ResponseEntity<LessonDtoResponse> createLesson(@RequestBody LessonDtoRequest lessonDtoRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseBuilder.createLesson(lessonDtoRequest));
    }

    @Operation(summary = "Изменить информацию об уроке по его идентификатору")
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    @PutMapping("{lesson_id}")
    public ResponseEntity<LessonDtoResponse> changeLesson(@PathVariable(name = "lesson_id")
                                                          @Parameter(name = "lesson_id", description = "Идентификатор урока") Long lessonId,
                                                          @RequestBody LessonDtoRequest lessonDtoRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBuilder.updateLesson(lessonDtoRequest, lessonId));
    }

    @Operation(summary = "Удалить урок по его идентификатору")
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    @DeleteMapping("{lesson_id}")
    public ResponseEntity<HttpStatus> deleteLesson(@PathVariable(name = "lesson_id")
                                                   @Parameter(name = "lesson_id", description = "Идентификатор урока") Long id) {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(responseBuilder.deleteLesson(id));
    }
}