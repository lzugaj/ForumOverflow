package com.luv2code.forumoverflow.service;

import com.luv2code.forumoverflow.domain.Category;

import java.util.List;

/**
 * Created by lzugaj on Wednesday, February 2020
 */

public interface CategoryService {

	Category findById(Long id);

	List<Category> findAll();

}
