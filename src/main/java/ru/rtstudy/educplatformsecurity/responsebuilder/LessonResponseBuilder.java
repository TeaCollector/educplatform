package ru.rtstudy.educplatformsecurity.responsebuilder;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.rtstudy.educplatformsecurity.dto.mapper.impl.LessonMapper;
import ru.rtstudy.educplatformsecurity.dto.request.LessonDtoRequest;
import ru.rtstudy.educplatformsecurity.dto.response.LessonDtoResponse;
import ru.rtstudy.educplatformsecurity.model.Lesson;
import ru.rtstudy.educplatformsecurity.service.LessonService;

@Service
@RequiredArgsConstructor
public class LessonResponseBuilder {

    private final LessonService lessonService;
    private final LessonMapper mapper;


    public LessonDtoResponse findLessonById(Long id) {
        return lessonService.findLessonById(id);
    }

    public LessonDtoResponse createLesson(LessonDtoRequest lessonDtoRequest) {
        Lesson lesson = lessonService.createLesson(lessonDtoRequest);
        return mapper.fromEntityToResponse(lesson);
    }

    public LessonDtoResponse updateLesson(LessonDtoRequest lessonDtoRequest, Long lessonId) {
        return mapper.toDto(lessonService.updateLesson(lessonDtoRequest, lessonId));
    }

    public HttpStatus deleteLesson(Long id) {
        lessonService.deleteLesson(id);
        return HttpStatus.NO_CONTENT;
    }
}
