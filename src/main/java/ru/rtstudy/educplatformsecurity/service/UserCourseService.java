package ru.rtstudy.educplatformsecurity.service;

public interface UserCourseService {

    void enterOnCourse(Long id);

    void upgradeToMentor(Long id);

    boolean onCourse(Long course, Long userId);

    void finishCourse(Long userId, Long courseId);
}

