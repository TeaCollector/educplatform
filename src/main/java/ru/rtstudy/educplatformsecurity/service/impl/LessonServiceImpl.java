package ru.rtstudy.educplatformsecurity.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rtstudy.educplatformsecurity.dto.request.LessonDtoRequest;
import ru.rtstudy.educplatformsecurity.dto.response.LessonDtoResponse;
import ru.rtstudy.educplatformsecurity.exception.CourseNotFoundException;
import ru.rtstudy.educplatformsecurity.exception.LessonNotFoundException;
import ru.rtstudy.educplatformsecurity.model.Course;
import ru.rtstudy.educplatformsecurity.model.Lesson;
import ru.rtstudy.educplatformsecurity.repository.CourseRepository;
import ru.rtstudy.educplatformsecurity.repository.LessonRepository;
import ru.rtstudy.educplatformsecurity.service.LessonService;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    @Override
    public LessonDtoResponse findLessonById(Long id) {
        return lessonRepository.getLessonById(id)
                .orElseThrow(() -> new LessonNotFoundException("Lesson was not found."));
    }

    @Override
    public void deleteLesson(Long id) {
        lessonRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Lesson updateLesson(LessonDtoRequest lessonDtoRequest, Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new LessonNotFoundException("Lesson not found."));

        lesson.setTitle(lessonDtoRequest.title());
        lesson.setDescription(lessonDtoRequest.description());
        lesson.setFileName(lessonDtoRequest.fileName());

        if (!lessonDtoRequest.courseName().equals(lesson.getCourse().getTitle())) {
            Course course = courseRepository.findByTitle(lessonDtoRequest.courseName())
                    .orElseThrow(() -> new CourseNotFoundException("Course not found."));
            lesson.setCourse(course);
        }
        return lesson;
    }

    @Override
    @Transactional
    public Lesson createLesson(LessonDtoRequest lessonDtoRequest) {
        Course course = courseRepository.findByTitle(lessonDtoRequest.courseName())
                .orElseThrow(() -> new CourseNotFoundException("Course not found."));

        Lesson lesson = Lesson.builder()
                .title(lessonDtoRequest.title())
                .description(lessonDtoRequest.description())
                .fileName(lessonDtoRequest.fileName())
                .course(course)
                .build();
        lessonRepository.save(lesson);
        return lesson;
    }
}
