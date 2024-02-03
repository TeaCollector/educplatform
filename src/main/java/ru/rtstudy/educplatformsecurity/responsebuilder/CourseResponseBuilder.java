package ru.rtstudy.educplatformsecurity.responsebuilder;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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


    public CourseLongDescriptionDto findCourseById(Long id) {
        return courseService.findCourseById(id);
    }

    public CourseDtoRequest updateCourse(Long id, CourseDtoRequest courseDtoRequest) {
        Course course = mapper.toEntity(courseDtoRequest);
        CourseDtoRequest courseDto = mapper.toDto(courseService.updateCourse(course, id));
        return courseDto;
    }

    public List<LessonDtoShortDescription> getAllLessonByCourseId(Long courseId) {
        return courseService.getAllLessonByCourseId(courseId);
    }

    public CourseDtoRequest createCourse(CourseDtoRequest dtoRequest) {
        Course course = mapper.toEntity(dtoRequest);
        return mapper.toDto(courseService.createCourse(course));
    }

    public HttpStatus deleteCourse(Long id) {
        courseService.deleteCourse(id);
        return HttpStatus.NO_CONTENT;
    }
}
