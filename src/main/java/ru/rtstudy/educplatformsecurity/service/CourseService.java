package ru.rtstudy.educplatformsecurity.service;

import ru.rtstudy.educplatformsecurity.dto.response.CourseLongDescriptionDto;
import ru.rtstudy.educplatformsecurity.dto.response.CourseShortDescriptionDto;

import java.util.List;

public interface CourseService {

    List<CourseShortDescriptionDto> getCoursesByDifficultId(Long id);

    CourseLongDescriptionDto findCourseById(Long id);

    CourseShortDescriptionDto findCourseByCategoryId(Long id);
}
