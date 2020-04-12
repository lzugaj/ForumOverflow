package com.luv2code.forumoverflow.service;

import com.luv2code.forumoverflow.domain.Category;

import java.util.List;

/**
 * Created by lzugaj on Wednesday, February 2020
 */

public interface CategoryService {

	Category save(final Category category);

	Category findById(final Long id);

	Category findByName(final String name);

	List<Category> findAll();

	Category update(final Category oldCategory, final Category newCategory);

	Category delete(final Category category);

	boolean isNameAlreadyUsed(final String name);

}
