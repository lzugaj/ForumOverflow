package com.luv2code.forumoverflow.rest.controller;

import com.luv2code.forumoverflow.domain.Post;
import com.luv2code.forumoverflow.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by lzugaj on Tuesday, February 2020
 */

@Slf4j
@RestController
@RequestMapping(path = "/post",
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE)
public class PostController {

	private final PostService postService;

	@Autowired
	private PostController(final PostService postService) {
		this.postService = postService;
	}

	@PostMapping
	public ResponseEntity<Post> save(@RequestBody Post post) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
			log.info("Currently User is not registered in application.");
			return ResponseEntity.notFound().build();
		}

		Post newPost = postService.save(authentication.getName(), post);
		log.info("Successfully saved Post with id: `{}`", newPost.getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(newPost);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Post> findById(@PathVariable Long id) {
		Post searchedPost = postService.findById(id);
		if (searchedPost == null) {
			log.info("Post that tried to be searched is null.");
			return ResponseEntity.notFound().build();
		}

		log.info("Successfully founded Post with id: `{}`", searchedPost.getId());
		return ResponseEntity.ok().body(searchedPost);
	}

	@GetMapping
	public ResponseEntity<List<Post>> findAll() {
		List<Post> posts = postService.findAll();
		log.info("Successfully founded all Posts.");
		return ResponseEntity.ok().body(posts);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Post> update(@PathVariable Long id, @RequestBody Post post) {
		Post updatedPost = postService.findById(id);
		if (updatedPost == null) {
			log.info("Post that tried to be searched is null.");
			return ResponseEntity.notFound().build();
		}

		updatedPost = postService.update(id, post);
		log.info("Successfully updated Post with id: `{}`", updatedPost.getId());
		return ResponseEntity.status(HttpStatus.OK).body(updatedPost);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Post> delete(@PathVariable Long id) {
		Post deletedPost = postService.findById(id);
		if (deletedPost == null) {
			log.info("Post that tried to be searched is null.");
			return ResponseEntity.notFound().build();
		}

		postService.delete(id);
		log.info("Successfully deleted Post with id: `{}`", deletedPost.getId());
		return ResponseEntity.noContent().build();
	}
}
