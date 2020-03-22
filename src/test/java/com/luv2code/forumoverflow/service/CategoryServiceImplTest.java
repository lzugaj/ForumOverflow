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

import javassist.NotFoundException;

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
	public void testSave() {
		Long id = 1L;
		Category category = new Category(id, "Feed", null);

		when(categoryRepository.save(category)).thenReturn(category);

		Category newCategory = categoryService.save(category);

		assertNotNull(newCategory);
		assertEquals("1", newCategory.getId().toString());
		assertEquals("Feed", newCategory.getName());
	}

	@Test
	public void testFindById() {
		Long id = 1L;
		Category category = createCategory(id, "Marketing");

		when(categoryRepository.findById(category.getId())).thenReturn(java.util.Optional.of(category));

		Category searchedCategory = categoryService.findById(category.getId());

		assertNotNull(searchedCategory);
		assertEquals("1", searchedCategory.getId().toString());
		assertEquals("Marketing", searchedCategory.getName());
	}

	@Test
	public void testFindByIdNullPointerException() {
		Long id = 1L;
		Category category = createCategory(id, "Feed");

		when(categoryRepository.findById(category.getId())).thenThrow(new NullPointerException());

		assertThrows(NullPointerException.class, () -> categoryService.findById(category.getId()));
	}

	@Test
	public void testFindByUsername() {
		Long id = 1L;
		Category category = createCategory(id, "Marketing");

		when(categoryRepository.findByName(category.getName())).thenReturn(java.util.Optional.of(category));

		Category searchedCategory = categoryService.findByName(category.getName());

		assertNotNull(searchedCategory);
		assertEquals("1", searchedCategory.getId().toString());
		assertEquals("Marketing", searchedCategory.getName());
	}

	@Test
	public void testFindByUsernameNullPointerException() {
		Long id = 1L;
		Category category = createCategory(id, "Feed");

		when(categoryRepository.findByName(category.getName())).thenThrow(new NullPointerException());

		assertThrows(NullPointerException.class, () -> categoryService.findByName(category.getName()));
	}

	@Test
	public void testFindAll() {
		Long firstCategoryId = 1L;
		Category firstCategory = createCategory(firstCategoryId, "Feed");

		Long secondCategoryId = 2L;
		Category secondCategory = createCategory(secondCategoryId, "Marketing");

		List<Category> categories = new ArrayList<>();
		categories.add(firstCategory);
		categories.add(secondCategory);

		when(categoryRepository.findAll()).thenReturn(categories);

		List<Category> searchedCategories = categoryService.findAll();

		assertEquals(2, categories.size());
		assertEquals(2, searchedCategories.size());
		verify(categoryRepository, times(1)).findAll();
	}

	@Test
	public void testUpdate() {
		Long firstCategoryId = 1L;
		Category firstCategory = createCategory(firstCategoryId, "Feed");

		Long secondCategoryId = 2L;
		Category secondCategory = createCategory(secondCategoryId, "Marketing");

		when(categoryRepository.save(secondCategory)).thenReturn(secondCategory);

		Category updatedCategory = categoryService.update(firstCategory, secondCategory);

		assertNotNull(updatedCategory);
		assertEquals("1", updatedCategory.getId().toString());
		assertEquals("Marketing", updatedCategory.getName());
	}

	@Test
	public void testDelete() {
		Long id = 1L;
		Category category = createCategory(id, "Feed");

		Category deletedCategory = categoryService.delete(category);

		assertEquals("1", deletedCategory.getId().toString());
		assertEquals("Feed", deletedCategory.getName());
		verify(categoryRepository, times(1)).delete(category);
	}

	@Test
	public void testNameAlreadyExistsReturnTrue() {
		Long firstCategoryId = 1L;
		Category firstCategory = createCategory(firstCategoryId, "Feed");

		Long secondCategoryId = 2L;
		Category secondCategory = createCategory(secondCategoryId, "Marketing");

		List<Category> categories = new ArrayList<>();
		categories.add(firstCategory);
		categories.add(secondCategory);

		when(categoryRepository.findAll()).thenReturn(categories);

		String name = "Marketing";
		boolean nameAlreadyExists = categoryService.nameAlreadyExists(name);

		assertTrue(nameAlreadyExists);
	}

	@Test
	public void testNameAlreadyExistsReturnFalse() {
		Long firstCategoryId = 1L;
		Category firstCategory = createCategory(firstCategoryId, "Feed");

		Long secondCategoryId = 2L;
		Category secondCategory = createCategory(secondCategoryId, "Marketing");

		List<Category> categories = new ArrayList<>();
		categories.add(firstCategory);
		categories.add(secondCategory);

		when(categoryRepository.findAll()).thenReturn(categories);

		String name = "School";
		boolean nameAlreadyExists = categoryService.nameAlreadyExists(name);

		assertFalse(nameAlreadyExists);
	}

	private Category createCategory(Long id, String name) {
		Category category = new Category();
		category.setId(id);
		category.setName(name);
		return category;
	}
}
