package ru.rtstudy.educplatformsecurity.service;

import ru.rtstudy.educplatformsecurity.dto.response.CourseLongDescriptionDto;
import ru.rtstudy.educplatformsecurity.dto.response.CourseShortDescriptionDto;
import ru.rtstudy.educplatformsecurity.model.Course;

import java.util.List;

public interface CourseService {

    CourseLongDescriptionDto findCourseById(Long id);

    List<CourseShortDescriptionDto> getCoursesByDifficultId(Long id);

    List<CourseShortDescriptionDto> findCourseByCategoryId(Long id);

    Course createCourse(Course course);

    void updateCourse(Course course, Long id);

    void deleteCourse(Long id);
}
