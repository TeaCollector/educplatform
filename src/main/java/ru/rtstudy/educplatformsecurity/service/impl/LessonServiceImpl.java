package ru.rtstudy.educplatformsecurity.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rtstudy.educplatformsecurity.dto.request.LessonDtoRequest;
import ru.rtstudy.educplatformsecurity.dto.response.LessonDtoResponse;
import ru.rtstudy.educplatformsecurity.exception.CourseNotFoundException;
import ru.rtstudy.educplatformsecurity.exception.EnterOnCourseException;
import ru.rtstudy.educplatformsecurity.exception.LessonNotFoundException;
import ru.rtstudy.educplatformsecurity.exception.NotCourseAuthorException;
import ru.rtstudy.educplatformsecurity.model.Course;
import ru.rtstudy.educplatformsecurity.model.Lesson;
import ru.rtstudy.educplatformsecurity.repository.CourseRepository;
import ru.rtstudy.educplatformsecurity.repository.LessonRepository;
import ru.rtstudy.educplatformsecurity.service.CourseService;
import ru.rtstudy.educplatformsecurity.service.LessonService;
import ru.rtstudy.educplatformsecurity.util.Util;

@Service
@RequiredArgsConstructor
@Transactional
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final CourseService courseService;
    private final Util util;

    @Override
    public LessonDtoResponse findLessonById(Long lessonId) {
        Long userId = util.findUserFromContext().getId();
        boolean whetherOnCourse = lessonRepository.whetherOnCourse(lessonId, userId);
        if(whetherOnCourse) {
            return lessonRepository.getLessonById(lessonId)
                    .orElseThrow(() -> new LessonNotFoundException("Lesson was not found."));
        } else {
            throw new EnterOnCourseException("You must enter on course.");
        }
    }

    @Override
    public Lesson updateLesson(LessonDtoRequest lessonDtoRequest, Long lessonId) {

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new LessonNotFoundException("Lesson not found."));
        boolean isAuthor = courseService.isAuthor(lesson.getCourse().getId());

        if (isAuthor) {
            lesson.setTitle(lessonDtoRequest.title());
            lesson.setDescription(lessonDtoRequest.description());
            lesson.setFileName(lessonDtoRequest.fileName());
            if (!lessonDtoRequest.courseName().equals(lesson.getCourse().getTitle())) {
                Course course = courseRepository.findByTitle(lessonDtoRequest.courseName())
                        .orElseThrow(() -> new CourseNotFoundException("Course not found."));
                lesson.setCourse(course);
            }
        } else {
            throw new NotCourseAuthorException("You are not course author.");
        }
        return lesson;
    }

    @Override
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

    @Override
    public void deleteFile(String fileName) {
        lessonRepository.deleteFile(fileName);
    }

    @Override
    public void deleteLesson(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new LessonNotFoundException("Lesson not found."));
        boolean isAuthor = courseService.isAuthor(lesson.getCourse().getId());

        if (isAuthor) {
            lessonRepository.deleteById(lessonId);
        } else {
            throw new NotCourseAuthorException("You are not course author.");
        }
    }
}
