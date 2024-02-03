package ru.rtstudy.educplatformsecurity.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rtstudy.educplatformsecurity.responsebuilder.DifficultResponseBuilder;
import ru.rtstudy.educplatformsecurity.dto.response.CourseShortDescriptionDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Difficulty Controller", description = "Difficulty Controller API")
@RequestMapping("api/v1/difficult")
public class DifficultController {

    private final DifficultResponseBuilder responseBuilder;

    @Operation(summary = "Получить список всех курсов по идентификатору сложности сложности")
    @GetMapping("{id}")
    public ResponseEntity<List<CourseShortDescriptionDto>> getCourseByDifficult(@PathVariable(name = "id")
                                                                                @Parameter(name = "id", description = "Идентификатор уровня сложности") Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBuilder.getCoursesByDifficultId(id));
    }
}
