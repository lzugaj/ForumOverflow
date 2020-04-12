package com.luv2code.forumoverflow.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.forumoverflow.domain.Category;
import com.luv2code.forumoverflow.rest.controller.CategoryController;
import com.luv2code.forumoverflow.service.CategoryService;

/**
 * Created by lzugaj on Tuesday, March 2020
 */

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryService categoryService;

    private Category firstCategory;

    private Category secondCategory;

    private List<Category> categories;

    @BeforeEach
    public void setup() {
        firstCategory = new Category();
        firstCategory.setId(1L);
        firstCategory.setName("Iot");
        firstCategory.setPosts(null);

        secondCategory = new Category();
        secondCategory.setId(2L);
        secondCategory.setName("AI");
        secondCategory.setPosts(null);

        categories = new ArrayList<>();
        categories.add(firstCategory);
        categories.add(secondCategory);
    }

    @Test
    public void testSave() throws Exception {
        Mockito.when(categoryService.findByName(firstCategory.getName())).thenReturn(firstCategory);
        Mockito.when(categoryService.save(secondCategory)).thenReturn(secondCategory);
        this.mockMvc
                .perform(
                        post("/category")
                            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .content(objectMapper.writeValueAsString(secondCategory))
                )
                .andExpect(status().isCreated());
    }

    @Test
    public void testSaveNotFound() throws Exception {
        Mockito.when(categoryService.findByName(firstCategory.getName())).thenReturn(firstCategory);
        this.mockMvc
                .perform(
                        post("/category")
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(objectMapper.writeValueAsString(firstCategory))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testFindById() throws Exception {
        Mockito.when(categoryService.findById(firstCategory.getId())).thenReturn(firstCategory);
        this.mockMvc
                .perform(
                        get("/category/{id}", firstCategory.getId())
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(objectMapper.writeValueAsString(firstCategory))
                )
                .andExpect(status().isOk());
    }

    @Test
    public void testFindByIdNotFound() throws Exception {
        Mockito.when(categoryService.findById(firstCategory.getId())).thenReturn(secondCategory);
        this.mockMvc
                .perform(
                        get("/category/{id}", secondCategory.getId())
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(objectMapper.writeValueAsString(firstCategory))
                )
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFindAll() throws Exception {
        Mockito.when(categoryService.findAll()).thenReturn(categories);
        this.mockMvc
                .perform(
                        get("/category")
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(objectMapper.writeValueAsString(categories))
                )
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdate() throws Exception {
        Mockito.when(categoryService.findById(firstCategory.getId())).thenReturn(firstCategory);
        Mockito.when(categoryService.update(firstCategory, secondCategory)).thenReturn(secondCategory);
        this.mockMvc
                .perform(
                        put("/category/{id}", firstCategory.getId())
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(objectMapper.writeValueAsString(secondCategory))
                )
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateNameAlreadyExistsBadRequest() throws Exception {
        Mockito.when(categoryService.findById(firstCategory.getId())).thenReturn(firstCategory);
        Mockito.when(categoryService.isNameAlreadyUsed(firstCategory.getName())).thenReturn(true);
        this.mockMvc
                .perform(
                        put("/category/{id}", firstCategory.getId())
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(objectMapper.writeValueAsString(firstCategory))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateNotFound() throws Exception {
        Mockito.when(categoryService.findById(firstCategory.getId())).thenReturn(firstCategory);
        this.mockMvc
                .perform(
                        put("/category/{id}", secondCategory.getId())
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(objectMapper.writeValueAsString(firstCategory))
                )
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDelete() throws Exception {
        Mockito.when(categoryService.findById(firstCategory.getId())).thenReturn(firstCategory);
        Mockito.when(categoryService.delete(firstCategory)).thenReturn(firstCategory);
        this.mockMvc
                .perform(
                        delete("/category/{id}", firstCategory.getId())
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(objectMapper.writeValueAsString(firstCategory))
                )
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        Mockito.when(categoryService.findById(firstCategory.getId())).thenReturn(firstCategory);
        this.mockMvc
                .perform(
                        delete("/category/{id}", secondCategory.getId())
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(objectMapper.writeValueAsString(firstCategory))
                )
                .andExpect(status().isNotFound());
    }
}
