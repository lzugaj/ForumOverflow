package com.luv2code.forumoverflow.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
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

    @Test
    public void testSave() throws Exception {
        Long id = 1L;
        Category category = createCategory(id, "Programming");

        when(categoryService.findByName(category.getName())).thenReturn(null);
        when(categoryService.save(category)).thenReturn(category);

        this.mockMvc
                .perform(
                        post("/category")
                            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .content(objectMapper.writeValueAsString(category))
                )
                .andExpect(status().isCreated());
    }

    @Test
    public void testSaveNotFound() throws Exception {
        Long id = 1L;
        Category category = createCategory(id, "Feed");

        when(categoryService.findByName(category.getName())).thenReturn(category);

        this.mockMvc
                .perform(
                        post("/category")
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(objectMapper.writeValueAsString(category))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testFindById() throws Exception {
        Long id = 1L;
        Category category = createCategory(id, "Programming");

        when(categoryService.findById(category.getId())).thenReturn(category);

        this.mockMvc
                .perform(
                        get("/category/{id}", category.getId())
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(objectMapper.writeValueAsString(category))
                )
                .andExpect(status().isOk());
    }

    @Test
    public void testFindByIdNotFound() throws Exception {
        Long id = 1L;
        Category category = createCategory(id, "Programming");

        when(categoryService.findById(category.getId())).thenReturn(null);

        this.mockMvc
                .perform(
                        get("/category/{id}", category.getId())
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(objectMapper.writeValueAsString(category))
                )
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFindAll() throws Exception {
        Long firstCategoryId = 1L;
        Category firstCategory = createCategory(firstCategoryId, "Programming");

        Long secondCategoryId = 2L;
        Category secondCategory = createCategory(secondCategoryId, "Feed");

        Long thirdCategoryId = 3L;
        Category thirdCategory = createCategory(thirdCategoryId, "Education");

        List<Category> categories = new ArrayList<>();
        categories.add(firstCategory);
        categories.add(secondCategory);
        categories.add(thirdCategory);

        when(categoryService.findAll()).thenReturn(categories);

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
        Long firstCategoryId = 1L;
        Category firstCategory = createCategory(firstCategoryId, "Programming");

        Long secondCategoryId = 2L;
        Category secondCategory = createCategory(secondCategoryId, "Feed");

        when(categoryService.findById(firstCategory.getId())).thenReturn(firstCategory);
        when(categoryService.nameAlreadyExists(secondCategory.getName())).thenReturn(false);
        when(categoryService.update(firstCategory, secondCategory)).thenReturn(secondCategory);

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
        Long firstCategoryId = 1L;
        Category firstCategory = createCategory(firstCategoryId, "Programming");

        Long secondCategoryId = 2L;
        Category secondCategory = createCategory(secondCategoryId, "Feed");

        when(categoryService.findById(firstCategory.getId())).thenReturn(firstCategory);
        when(categoryService.nameAlreadyExists(secondCategory.getName())).thenReturn(true);

        this.mockMvc
                .perform(
                        put("/category/{id}", firstCategory.getId())
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(objectMapper.writeValueAsString(secondCategory))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateNotFound() throws Exception {
        Long firstCategoryId = 1L;
        Category firstCategory = createCategory(firstCategoryId, "Programming");

        Long secondCategoryId = 2L;
        Category secondCategory = createCategory(secondCategoryId, "Feed");

        when(categoryService.findById(firstCategory.getId())).thenReturn(null);

        this.mockMvc
                .perform(
                        put("/category/{id}", firstCategory.getId())
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(objectMapper.writeValueAsString(secondCategory))
                )
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDelete() throws Exception {
        Long id = 1L;
        Category category = createCategory(id, "Programming");

        when(categoryService.findById(category.getId())).thenReturn(category);
        when(categoryService.delete(category)).thenReturn(category);

        this.mockMvc
                .perform(
                        delete("/category/{id}", category.getId())
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(objectMapper.writeValueAsString(category))
                )
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        Long id = 1L;
        Category category = createCategory(id, "Programming");

        when(categoryService.findById(category.getId())).thenReturn(null);

        this.mockMvc
                .perform(
                        delete("/category/{id}", category.getId())
                                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                .content(objectMapper.writeValueAsString(category))
                )
                .andExpect(status().isNotFound());
    }

    private Category createCategory(Long id, String name) {
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        category.setPosts(null);
        return category;
    }
}
