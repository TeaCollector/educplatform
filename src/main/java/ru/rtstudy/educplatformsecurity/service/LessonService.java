package ru.rtstudy.educplatformsecurity.service;

import ru.rtstudy.educplatformsecurity.dto.request.LessonDtoRequest;
import ru.rtstudy.educplatformsecurity.dto.response.LessonDtoResponse;
import ru.rtstudy.educplatformsecurity.model.Lesson;

public interface LessonService {

    LessonDtoResponse findLessonById(Long id);

    void deleteLesson(Long id);

    Lesson updateLesson(LessonDtoRequest lessonDtoRequest, Long lessonId);

    Lesson createLesson(LessonDtoRequest lesson);

    void deleteFile(String fileName);
}
