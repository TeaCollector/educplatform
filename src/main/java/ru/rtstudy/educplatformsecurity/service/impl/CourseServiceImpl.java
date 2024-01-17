package ru.rtstudy.educplatformsecurity.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rtstudy.educplatformsecurity.auth.JwtService;
import ru.rtstudy.educplatformsecurity.dto.response.CourseLongDescriptionDto;
import ru.rtstudy.educplatformsecurity.dto.response.CourseShortDescriptionDto;
import ru.rtstudy.educplatformsecurity.exception.CategoryNotExistsException;
import ru.rtstudy.educplatformsecurity.exception.CourseNotFoundException;
import ru.rtstudy.educplatformsecurity.exception.DifficultNotExistsException;
import ru.rtstudy.educplatformsecurity.model.Category;
import ru.rtstudy.educplatformsecurity.model.Course;
import ru.rtstudy.educplatformsecurity.model.Difficult;
import ru.rtstudy.educplatformsecurity.model.User;
import ru.rtstudy.educplatformsecurity.repository.CategoryRepository;
import ru.rtstudy.educplatformsecurity.repository.CourseRepository;
import ru.rtstudy.educplatformsecurity.repository.DifficultRepository;
import ru.rtstudy.educplatformsecurity.service.CourseService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final DifficultRepository difficultRepository;
    private final JwtService jwtService;

    @Override
    public List<CourseShortDescriptionDto> getCoursesByDifficultId(Long id) {
        return courseRepository.findCourseByDifficultId(id)
                .orElseThrow(() -> new DifficultNotExistsException("Difficult not exists."));
    }

    @Override
    public CourseLongDescriptionDto findCourseById(Long id) {
        return courseRepository.findCourseById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course was not found"));
    }

    @Override
    public CourseShortDescriptionDto findCourseByCategoryId(Long id) {
        return courseRepository.findCourseByCategoryId(id)
                .orElseThrow(() -> new CategoryNotExistsException("Category was not found."));
    }

    @Override
    @Transactional
    public Course createCourse(Course course) {

        Category category = categoryRepository.getCategoryByName(course.getCategory().getTitle());
//        log.info("CATEGORY: {} AND COURSES: {}", category.getId(), category.getTitle());

        course.setCategory(category);

        Difficult difficult = difficultRepository.getDifficultByDifficultName(course.getDifficult().getDifficult());

        course.setDifficult(difficult);
//        log.info("DIFFICULT: {}", difficult);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentPrincipalName = (User) authentication.getPrincipal();

//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

//        log.info("USER: {}", currentPrincipalName);

//        log.info("COURSE: {}", course);
        return courseRepository.save(course);
    }
}
