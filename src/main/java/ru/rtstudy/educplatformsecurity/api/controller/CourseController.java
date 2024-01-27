package ru.rtstudy.educplatformsecurity.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.rtstudy.educplatformsecurity.api.CourseApi;
import ru.rtstudy.educplatformsecurity.dto.mapper.impl.CourseMapper;
import ru.rtstudy.educplatformsecurity.dto.request.CourseDtoRequest;
import ru.rtstudy.educplatformsecurity.dto.response.CourseLongDescriptionDto;
import ru.rtstudy.educplatformsecurity.model.Course;
import ru.rtstudy.educplatformsecurity.service.CourseService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CourseController implements CourseApi {

    private final CourseService courseService;
    private final CourseMapper mapper;

    @Override
    public ResponseEntity<CourseLongDescriptionDto> getCourse(Long id) {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(courseService.findCourseById(id));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public ResponseEntity<HttpStatus> updateCourse(Long id, CourseDtoRequest courseDtoRequest) {
        Course course = mapper.toEntity(courseDtoRequest);
        courseService.updateCourse(course, id);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public ResponseEntity<CourseDtoRequest> createCourse(CourseDtoRequest courseDtoRequest) {
        Course course = mapper.toEntity(courseDtoRequest);
        return ResponseEntity
                .status(HttpStatusCode.valueOf(201))
                .body(mapper.toDto(courseService.createCourse(course)));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public ResponseEntity<HttpStatus> deleteCourse(Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok(HttpStatus.valueOf(204));
    }
}