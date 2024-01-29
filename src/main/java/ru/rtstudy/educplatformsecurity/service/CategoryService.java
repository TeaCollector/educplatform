package ru.rtstudy.educplatformsecurity.service;

import ru.rtstudy.educplatformsecurity.dto.response.CourseShortDescriptionDto;

import java.util.List;

public interface CategoryService {

    List<String> getAllCategories();

    List<CourseShortDescriptionDto> getCoursesByCategory(Long id);
}
