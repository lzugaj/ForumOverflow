package com.luv2code.forumoverflow.service;

import java.util.List;

import com.luv2code.forumoverflow.domain.ContentStatus;
import com.luv2code.forumoverflow.domain.Post;

/**
 * Created by lzugaj on Friday, February 2020
 */

public interface PostService {

	Post save(String username, Post post);

	Post reportAsInvalid(Long id);

	Post findById(Long id);

	List<Post> findAll();

	List<Post> findAllByUsername(String username);

	List<Post> findAllByCategory(Long categoryId);

	List<Post> findAllReported();

	Post update(Post oldPost, Post newPost);

	Post updateStatus(Post updatedStatus, ContentStatus contentStatus);

	Post delete(Post post);

}
