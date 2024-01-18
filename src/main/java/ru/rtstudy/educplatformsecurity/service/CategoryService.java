package ru.rtstudy.educplatformsecurity.service;

import ru.rtstudy.educplatformsecurity.dto.response.CourseLongDescriptionDto;

import java.util.List;

public interface CategoryService {

    List<String> getAllCategories();

    List<CourseLongDescriptionDto> getCoursesByCategory(Long id);
}
