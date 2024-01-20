package ru.rtstudy.educplatformsecurity.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rtstudy.educplatformsecurity.exception.CourseNotFoundException;
import ru.rtstudy.educplatformsecurity.exception.NotEnoughScoreToMentorException;
import ru.rtstudy.educplatformsecurity.model.Grade;
import ru.rtstudy.educplatformsecurity.model.UserCourse;
import ru.rtstudy.educplatformsecurity.repository.CourseRepository;
import ru.rtstudy.educplatformsecurity.repository.UserCourseRepository;
import ru.rtstudy.educplatformsecurity.service.GradeService;
import ru.rtstudy.educplatformsecurity.service.UserCourseService;
import ru.rtstudy.educplatformsecurity.util.Util;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserCourseServiceImpl implements UserCourseService {

    private final CourseRepository courseRepository;
    private final UserCourseRepository userCourseRepository;
    private final GradeService gradeService;
    private final Util util;

    @Override
    public void enterOnCourse(Long id) {
        UserCourse userCourse = UserCourse.builder()
                .user(util.findUserFromContext())
                .beginCourse(LocalDateTime.now())
                .course(courseRepository
                        .findById(id)
                        .orElseThrow(() -> new CourseNotFoundException("Course was not found.")))
                .build();
        log.info("UserCourse: {}", userCourse);
        userCourseRepository.save(userCourse);
    }

    @Override
    public void upgradeToMentor(Long courseId) {
        Long userId = util.findUserFromContext().getId();
        List<Long> lessonsIds = gradeService.getAllLessonsId(courseId);
        List<Grade> gradeList = gradeService.getAllGradesFromCourse(lessonsIds, userId);
        double average = 0;
        for (Grade grade: gradeList) {
            average += grade.getGrade();
        }
        if (average / gradeList.size() >= 8) {
            userCourseRepository.upgradeToMentor(userId, courseId);
        } else {
            throw new NotEnoughScoreToMentorException("You don't have enough score to became a mentor.");
        }
    }
}
