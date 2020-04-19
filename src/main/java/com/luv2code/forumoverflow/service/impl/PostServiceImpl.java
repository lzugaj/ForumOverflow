package com.luv2code.forumoverflow.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luv2code.forumoverflow.domain.Category;
import com.luv2code.forumoverflow.domain.ContentStatus;
import com.luv2code.forumoverflow.domain.Post;
import com.luv2code.forumoverflow.domain.User;
import com.luv2code.forumoverflow.repository.PostRepository;
import com.luv2code.forumoverflow.service.CategoryService;
import com.luv2code.forumoverflow.service.ContentStatusService;
import com.luv2code.forumoverflow.service.PostService;
import com.luv2code.forumoverflow.service.UserService;
import com.luv2code.forumoverflow.util.Constants;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lzugaj on Friday, February 2020
 */

@Slf4j
@Service
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;

	private final UserService userService;

	private final CategoryService categoryService;

	private final ContentStatusService contentStatusService;

	@Autowired
	public PostServiceImpl(final PostRepository postRepository, final UserService userService,
						   final CategoryService categoryService, final ContentStatusService contentStatusService) {
		this.postRepository = postRepository;
		this.userService = userService;
		this.categoryService = categoryService;
		this.contentStatusService = contentStatusService;
	}

	// TODO: Refactor method
	@Override
	public Post save(final String username, final Post post) {
		final User postCreator = userService.findByUsername(username);
		log.info("Successfully founded User with username: `{}`.", username);

		final Category selectedCategory = categoryService.findById(post.getCategory().getId());
		log.info("Successfully founded Category with id: `{}`.", post.getCategory().getId());

		final ContentStatus validContentStatus = contentStatusService.findByName(Constants.VALID);
		log.info("Successfully founded ContentStatus with name: `{}`.", validContentStatus.getName());

		// TODO: Separate method?
		post.setCreatedDate(LocalDateTime.now());
		post.setContentStatus(validContentStatus);
		post.setUser(postCreator);
		post.setCategory(selectedCategory);

		postRepository.save(post);
		log.info("Saving Post with id: `{}`.", post.getId());

		// TODO: Separate methods?
		validContentStatus.setPosts(Collections.singletonList(post));
		postCreator.setPosts(Collections.singletonList(post));
		selectedCategory.setPosts(Collections.singletonList(post));

		return post;
	}

	@Override
	public Post reportAsInvalid(final Long id) {
		final Post searchedPost = findById(id);
		log.info("Successfully founded Post with id: `{}`.", id);

		final ContentStatus invalidContentStatus = contentStatusService.findByName(Constants.INVALID);
		log.info("Successfully founded ContentStatus with name: `{}`.", invalidContentStatus.getName());

		searchedPost.setContentStatus(invalidContentStatus);
		log.info("User has report Post with id `{}` as invalid.", id);

		// TODO: Send notification to User which Post was report as invalid(generic mail), current post will automatically become invalid and unreachable to other users

		// TODO: Send mail to Admin with info which User has report the post and which post and it's owner was reported.
		// TODO: Admin will decide if reported post is really invalid or not and update content status of the post
		// TODO: If post will be characterized as Valid, content status will become back to Valid and everyone will see post again as normal(user will get notification)
		// TODO: If post will be characterized as Invalid, post will be automatically deleted(Scheduler every day?) and Post's user will get notification
		// TODO: and user's blocker counter will increase for 1
		// TODO: If blocker has value 1 or 2 user will be blocked for next 24 hours and get notification about that
		// TODO: If blocker has value 3 user will be blocked for 1 week and get notification(update blocker value to 0 after that)

		return searchedPost;
	}

	@Override
	public Post findById(final Long id) {
		final Post searchedPost = postRepository.findById(id).orElse(null);
		log.info("Searching Post with id: `{}`.", id);
		return searchedPost;
	}

	@Override
	public List<Post> findAll() {
		final List<Post> posts = postRepository.findAll();
		log.info("Searching all Posts.");
		return posts;
	}

	@Override
	public List<Post> findAllByUsername(final String username) {
		return findAll().stream()
				.filter(searchedPost -> searchedPost.getUser().getUsername().equals(username))
				.collect(Collectors.toList());
	}

	@Override
	public List<Post> findAllByCategory(final Long categoryId) {
		return findAll().stream()
				.filter(searchedPost -> searchedPost.getCategory().getId().equals(categoryId))
				.collect(Collectors.toList());
	}

	@Override
	public List<Post> findAllReported() {
		return findAll().stream()
				.filter(searchedPost -> searchedPost.getContentStatus().getName().equals(Constants.INVALID))
				.collect(Collectors.toList());
	}

	@Override
	public Post update(final Post oldPost, final Post newPost) {
		final Post updatedPost = setUpVariables(oldPost, newPost);
		postRepository.save(updatedPost);
		log.info("Updating Post with id: `{}`.", updatedPost.getId());
		return updatedPost;
	}

	private Post setUpVariables(final Post oldPost, final Post newPost) {
		oldPost.setTitle(newPost.getTitle());
		oldPost.setDescription(newPost.getDescription());
		oldPost.setCreatedDate(LocalDateTime.now());
		oldPost.setCategory(newPost.getCategory());
		return oldPost;
	}

	@Override
	public Post updateStatus(final Post updatedStatus, final ContentStatus contentStatus) {
		updatedStatus.setContentStatus(contentStatus);
		log.info("Updating content status to: `{}`", contentStatus.getName());

		postRepository.save(updatedStatus);
		log.info("Updating Post with id: `{}`", updatedStatus.getId());
		return updatedStatus;
	}

	@Override
	public Post delete(final Post post) {
		postRepository.delete(post);
		log.info("Deleting Post with id: `{}`.", post.getId());

		// TODO: Check if current logged in user is Admin and send notification to user if Admin manually delete user post

		return post;
	}
}
