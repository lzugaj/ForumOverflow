package com.luv2code.forumoverflow.service.impl;

import com.luv2code.forumoverflow.domain.Category;
import com.luv2code.forumoverflow.exception.EntityNotFoundException;
import com.luv2code.forumoverflow.repository.CategoryRepository;
import com.luv2code.forumoverflow.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
	public Category findById(Long id) {
		Category searchedCategory = categoryRepository.findById(id).orElse(null);
		log.info("Searching Category with id: `{}`.", id);
		if (searchedCategory == null) {
			throw new EntityNotFoundException("Category", "id", id.toString());
		}

		return searchedCategory;
	}

	@Override
	public List<Category> findAll() {
		List<Category> categories = categoryRepository.findAll();
		log.info("Searching all Categories.");
		return categories;
	}
}
