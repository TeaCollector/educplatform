package ru.rtstudy.educplatformsecurity.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rtstudy.educplatformsecurity.dto.response.CourseLongDescriptionDto;
import ru.rtstudy.educplatformsecurity.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<String>> getAllAvailableCategories() {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<CourseLongDescriptionDto>> getCoursesByCategoryId(@PathVariable(name = "id") Long id) {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(200))
                .body(categoryService.getCoursesByCategory(id));
    }
}
