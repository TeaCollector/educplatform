package ru.rtstudy.educplatformsecurity.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
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
        return ResponseEntity
                .status(HttpStatusCode.valueOf(201))
                .body(mapper.toDto(courseService.createCourse(course)));
    }

    @PutMapping("{id}")
    public ResponseEntity<HttpStatus> updateCourse(@PathVariable(name = "id") Long id,
                                                   @RequestBody CourseDtoRequest courseDtoRequest) {
        Course course = mapper.toEntity(courseDtoRequest);
        courseService.updateCourse(course, id);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable(name = "id") Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok(HttpStatus.valueOf(204));
    }
}
