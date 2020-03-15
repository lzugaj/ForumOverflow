package com.luv2code.forumoverflow.rest.controller;

import com.luv2code.forumoverflow.config.constants.Constants;
import com.luv2code.forumoverflow.domain.Post;
import com.luv2code.forumoverflow.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by lzugaj on Tuesday, February 2020
 */

@Slf4j
@Api(value = "Post Controller")
@RestController
@RequestMapping(path = "/post",
		produces = Constants.MEDIA_TYPE_FORUM_OVERFLOW_API_V1_VALUE)
public class PostController {

	private final PostService postService;

	@Autowired
	private PostController(final PostService postService) {
		this.postService = postService;
	}

	@ApiOperation(value = "Create post")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully created post")
	})
	@PostMapping(
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Post> save(@RequestBody Post post) {
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

	@ApiOperation(value = "Find post by id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully founded post"),
			@ApiResponse(code = 401, message = "You are not authorized to find the post"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to find is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to find is not found")
	})
	@GetMapping(path = "/{id}",
			produces = Constants.MEDIA_TYPE_FORUM_OVERFLOW_API_V1_VALUE)
	public ResponseEntity<Post> findById(@PathVariable Long id) {
		Post searchedPost = postService.findById(id);
		log.info("Successfully founded Post with id: `{}`.", searchedPost.getId());
		return ResponseEntity.status(HttpStatus.OK).body(searchedPost);
	}

	@ApiOperation(value = "Find all posts")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully founded all posts"),
			@ApiResponse(code = 401, message = "You are not authorized to find all the posts"),
			@ApiResponse(code = 403, message = "Accessing the resources you were trying to find is forbidden"),
			@ApiResponse(code = 404, message = "The resources you were trying to find is not found")
	})
	@GetMapping(produces = Constants.MEDIA_TYPE_FORUM_OVERFLOW_API_V1_UTF8_VALUE)
	public ResponseEntity<List<Post>> findAll() {
		List<Post> posts = postService.findAll();
		log.info("Successfully founded all Posts.");
		return ResponseEntity.status(HttpStatus.OK).body(posts);
	}

	@ApiOperation(value = "Update post")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully updated post"),
			@ApiResponse(code = 401, message = "You are not authorized to update the post"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to update is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to update is not found")
	})
	@PutMapping(path = "/{id}",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Post> update(@PathVariable Long id, @RequestBody Post post) {
		Post updatedPost = postService.update(id, post);
		log.info("Successfully updated Post with id: `{}`.", updatedPost.getId());
		return ResponseEntity.status(HttpStatus.OK).body(updatedPost);
	}

	@ApiOperation(value = "Delete post")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully deleted post"),
			@ApiResponse(code = 401, message = "You are not authorized to delete the post"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to delete is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to delete is not found")
	})
	@DeleteMapping(path = "/{id}",
			produces = Constants.MEDIA_TYPE_FORUM_OVERFLOW_API_V1_VALUE)
	public ResponseEntity<Post> delete(@PathVariable Long id) {
		Post deletedPost = postService.delete(id);
		log.info("Successfully deleted Post with id: `{}`.", deletedPost.getId());
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
