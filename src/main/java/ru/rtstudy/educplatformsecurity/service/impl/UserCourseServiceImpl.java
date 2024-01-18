package ru.rtstudy.educplatformsecurity.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rtstudy.educplatformsecurity.exception.CourseNotFoundException;
import ru.rtstudy.educplatformsecurity.model.UserCourse;
import ru.rtstudy.educplatformsecurity.repository.CourseRepository;
import ru.rtstudy.educplatformsecurity.repository.UserCourseRepository;
import ru.rtstudy.educplatformsecurity.service.UserCourseService;
import ru.rtstudy.educplatformsecurity.util.Util;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCourseServiceImpl implements UserCourseService {

    private final CourseRepository courseRepository;
    private final UserCourseRepository userCourseRepository;
    private final Util util;

    @Override
    @Transactional
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
}
