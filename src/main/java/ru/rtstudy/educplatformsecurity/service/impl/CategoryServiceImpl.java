package ru.rtstudy.educplatformsecurity.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rtstudy.educplatformsecurity.dto.response.CourseLongDescriptionDto;
import ru.rtstudy.educplatformsecurity.exception.CategoryNotExistsException;
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

    @Override
    public List<CourseLongDescriptionDto> getCoursesByCategory(Long categoryId) {
        return categoryRepository.getCourseByCategoryId(categoryId)
                .orElseThrow(()-> new CategoryNotExistsException("Category not found!"));
    }
}
