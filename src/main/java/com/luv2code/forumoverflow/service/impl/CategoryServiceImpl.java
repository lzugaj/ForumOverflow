package com.luv2code.forumoverflow.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luv2code.forumoverflow.domain.Category;
import com.luv2code.forumoverflow.repository.CategoryRepository;
import com.luv2code.forumoverflow.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lzugaj on Wednesday, February 2020
 */

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;

	@Autowired
	public CategoryServiceImpl(final CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public Category save(Category category) {
		Category newCategory = categoryRepository.save(category);
		log.info("Saving new Category with id: `{}`", category.getId());
		return newCategory;
	}

	@Override
	public Category findById(Long id) {
		Category searchedCategory = categoryRepository.findById(id).orElse(null);
		log.info("Searching Category with id: `{}`.", id);
		return searchedCategory;
	}

	@Override
	public Category findByName(String name) {
		Category searchedCategory = categoryRepository.findByName(name).orElse(null);
		log.info("Searching Category with name: `{}`.", name);
		return searchedCategory;
	}

	@Override
	public List<Category> findAll() {
		List<Category> categories = categoryRepository.findAll();
		log.info("Searching all Categories.");
		return categories;
	}

	@Override
	public Category update(Category oldCategory, Category newCategory) {
		Category updatedCategory = setUpVariables(oldCategory, newCategory);
		categoryRepository.save(updatedCategory);
		log.info("Updating Category with id: `{}`.", updatedCategory.getId());
		return updatedCategory;
	}

	private Category setUpVariables(Category oldCategory, Category newCategory) {
		Category updatedCategory = new Category();
		updatedCategory.setId(oldCategory.getId());
		updatedCategory.setName(newCategory.getName());
		updatedCategory.setPosts(oldCategory.getPosts());

		log.info("Setting up variables for updated Category with id: `{}`", oldCategory.getId());
		return updatedCategory;
	}

	@Override
	public Category delete(Category category) {
		categoryRepository.delete(category);
		log.info("Successfully deleted Category with id: `{}`", category.getId());
		return category;
	}

	@Override
	public boolean nameAlreadyExists(String name) {
		for (Category category : findAll()) {
			if (category.getName().equals(name)) {
				return true;
			}
		}

		return false;
	}
}
