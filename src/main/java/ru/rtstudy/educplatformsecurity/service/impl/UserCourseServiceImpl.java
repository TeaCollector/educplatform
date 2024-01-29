package ru.rtstudy.educplatformsecurity.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rtstudy.educplatformsecurity.exception.AlreadyMentorException;
import ru.rtstudy.educplatformsecurity.exception.CourseNotFoundException;
import ru.rtstudy.educplatformsecurity.exception.NotEnoughScoreToMentorException;
import ru.rtstudy.educplatformsecurity.model.Grade;
import ru.rtstudy.educplatformsecurity.model.User;
import ru.rtstudy.educplatformsecurity.model.UserCourse;
import ru.rtstudy.educplatformsecurity.model.constant.Role;
import ru.rtstudy.educplatformsecurity.repository.CourseRepository;
import ru.rtstudy.educplatformsecurity.repository.UserCourseRepository;
import ru.rtstudy.educplatformsecurity.service.GradeService;
import ru.rtstudy.educplatformsecurity.service.UserCourseService;
import ru.rtstudy.educplatformsecurity.service.UserService;
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
    private final UserService userService;
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
        User user = util.findUserFromContext();
        boolean courseMentor = userCourseRepository.alreadyCourseMentor(user.getId(), courseId);
        if (courseMentor) {
            throw new AlreadyMentorException("You already mentor this course");
        }
        List<Long> lessonsIds = gradeService.getAllLessonsId(courseId);
        List<Grade> gradeList = gradeService.getAllGradesByLesson(lessonsIds, user.getId());
        log.info("GRADES: {}", gradeList);
        double average = 0;
        for (Grade grade : gradeList) {
            average += grade.getGrade();
        }
        if (average / gradeList.size() >= 8) {
            userService.changeUserRole(user.getId(), Role.ROLE_MENTOR);
        } else {
            throw new NotEnoughScoreToMentorException("You don't have enough score to became a mentor.");
        }
    }
}