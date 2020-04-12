package com.luv2code.forumoverflow.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.luv2code.forumoverflow.domain.Category;
import com.luv2code.forumoverflow.repository.CategoryRepository;
import com.luv2code.forumoverflow.service.impl.CategoryServiceImpl;

/**
 * Created by lzugaj on Saturday, February 2020
 */

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceImplTest {

	@Mock
	private CategoryRepository categoryRepository;

	@InjectMocks
	private CategoryServiceImpl categoryService;

	private Category firstCategory;

	private Category secondCategory;

	private List<Category> categories;

	@BeforeEach
	public void setup() {
		firstCategory = new Category();
		firstCategory.setId(1L);
		firstCategory.setName("Feed");
		firstCategory.setPosts(null);

		secondCategory = new Category();
		secondCategory.setId(2L);
		secondCategory.setName("Schools");
		secondCategory.setPosts(null);

		categories = new ArrayList<>();
		categories.add(firstCategory);
		categories.add(secondCategory);

		Mockito.when(categoryRepository.save(firstCategory)).thenReturn(firstCategory);
		Mockito.when(categoryRepository.findById(firstCategory.getId())).thenReturn(java.util.Optional.of(firstCategory));
		Mockito.when(categoryRepository.findByName(firstCategory.getName())).thenReturn(java.util.Optional.ofNullable(firstCategory));
		Mockito.when(categoryRepository.findAll()).thenReturn(categories);
	}

	@Test
	public void testSave() {
		Category newCategory = categoryService.save(firstCategory);

		assertNotNull(newCategory);
		assertEquals("1", newCategory.getId().toString());
		assertEquals("Feed", newCategory.getName());
		assertNull(firstCategory.getPosts());
	}

	@Test
	public void testFindById() {
		Category searchedCategory = categoryService.findById(firstCategory.getId());

		assertNotNull(searchedCategory);
		assertEquals("1", firstCategory.getId().toString());
		assertEquals("Feed", firstCategory.getName());
		assertNull(firstCategory.getPosts());
	}

	@Test
	public void testFindByIdNullPointerException() {
		when(categoryRepository.findById(firstCategory.getId())).thenThrow(new NullPointerException());

		assertThrows(NullPointerException.class, () -> categoryService.findById(firstCategory.getId()));
	}

	@Test
	public void testFindByName() {
		Category searchedCategory = categoryService.findByName(firstCategory.getName());

		assertNotNull(searchedCategory);
		assertEquals("1", searchedCategory.getId().toString());
		assertEquals("Feed", searchedCategory.getName());
		assertNull(firstCategory.getPosts());
	}

	@Test
	public void testFindByNameNullPointerException() {
		when(categoryRepository.findByName(firstCategory.getName())).thenThrow(new NullPointerException());

		assertThrows(NullPointerException.class, () -> categoryService.findByName(firstCategory.getName()));
	}

	@Test
	public void testFindAll() {
		List<Category> searchedCategories = categoryService.findAll();

		assertEquals(2, categories.size());
		assertEquals(2, searchedCategories.size());
		verify(categoryRepository, times(1)).findAll();
	}

	@Test
	public void testUpdate() {
		Category updatedCategory = categoryService.update(secondCategory, firstCategory);

		assertNotNull(updatedCategory);
		assertEquals("2", updatedCategory.getId().toString());
		assertEquals("Feed", updatedCategory.getName());
	}

	@Test
	public void testDelete() {
		Category deletedCategory = categoryService.delete(firstCategory);

		assertEquals("1", deletedCategory.getId().toString());
		assertEquals("Feed", deletedCategory.getName());
		verify(categoryRepository, times(1)).delete(firstCategory);
	}

	@Test
	public void testNameAlreadyExistsReturnTrue() {
		String name = "Feed";
		boolean nameAlreadyExists = categoryService.isNameAlreadyUsed(name);

		assertTrue(nameAlreadyExists);
	}

	@Test
	public void testNameAlreadyExistsReturnFalse() {
		String name = "IoT";
		boolean nameAlreadyExists = categoryService.isNameAlreadyUsed(name);

		assertFalse(nameAlreadyExists);
	}
}
