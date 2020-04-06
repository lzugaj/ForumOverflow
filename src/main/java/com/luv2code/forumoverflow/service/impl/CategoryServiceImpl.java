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
	public Category save(final Category category) {
		categoryRepository.save(category);
		log.info("Saving new Category with id: `{}`.", category.getId());
		return category;
	}

	@Override
	public Category findById(final Long id) {
		final Category searchedCategory = categoryRepository.findById(id).orElse(null);
		log.info("Searching Category with id: `{}`.", id);
		return searchedCategory;
	}

	@Override
	public Category findByName(final String name) {
		final Category searchedCategory = categoryRepository.findByName(name).orElse(null);
		log.info("Searching Category by with: `{}`.", name);
		return searchedCategory;
	}

	@Override
	public List<Category> findAll() {
		final List<Category> categories = categoryRepository.findAll();
		log.info("Searching all Categories.");
		return categories;
	}

	@Override
	public Category update(final Category oldCategory, final Category newCategory) {
		final Category updatedCategory = setUpVariables(oldCategory, newCategory);
		categoryRepository.save(updatedCategory);
		log.info("Updating Category with id: `{}`.", updatedCategory.getId());
		return updatedCategory;
	}

	private Category setUpVariables(final Category oldCategory, final Category newCategory) {
		oldCategory.setName(newCategory.getName());
		return oldCategory;
	}

	@Override
	public Category delete(final Category category) {
		categoryRepository.delete(category);
		log.info("Deleting Category with id: `{}`.", category.getId());
		return category;
	}

	@Override
	public boolean isNameAlreadyUsed(final String name) {
		return findAll().stream()
				.anyMatch(searchedCategory -> searchedCategory.getName().equals(name));
	}
}
