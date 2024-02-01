package ru.rtstudy.educplatformsecurity.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.rtstudy.educplatformsecurity.api.DifficultApi;
import ru.rtstudy.educplatformsecurity.api.responsebuilder.DifficultResponseBuilder;
import ru.rtstudy.educplatformsecurity.dto.response.CourseShortDescriptionDto;
import ru.rtstudy.educplatformsecurity.repository.DifficultRepository;
import ru.rtstudy.educplatformsecurity.service.CourseService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DifficultController implements DifficultApi {

    private final DifficultResponseBuilder responseBuilder;

    @Override
    public ResponseEntity<List<CourseShortDescriptionDto>> getCourseByDifficult(Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBuilder.getCoursesByDifficultId(id));
    }
}
