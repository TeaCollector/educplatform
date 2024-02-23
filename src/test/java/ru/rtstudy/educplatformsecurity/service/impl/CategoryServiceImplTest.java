package ru.rtstudy.educplatformsecurity.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.rtstudy.educplatformsecurity.exception.entity.CategoryNotExistsException;
import ru.rtstudy.educplatformsecurity.model.Category;
import ru.rtstudy.educplatformsecurity.repository.CategoryRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
//        when(categoryRepository.getCategoryByName("BACKEND")).thenReturn(Category.builder().title("BACKEND").build());
//        when(categoryRepository.getCategoryByName("DATA SCIENCE")).thenThrow(CategoryNotExistsException.class);
//        when(categoryRepository.getCourseByCategoryId(1L)).thenReturn(any());
//        when(categoryRepository.getCourseByCategoryId(4L)).thenThrow(CategoryNotExistsException.class);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllCategories() {
//        categoryRepository.findAll();
//        verify(categoryRepository).findAll();
    }

    @Test
    void getCoursesByCategory() {
        categoryRepository.getCategoryByName("DATA SCIENCE");
        verify(categoryRepository).getCategoryByName("DATA SCIENCE");
    }

    @Test
    void getCategoryByName() {
    }
}