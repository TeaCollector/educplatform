package ru.rtstudy.educplatformsecurity.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rtstudy.educplatformsecurity.dto.response.CourseLongDescriptionDto;
import ru.rtstudy.educplatformsecurity.dto.response.CourseShortDescriptionDto;
import ru.rtstudy.educplatformsecurity.exception.CategoryNotExistsException;
import ru.rtstudy.educplatformsecurity.exception.CourseNotFoundException;
import ru.rtstudy.educplatformsecurity.model.Category;
import ru.rtstudy.educplatformsecurity.repository.CategoryRepository;
import ru.rtstudy.educplatformsecurity.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<String> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(Category::getTitle)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public List<CourseShortDescriptionDto> getCoursesByCategory(Long categoryId) {
        categoryRepository.findById(categoryId)
                .orElseThrow(()-> new CategoryNotExistsException("Category not found!"));
        return categoryRepository.getCourseByCategoryId(categoryId)
                .map(courses -> {
                    if (courses.isEmpty()) {
                        return null;
                    }
                    return courses;
                })
                .orElseThrow(() -> new CourseNotFoundException("Course not found."));
    }
}
