package ru.rtstudy.educplatformsecurity.responsebuilder;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.rtstudy.educplatformsecurity.dto.response.CourseShortDescriptionDto;
import ru.rtstudy.educplatformsecurity.service.CourseService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DifficultResponseBuilder {

    private final CourseService courseService;

    public ResponseEntity<List<CourseShortDescriptionDto>> getCoursesByDifficultId(Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(courseService.getCoursesByDifficultId(id));
    }
}
