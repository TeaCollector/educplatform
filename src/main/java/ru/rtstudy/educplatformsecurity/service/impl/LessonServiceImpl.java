package ru.rtstudy.educplatformsecurity.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rtstudy.educplatformsecurity.dto.request.LessonDtoRequest;
import ru.rtstudy.educplatformsecurity.dto.response.LessonDtoResponse;
import ru.rtstudy.educplatformsecurity.exception.exist.CourseNotFoundException;
import ru.rtstudy.educplatformsecurity.exception.user.EnterOnCourseException;
import ru.rtstudy.educplatformsecurity.exception.exist.LessonNotFoundException;
import ru.rtstudy.educplatformsecurity.exception.user.NotCourseAuthorException;
import ru.rtstudy.educplatformsecurity.model.Course;
import ru.rtstudy.educplatformsecurity.model.Lesson;
import ru.rtstudy.educplatformsecurity.model.User;
import ru.rtstudy.educplatformsecurity.repository.CourseRepository;
import ru.rtstudy.educplatformsecurity.repository.LessonRepository;
import ru.rtstudy.educplatformsecurity.service.CourseService;
import ru.rtstudy.educplatformsecurity.service.LessonService;
import ru.rtstudy.educplatformsecurity.util.Util;

@Slf4j
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
        User user = util.findUserFromContext();
        log.info("{} find lesson by id: {}", user.getEmail(), lessonId);
        boolean whetherOnCourse = lessonRepository.whetherOnCourse(lessonId, user.getId());
        if (whetherOnCourse) {
            return lessonRepository.getLessonById(lessonId)
                    .orElseThrow(() -> {
                        log.error("Lessson not found: {}", lessonId, new LessonNotFoundException("Lesson was not found."));
                        return new LessonNotFoundException("Lesson was not found.");
                    });
        } else {
            log.error("{} trying to get lesson: {}", user.getEmail(), lessonId, new EnterOnCourseException("You must enter on course."));
            throw new EnterOnCourseException("You must enter on course.");
        }
    }

    @Override
    public Lesson updateLesson(LessonDtoRequest lessonDtoRequest, Long lessonId) {
        log.info("{} update lesson: {}, {}", util.findUserFromContext().getEmail(), lessonId, lessonDtoRequest);
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> {
                    log.error("Lesson not found: {}", lessonId);
                    return new LessonNotFoundException("Lesson not found.");
                });
        boolean isAuthor = courseService.isAuthor(lesson.getCourse().getId());

        if (isAuthor) {
            lesson.setTitle(lessonDtoRequest.title());
            lesson.setDescription(lessonDtoRequest.description());
            lesson.setFileName(lessonDtoRequest.fileName());
            if (!lessonDtoRequest.courseName().equals(lesson.getCourse().getTitle())) {
                Course course = courseRepository.findByTitle(lessonDtoRequest.courseName())
                        .orElseThrow(() -> {
                            log.info("Course not found by title: {}", lessonDtoRequest.title());
                            return new CourseNotFoundException("Course not found.");
                        });
                lesson.setCourse(course);
            }
        } else {
            log.error("{} not course author: {}", util.findUserFromContext().getEmail(), lessonDtoRequest.title(), new NotCourseAuthorException("You are not course author."));
            throw new NotCourseAuthorException("You are not course author.");
        }
        return lesson;
    }

    @Override
    public Lesson createLesson(LessonDtoRequest lessonDtoRequest) {
        log.info("{} create lesson: {}", util.findUserFromContext().getEmail(), lessonDtoRequest);
        Course course = courseRepository.findByTitle(lessonDtoRequest.courseName())
                .orElseThrow(() -> {
                    log.error("Course not found by title: {}", lessonDtoRequest.courseName(), new CourseNotFoundException("Course not found."));
                    return new CourseNotFoundException("Course not found.");
                });

        Lesson lesson = Lesson.builder()
                .title(lessonDtoRequest.title())
                .description(lessonDtoRequest.description())
                .fileName(lessonDtoRequest.fileName())
                .course(course)
                .build();
        log.info("Lesson to create: {}", lesson);
        lessonRepository.save(lesson);
        return lesson;
    }

    @Override
    public void deleteFile(String fileName) {
        log.info("{} delete file: {}", util.findUserFromContext().getEmail(), fileName);
        lessonRepository.deleteFile(fileName);
        log.debug("{} delete file: {}", util.findUserFromContext().getEmail(), fileName);
    }

    @Override
    public void deleteLesson(Long lessonId) {
        log.info("{} delete lesson: {}", util.findUserFromContext().getEmail(), lessonId);
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> {
                    log.error("Lesson was not found: {}", lessonId, new LessonNotFoundException("Lesson not found."));
                    return new LessonNotFoundException("Lesson not found.");
                });
        boolean isAuthor = courseService.isAuthor(lesson.getCourse().getId());

        if (isAuthor) {
            lessonRepository.deleteById(lessonId);
        } else {
            log.error("{} is not course author", util.findUserFromContext().getEmail(), new NotCourseAuthorException("You are not course author."));
            throw new NotCourseAuthorException("You are not course author.");
        }
    }
}
