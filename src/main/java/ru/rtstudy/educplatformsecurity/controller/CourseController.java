package ru.rtstudy.educplatformsecurity.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.rtstudy.educplatformsecurity.dto.mapper.impl.CourseMapper;
import ru.rtstudy.educplatformsecurity.dto.request.CourseDtoRequest;
import ru.rtstudy.educplatformsecurity.dto.response.CourseLongDescriptionDto;
import ru.rtstudy.educplatformsecurity.model.Course;
import ru.rtstudy.educplatformsecurity.service.CourseService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/courses")
@Slf4j
public class CourseController {

    private final CourseService courseService;
    private final CourseMapper mapper;

    @GetMapping("{id}")
    public ResponseEntity<CourseLongDescriptionDto> getCourse(@PathVariable(name = "id") Long id) {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(courseService.findCourseById(id));
    }

    @PostMapping
    public ResponseEntity<CourseDtoRequest> createCourse(@RequestBody CourseDtoRequest courseDtoRequest) {
        Course course = mapper.toEntity(courseDtoRequest);

        log.info("COURSE: {}; COURSEDTO: {}", course, courseDtoRequest);

        return ResponseEntity
                .status(HttpStatusCode.valueOf(201))
                .body(mapper.toDto(courseService.createCourse(course)));
    }
}
