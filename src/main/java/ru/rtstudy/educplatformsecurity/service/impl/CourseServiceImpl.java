package ru.rtstudy.educplatformsecurity.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rtstudy.educplatformsecurity.dto.response.CourseLongDescriptionDto;
import ru.rtstudy.educplatformsecurity.dto.response.CourseShortDescriptionDto;
import ru.rtstudy.educplatformsecurity.exception.CategoryNotExistsException;
import ru.rtstudy.educplatformsecurity.exception.CourseNotFoundException;
import ru.rtstudy.educplatformsecurity.exception.DifficultNotExistsException;
import ru.rtstudy.educplatformsecurity.repository.CourseRepository;
import ru.rtstudy.educplatformsecurity.service.CourseService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

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
}
