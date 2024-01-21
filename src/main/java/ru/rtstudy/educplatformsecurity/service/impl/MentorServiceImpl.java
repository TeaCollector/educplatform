package ru.rtstudy.educplatformsecurity.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rtstudy.educplatformsecurity.dto.mapper.impl.GradeMapper;
import ru.rtstudy.educplatformsecurity.dto.response.GradeDtoResponse;
import ru.rtstudy.educplatformsecurity.dto.response.GradeStudentDtoResponse;
import ru.rtstudy.educplatformsecurity.exception.GradeNotFoundException;
import ru.rtstudy.educplatformsecurity.exception.NoCompletedTasksException;
import ru.rtstudy.educplatformsecurity.exception.UserNotMentorException;
import ru.rtstudy.educplatformsecurity.model.Course;
import ru.rtstudy.educplatformsecurity.model.UserCourse;
import ru.rtstudy.educplatformsecurity.repository.GradeRepository;
import ru.rtstudy.educplatformsecurity.repository.UserCourseRepository;
import ru.rtstudy.educplatformsecurity.service.MentorService;
import ru.rtstudy.educplatformsecurity.util.Util;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MentorServiceImpl implements MentorService {

    private final UserCourseRepository userCourseRepository;
    private final GradeRepository gradeRepository;
    private final GradeMapper gradeMapper;
    private final Util util;

    @Override
    public List<Course> getAllCoursesForMentor() {
        var mentor = util.findUserFromContext();
        return userCourseRepository.findAllByUserAndMentorCourse(mentor, true)
                .orElseThrow(() -> new UserNotMentorException("Пользователь не является ментором ни для одного курса!"))
                .stream()
                .map(UserCourse::getCourse)
                .toList();
    }

    @Override
    public List<GradeDtoResponse> getAllAnswersForMentorCourses() {
        var mentorCourses = getAllCoursesForMentor();
        return mentorCourses.stream()
                .flatMap(course -> gradeRepository.findAllByCourseId(course.getId())
                        .orElseThrow(() -> new NoCompletedTasksException("Нет готовых для проверки заданий!"))
                        .stream())
                .map(grade -> gradeRepository.getGradeById(grade.getId())
                        .orElseThrow(() -> new GradeNotFoundException("Такой оценки не существует")))
                .toList();
    }

    @Override
    public List<GradeStudentDtoResponse> getAllAnswersForCourse(Long courseId) {
        var mentorId = util.findUserFromContext().getId();
        boolean isMentorForCourse = userCourseRepository.isMentorForCourse(mentorId, courseId);
        if (isMentorForCourse) {
           return gradeRepository.findAllByCourseId(courseId)
                   .orElseThrow(() -> new NoCompletedTasksException("Нет готовых для проверки заданий!"))
                   .stream()
                   .map(gradeMapper::toDto)
                   .toList();
        } else {
            throw new UserNotMentorException("Пользователь не является ментором для данного курса");
        }
    }

    @Override
    public List<GradeStudentDtoResponse> getAllAnswersForLesson(Long lessonId) {
        var mentorId = util.findUserFromContext().getId();
        boolean isMentorForLesson = userCourseRepository.isMentorForLesson(mentorId, lessonId);
        if (isMentorForLesson){
            return gradeRepository.findAllByLessonId(lessonId)
                    .orElseThrow(() -> new NoCompletedTasksException("Нет готовых для проверки заданий!"))
                    .stream()
                    .map(gradeMapper::toDto)
                    .toList();
        } else {
            throw new UserNotMentorException("Пользователь не является ментором для урока данного курса");
        }

    }
}
