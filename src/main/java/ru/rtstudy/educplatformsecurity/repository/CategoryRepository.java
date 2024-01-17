package ru.rtstudy.educplatformsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.rtstudy.educplatformsecurity.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("""
            select c from Category c where c.title = :title
            """)
    Category getCategoryByName(String title);
}
