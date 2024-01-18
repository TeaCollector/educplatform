package ru.rtstudy.educplatformsecurity.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import ru.rtstudy.educplatformsecurity.util.Util;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final DifficultRepository difficultRepository;
    private final Util util;

    @Override
    public List<CourseShortDescriptionDto> getCoursesByDifficultId(Long id) {
        return courseRepository.findCourseByDifficultId(id)
                .orElseThrow(() -> new DifficultNotExistsException("Difficult not exists."));
    }

    @Override
    public List<CourseShortDescriptionDto> findCourseByCategoryId(Long id) {
        return courseRepository.findCourseByCategoryId(id)
                .orElseThrow(() -> new CategoryNotExistsException("Category was not found."));
    }

    @Override
    public CourseLongDescriptionDto findCourseById(Long id) {
        return courseRepository.findCourseById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course was not found"));
    }

    @Override
    @Transactional
    public Course createCourse(Course course) {

        Category category = categoryRepository.getCategoryByName(course.getCategory().getTitle());
        course.setCategory(category);

        Difficult difficult = difficultRepository.getDifficultByDifficultName(course.getDifficult().getDifficultLevel());
        course.setDifficult(difficult);

        User user = util.findUserFromContext();
        course.setCourseAuthor(user);

        return courseRepository.save(course);
    }

    @Override
    @Transactional
    public void updateCourse(Course course, Long id) {

        Course toUpdate = courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException("Course was not found."));

        toUpdate.setDuration(course.getDuration());
        toUpdate.setTitle(course.getTitle());
        toUpdate.setDescription(course.getDescription());

        if (!course.getCategory().getTitle().equals(toUpdate.getCategory().getTitle())) {
            toUpdate.setCategory(categoryRepository.getCategoryByName(course.getCategory().getTitle()));
        }
        if (!course.getDifficult().getDifficultLevel().name().equals(toUpdate.getDifficult().getDifficultLevel().name())) {
            toUpdate.setDifficult(difficultRepository.getDifficultByDifficultName(course.getDifficult().getDifficultLevel()));
        }
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
