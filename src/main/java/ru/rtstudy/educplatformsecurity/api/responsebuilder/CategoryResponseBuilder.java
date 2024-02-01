package ru.rtstudy.educplatformsecurity.api.responsebuilder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.rtstudy.educplatformsecurity.dto.response.CourseShortDescriptionDto;
import ru.rtstudy.educplatformsecurity.service.CategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor

public class CategoryResponseBuilder {

    private final CategoryService categoryService;

    public List<String> getAllCategories() {
        return categoryService.getAllCategories();
    }

    public List<CourseShortDescriptionDto> getCourseByCategory(Long id) {
        return categoryService.getCoursesByCategory(id);
    }
}
