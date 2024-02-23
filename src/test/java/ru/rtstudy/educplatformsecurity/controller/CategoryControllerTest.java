package ru.rtstudy.educplatformsecurity.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import ru.rtstudy.educplatformsecurity.responsebuilder.CategoryResponseBuilder;
import ru.rtstudy.educplatformsecurity.service.CategoryService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureWebMvc
@WithMockUser(roles = "STUDENT")
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryResponseBuilder categoryResponseBuilder;

    @Test
    @DisplayName("My first test api")
    void firstTestApi() throws Exception {
//        when(categoryResponseBuilder.getAllCategories()).thenReturn(ResponseEntity.ok(List.of("BACKEND", "FRONTEND")));
//
//        this.mockMvc.perform(get("api/v1/category")).andDo(print()).andExpect(status().isOk());
    }

}