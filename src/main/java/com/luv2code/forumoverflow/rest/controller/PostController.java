package com.luv2code.forumoverflow.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.forumoverflow.domain.ContentStatus;
import com.luv2code.forumoverflow.domain.Post;
import com.luv2code.forumoverflow.service.PostService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by lzugaj on Tuesday, February 2020
 */

@Slf4j
@Api(value = "Post Controller")
@RestController
@RequestMapping(path = "/post",
		produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PostController {

	private final PostService postService;

	@Autowired
	private PostController(final PostService postService) {
		this.postService = postService;
	}

	@ApiOperation(value = "Create")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully created post")
	})
	@PostMapping(
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> save(@RequestBody Post post) {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//		// TODO: Need this or not?
//		if (authentication instanceof AnonymousAuthenticationToken) {
//			log.info("Currently User is not registered in application.");
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//		}

//		Post newPost = postService.save(authentication.getName(), post);
		Post newPost = postService.save("lzugaj", post);
		log.info("Successfully saved Post with id: `{}`.", newPost.getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(newPost);
	}

	@ApiOperation(value = "Find all by category")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully founded all posts by category")
	})
	@PostMapping(path = "/{categoryId}",
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> findAllByCategory(@PathVariable Long categoryId) {
		List<Post> searchedPosts = postService.findAllByCategory(categoryId);
		log.info("Successfully founded all Posts by Category id: `{}`", categoryId);
		return ResponseEntity.status(HttpStatus.OK).body(searchedPosts);
	}

	// TODO: Method for User to report Post

	@ApiOperation(value = "Find by id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully founded post"),
			@ApiResponse(code = 401, message = "You are not authorized to find the post"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to find is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to find is not found")
	})
	@GetMapping(path = "/{id}",
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> findById(@PathVariable Long id) {
		Optional<Post> searchedPost = Optional.ofNullable(postService.findById(id));
		if (searchedPost.isPresent()) {
			log.info("Successfully founded Post with id: `{}`", id);
			return ResponseEntity.status(HttpStatus.OK).body(searchedPost.get());
		} else {
			log.info("Post with id `{}` wasn't founded.", id);
			return new ResponseEntity<>("Searched Post wasn't founded.", HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation(value = "Find all")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully founded all posts"),
			@ApiResponse(code = 401, message = "You are not authorized to find all the posts"),
			@ApiResponse(code = 403, message = "Accessing the resources you were trying to find is forbidden"),
			@ApiResponse(code = 404, message = "The resources you were trying to find is not found")
	})
	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> findAll() {
		List<Post> searchedPosts = postService.findAll();
		log.info("Successfully founded all Posts.");
		return ResponseEntity.status(HttpStatus.OK).body(searchedPosts);
	}

	// TODO: Send info which user has reported this post as invalid -> SecurityContextHolder.getContext().getAuthentication().getName();
	@ApiOperation(value = "Find all reported")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully founded all reported posts"),
			@ApiResponse(code = 401, message = "You are not authorized to find all the reported posts"),
			@ApiResponse(code = 403, message = "Accessing the resources you were trying to find is forbidden"),
			@ApiResponse(code = 404, message = "The resources you were trying to find is not found")
	})
	@GetMapping(path = "/reported",
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> findAllReported() {
		List<Post> reportedPosts = postService.findAllReported();
		log.info("Successfully founded all reported Posts.");
		return ResponseEntity.status(HttpStatus.OK).body(reportedPosts);
	}

	@ApiOperation(value = "Update")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully updated post"),
			@ApiResponse(code = 401, message = "You are not authorized to update the post"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to update is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to update is not found")
	})
	@PutMapping(path = "/{id}",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Post post) {
		Optional<Post> searchedPost = Optional.ofNullable(postService.findById(id));
		if (searchedPost.isPresent()) {
			Post updatedPost = postService.update(searchedPost.get(), post);
			log.info("Successfully updated Post with id: `{}`", id);
			return ResponseEntity.status(HttpStatus.OK).body(updatedPost);
		} else {
			log.info("Post with id `{}` wasn't founded.", id);
			return new ResponseEntity<>("Searched Post wasn't founded.", HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation(value = "Update content status")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully updated content status"),
			@ApiResponse(code = 401, message = "You are not authorized to update the content status"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to update is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to update is not found")
	})
	@PutMapping(path = "/info/{id}",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateContentStatus(@PathVariable Long id, @RequestBody ContentStatus contentStatus) {
		Optional<Post> searchedPost = Optional.ofNullable(postService.findById(id));
		if (searchedPost.isPresent()) {
			Post updatedPost = postService.updateStatus(searchedPost.get(), contentStatus);
			log.info("Successfully update content status for Post with id: `{}`", id);
			return ResponseEntity.status(HttpStatus.OK).body(updatedPost);
		} else {
			log.info("Post with id `{}` wasn't founded.", id);
			return new ResponseEntity<>("Searched Post wasn't founded.", HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation(value = "Delete")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully deleted post"),
			@ApiResponse(code = 401, message = "You are not authorized to delete the post"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to delete is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to delete is not found")
	})
	@DeleteMapping(path = "/{id}",
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<Post> searchedPost = Optional.ofNullable(postService.findById(id));
		if (searchedPost.isPresent()) {
			Post deletedPost = postService.delete(searchedPost.get());
			log.info("Successfully deleted Post with id: `{}`", deletedPost.getId());
			return new ResponseEntity<>("Post was successfully deleted.", HttpStatus.OK);
		} else {
			log.info("Post with id `{}` wasn't founded.", id);
			return new ResponseEntity<>("Searched Post wasn't founded.", HttpStatus.NOT_FOUND);
		}
	}
}
