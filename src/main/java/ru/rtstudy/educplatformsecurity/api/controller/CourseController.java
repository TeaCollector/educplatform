package ru.rtstudy.educplatformsecurity.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import ru.rtstudy.educplatformsecurity.api.CourseApi;
import ru.rtstudy.educplatformsecurity.api.responsebuilder.CourseResponseBuilder;
import ru.rtstudy.educplatformsecurity.dto.request.CourseDtoRequest;
import ru.rtstudy.educplatformsecurity.dto.response.CourseLongDescriptionDto;
import ru.rtstudy.educplatformsecurity.dto.response.LessonDtoShortDescription;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CourseController implements CourseApi {

    private final CourseResponseBuilder responseBuilder;

    @Override
    public ResponseEntity<CourseLongDescriptionDto> getCourse(Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBuilder.findCourseById(id));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public ResponseEntity<CourseDtoRequest> updateCourse(Long id, CourseDtoRequest courseDtoRequest) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(responseBuilder.updateCourse(id, courseDtoRequest));
    }

    @Override
    public ResponseEntity<List<LessonDtoShortDescription>> getAllLessonByCourseId(Long courseId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBuilder.getAllLessonByCourseId(courseId));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public ResponseEntity<CourseDtoRequest> createCourse(CourseDtoRequest courseDtoRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBuilder.createCourse(courseDtoRequest));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_AUTHOR')")
    public ResponseEntity<HttpStatus> deleteCourse(Long id) {
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(responseBuilder.deleteCourse(id));
    }
}
