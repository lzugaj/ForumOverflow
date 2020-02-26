package com.luv2code.forumoverflow.service;

import com.luv2code.forumoverflow.domain.Category;
import com.luv2code.forumoverflow.exception.EntityNotFoundException;
import com.luv2code.forumoverflow.repository.CategoryRepository;
import com.luv2code.forumoverflow.service.impl.CategoryServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by lzugaj on Saturday, February 2020
 */

@SpringBootTest
public class CategoryServiceImplTest {

	@Mock
	private CategoryRepository categoryRepository;

	@InjectMocks
	private CategoryServiceImpl categoryService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFindById() {
		Long id = 1L;
		Category category = createCategory(id, "Feed");

		when(categoryRepository.findById(id)).thenReturn(java.util.Optional.of(category));

		Category searchedCategory = categoryService.findById(id);

		assertNotNull(searchedCategory);
		assertEquals("1", searchedCategory.getId().toString());
		assertEquals("Feed", searchedCategory.getName());
	}

	@Test
	public void testFindByIdEntityNotFoundException() {
		Long id = 1L;

		when(categoryRepository.findById(id)).thenThrow(new EntityNotFoundException("Category", "id", id.toString()));

		assertThrows(EntityNotFoundException.class, () -> categoryService.findById(id));
	}

	@Test
	public void testFindAll() {
		Long firstCategoryId = 1L;
		Category firstCategory = createCategory(firstCategoryId, "Feed");

		Long secondCategoryId = 2L;
		Category secondCategory = createCategory(secondCategoryId, "School");

		List<Category> categories = new ArrayList<>();
		categories.add(firstCategory);
		categories.add(secondCategory);

		when(categoryRepository.findAll()).thenReturn(categories);

		List<Category> searchedCategories = categoryService.findAll();

		assertEquals(2, searchedCategories.size());
		verify(categoryRepository, times(1)).findAll();
	}

	private Category createCategory(Long id, String name) {
		Category category = new Category();
		category.setId(id);
		category.setName(name);
		category.setPosts(null);
		return category;
	}
}
