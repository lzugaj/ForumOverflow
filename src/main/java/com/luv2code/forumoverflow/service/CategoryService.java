package com.luv2code.forumoverflow.service;

import com.luv2code.forumoverflow.domain.Category;

import java.util.List;

/**
 * Created by lzugaj on Wednesday, February 2020
 */

public interface CategoryService {

	Category save(Category category);

	Category findById(Long id);

	Category findByName(String name);

	List<Category> findAll();

	Category update(Category oldCategory, Category newCategory);

	Category delete(Category category);

	boolean isNameAlreadyUsed(String name);

}
