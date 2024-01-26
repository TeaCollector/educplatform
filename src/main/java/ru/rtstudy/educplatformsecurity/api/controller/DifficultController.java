package ru.rtstudy.educplatformsecurity.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.rtstudy.educplatformsecurity.api.DifficultApi;
import ru.rtstudy.educplatformsecurity.dto.response.CourseShortDescriptionDto;
import ru.rtstudy.educplatformsecurity.service.CourseService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DifficultController implements DifficultApi {

    private final CourseService courseService;

    @Override
    public ResponseEntity<List<CourseShortDescriptionDto>> getCourseByDifficult(Long id) {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(courseService.getCoursesByDifficultId(id));
    }
}
