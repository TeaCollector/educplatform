package ru.rtstudy.educplatformsecurity.service;

import ru.rtstudy.educplatformsecurity.dto.response.LessonDto;

public interface LessonService {

    LessonDto findLessonById(Long id);

    void deleteLesson(Long id);

    LessonDto createLesson(LessonDto lessonDto);
}
