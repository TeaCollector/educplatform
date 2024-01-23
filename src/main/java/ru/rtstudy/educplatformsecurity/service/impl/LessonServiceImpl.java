package ru.rtstudy.educplatformsecurity.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.rtstudy.educplatformsecurity.dto.response.LessonDto;
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
    public LessonDto findLessonById(Long id) {
        return lessonRepository.getLessonById(id)
                .orElseThrow(() -> new LessonNotFoundException("Lesson was not found."));
    }

    @Override
    public void deleteLesson(Long id) {
        lessonRepository.deleteById(id);
    }

    @Override
    @Transactional
    public LessonDto createLesson(LessonDto lessonDto) {
        Course course = courseRepository.findByTitle(lessonDto.courseName())
                .orElseThrow(() -> new CourseNotFoundException("Course not found."));
        Lesson lesson = Lesson.builder()
                .title(lessonDto.title())
                .description(lessonDto.description())
                .fileName(lessonDto.fileName())
                .course(course)
                .build();
        lessonRepository.save(lesson);
        return lessonDto;
    }
}
