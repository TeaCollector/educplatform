package ru.rtstudy.educplatformsecurity.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.rtstudy.educplatformsecurity.api.CategoryApi;
import ru.rtstudy.educplatformsecurity.dto.response.CourseShortDescriptionDto;
import ru.rtstudy.educplatformsecurity.service.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController implements CategoryApi {

    private final CategoryService categoryService;

    @Override
    public ResponseEntity<List<String>> getAllAvailableCategories() {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(categoryService.getAllCategories());
    }

    @Override
    public ResponseEntity<List<CourseShortDescriptionDto>> getCoursesByCategoryId(Long id) {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(categoryService.getCoursesByCategory(id));
    }
}
