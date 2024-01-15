package ru.rtstudy.educplatformsecurity.service;

import ru.rtstudy.educplatformsecurity.dto.response.LessonDto;
import ru.rtstudy.educplatformsecurity.model.Lesson;

public interface LessonService {

    LessonDto findLessonById(Long id);
}
