package ru.rtstudy.educplatformsecurity.service;

import ru.rtstudy.educplatformsecurity.dto.response.CourseShortDescriptionDto;

import java.util.List;

public interface UserCourseService {

    void enterOnCourse(Long id);

    void upgradeToMentor(Long id);

    boolean onCourse(Long course, Long userId);

    void finishCourse(Long userId, Long courseId);

    List<CourseShortDescriptionDto> getAllStartedCourse(Long userId);
}

