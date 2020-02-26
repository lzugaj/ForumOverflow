package com.luv2code.forumoverflow.service.impl;

import com.luv2code.forumoverflow.domain.Category;
import com.luv2code.forumoverflow.domain.Post;
import com.luv2code.forumoverflow.domain.User;
import com.luv2code.forumoverflow.exception.EntityNotFoundException;
import com.luv2code.forumoverflow.repository.PostRepository;
import com.luv2code.forumoverflow.service.CategoryService;
import com.luv2code.forumoverflow.service.PostService;
import com.luv2code.forumoverflow.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Created by lzugaj on Friday, February 2020
 */

@Slf4j
@Service
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;

	private final UserService userService;

	private final CategoryService categoryService;

	@Autowired
	public PostServiceImpl(final PostRepository postRepository, final UserService userService,
						   final CategoryService categoryService) {
		this.postRepository = postRepository;
		this.userService = userService;
		this.categoryService = categoryService;
	}

	@Override
	public Post save(String username, Post post) {
		User postCreator = userService.findByUsername(username);
		log.info("Successfully founded User with username: `{}`.", username);

		Category selectedCategory = categoryService.findById(post.getCategory().getId());
		log.info("Successfully founded Category with id: `{}`.", post.getCategory().getId());

		post.setCreatedDate(LocalDateTime.now());
		post.setUser(postCreator);
		post.setCategory(post.getCategory());
		post.setComments(null);
		postRepository.save(post);
		log.info("Saving Post with id: `{}`.", post.getId());

		selectedCategory.setPosts(Collections.singletonList(post));
		log.info("Setting Post to selected Category with id: ´{}´.", selectedCategory.getId());
		return post;
	}

	@Override
	public Post findById(Long id) {
		Post searchedPost = postRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Post", "id", id.toString()));
		log.info("Searching Post with id: `{}`.", id);
		return searchedPost;
	}

	@Override
	public List<Post> findAll() {
		List<Post> posts = postRepository.findAll();
		log.info("Searching all Posts.");
		return posts;
	}

	@Override
	public Post update(Long id, Post post) {
		Post updatedPost = findById(id);
		log.info("Successfully founded Post with id: `{}`.", id);

		updatedPost.setTitle(post.getTitle());
		updatedPost.setDescription(post.getDescription());
		updatedPost.setCreatedDate(LocalDateTime.now());
		updatedPost.setUser(updatedPost.getUser());
		updatedPost.setCategory(post.getCategory());
		updatedPost.setComments(post.getComments());

		postRepository.save(updatedPost);
		log.info("Updating Post with id: `{}`.", post.getId());
		return updatedPost;
	}

	@Override
	public Post delete(Long id) {
		Post searchedPost = findById(id);
		log.info("Successfully founded Post with id: `{}`.", id);

		postRepository.delete(searchedPost);
		log.info("Deleting Post with id: `{}`.", id);
		return searchedPost;
	}
}
