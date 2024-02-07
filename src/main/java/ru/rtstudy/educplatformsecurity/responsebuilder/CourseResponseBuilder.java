package ru.rtstudy.educplatformsecurity.responsebuilder;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.rtstudy.educplatformsecurity.dto.mapper.impl.CourseMapper;
import ru.rtstudy.educplatformsecurity.dto.request.CourseDtoRequest;
import ru.rtstudy.educplatformsecurity.dto.response.CourseLongDescriptionDto;
import ru.rtstudy.educplatformsecurity.dto.response.LessonDtoShortDescription;
import ru.rtstudy.educplatformsecurity.model.Course;
import ru.rtstudy.educplatformsecurity.service.CourseService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseResponseBuilder {

    private final CourseService courseService;
    private final CourseMapper mapper;


    public ResponseEntity<CourseLongDescriptionDto> findCourseById(Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(courseService.findCourseById(id));
    }

    public ResponseEntity<CourseDtoRequest> updateCourse(Long id, CourseDtoRequest courseDtoRequest) {
        Course course = mapper.toEntity(courseDtoRequest);
        CourseDtoRequest courseDto = mapper.toDto(courseService.updateCourse(course, id));
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(courseDto);
    }

    public ResponseEntity<List<LessonDtoShortDescription>> getAllLessonByCourseId(Long courseId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(courseService.getAllLessonByCourseId(courseId));
    }

    public ResponseEntity<CourseDtoRequest> createCourse(CourseDtoRequest dtoRequest) {
        Course course = mapper.toEntity(dtoRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(mapper.toDto(courseService.createCourse(course)));
    }

    public ResponseEntity<HttpStatus> deleteCourse(Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(HttpStatus.NO_CONTENT);
    }
}
