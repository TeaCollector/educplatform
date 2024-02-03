package ru.rtstudy.educplatformsecurity.service;

import ru.rtstudy.educplatformsecurity.dto.response.CourseLongDescriptionDto;
import ru.rtstudy.educplatformsecurity.dto.response.CourseShortDescriptionDto;
import ru.rtstudy.educplatformsecurity.dto.response.LessonDtoShortDescription;
import ru.rtstudy.educplatformsecurity.model.Course;

import java.util.List;

public interface CourseService {

    CourseLongDescriptionDto findCourseById(Long id);

    List<CourseShortDescriptionDto> getCoursesByDifficultId(Long id);

    Course createCourse(Course course);

    Course updateCourse(Course course, Long id);

    void deleteCourse(Long id);

    boolean isAuthor(Long courseId);

    List<LessonDtoShortDescription> getAllLessonByCourseId(Long courseId);
}
