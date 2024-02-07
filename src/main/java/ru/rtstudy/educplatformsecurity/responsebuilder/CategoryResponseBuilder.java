package ru.rtstudy.educplatformsecurity.responsebuilder;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.rtstudy.educplatformsecurity.dto.response.CourseShortDescriptionDto;
import ru.rtstudy.educplatformsecurity.service.CategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor

public class CategoryResponseBuilder {

    private final CategoryService categoryService;

    public ResponseEntity<List<String>> getAllCategories() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.getAllCategories());
    }

    public ResponseEntity<List<CourseShortDescriptionDto>> getCourseByCategory(Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.getCoursesByCategory(id));
    }
}
