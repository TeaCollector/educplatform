package ru.rtstudy.educplatformsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.rtstudy.educplatformsecurity.dto.response.CategoryDto;
import ru.rtstudy.educplatformsecurity.dto.response.CourseShortDescriptionDto;
import ru.rtstudy.educplatformsecurity.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("""
            select c
            from Category c
            where c.title = :title
            """)
    Category getCategoryByName(String title);
    @Query("""
            select new CourseShortDescriptionDto(c.id, c.title, c.description)
            from Course c
            where c.category.id = :categoryId
            """)
    Optional<List<CourseShortDescriptionDto>> getCourseByCategoryId(Long categoryId);
    @Query("""
            select new CategoryDto(c.id, c.title)
            from Category c
            """)
    List<CategoryDto> getAllCategories();
}


