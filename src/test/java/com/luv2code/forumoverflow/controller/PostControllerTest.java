package com.luv2code.forumoverflow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.forumoverflow.config.constants.Constants;
import com.luv2code.forumoverflow.domain.Category;
import com.luv2code.forumoverflow.domain.Post;
import com.luv2code.forumoverflow.domain.User;
import com.luv2code.forumoverflow.rest.controller.PostController;
import com.luv2code.forumoverflow.service.PostService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by lzugaj on Sunday, February 2020
 */

@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)
public class PostControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	private PostService postService;

	// TODO: Refactor with Authentication username
	@Test
	public void testSave() throws Exception {
		Long userId = 1L;
		User user = createUser(userId, "Luka", "Zugaj", "lzugaj", "lzugaj@racunarstvo.hr", "lzugaj123");

		Long categoryId = 1L;
		Category category = createCategory(categoryId, "School");

		Long postId = 1L;
		Post post = createPost(postId, "Title", "Description", user, category);

		when(postService.save(user.getUsername(), post)).thenReturn(post);

		this.mockMvc
				.perform(
						post("/post")
							.contentType(MediaType.APPLICATION_JSON_VALUE)
							.content(objectMapper.writeValueAsString(post))
				)
				.andExpect(status().isCreated());
	}

	@Test
	public void testFindById() throws Exception {
		Long userId = 1L;
		User user = createUser(userId, "Luka", "Zugaj", "lzugaj", "lzugaj@racunarstvo.hr", "lzugaj123");

		Long categoryId = 1L;
		Category category = createCategory(categoryId, "School");

		Long postId = 3L;
		Post post = createPost(postId, "Title", "Description", user, category);

		when(postService.findById(post.getId())).thenReturn(post);

		this.mockMvc
				.perform(
						get("/post/{id}", post.getId())
							.contentType(Constants.MEDIA_TYPE_FORUM_OVERFLOW_API_V1_VALUE)
							.content(objectMapper.writeValueAsString(post))
				)
				.andExpect(status().isOk());
	}

	@Test
	public void testFindAll() throws Exception {
		Long userId = 1L;
		User user = createUser(userId, "Dalibor", "Torma", "dtorma", "dtorma@gmail.com", "dtorma10");

		Long categoryId = 1L;
		Category category = createCategory(categoryId, "Feed");

		Long firstPostId = 1L;
		Post firstPost = createPost(firstPostId, "Naslov", "Opis", user, category);

		Long secondPostId = 2L;
		Post secondPost = createPost(secondPostId, "Title", "Description", user, category);

		List<Post> posts = new ArrayList<>();
		posts.add(firstPost);
		posts.add(secondPost);

		when(postService.findAll()).thenReturn(posts);

		this.mockMvc
				.perform(
						get("/post")
							.contentType(Constants.MEDIA_TYPE_FORUM_OVERFLOW_API_V1_VALUE)
							.content(objectMapper.writeValueAsString(posts))
				)
				.andExpect(status().isOk());
	}

	@Test
	public void testUpdate() throws Exception {
		Long userId = 1L;
		User user = createUser(userId, "Luka", "Zugaj", "lzugaj", "lzugaj@racunarstvo.hr", "lzugaj123");

		Long categoryId = 1L;
		Category category = createCategory(categoryId, "School");

		Long postId = 3L;
		Post post = createPost(postId, "Title", "Description", user, category);

		when(postService.update(post.getId(), post)).thenReturn(post);

		this.mockMvc
				.perform(
						put("/post/{id}", post.getId())
								.contentType(MediaType.APPLICATION_JSON_VALUE)
								.content(objectMapper.writeValueAsString(post))
				)
				.andExpect(status().isOk());
	}

	@Test
	public void testDelete() throws Exception {
		Long userId = 1L;
		User user = createUser(userId, "Tereza", "Zugaj", "tzugaj", "tzugaj@gmail.com", "tzugaj1");

		Long categoryId = 1L;
		Category category = createCategory(categoryId, "Marketing");

		Long postId = 3L;
		Post post = createPost(postId, "Naslov 2", "Opis popis", user, category);

		when(postService.delete(post.getId())).thenReturn(post);

		this.mockMvc
				.perform(
						delete("/post/{id}", post.getId())
							.contentType(Constants.MEDIA_TYPE_FORUM_OVERFLOW_API_V1_VALUE)
							.content(objectMapper.writeValueAsString(post))
				)
				.andExpect(status().isOk());
	}

	private User createUser(Long id, String firstName, String lastName, String username, String email, String password) {
		User user = new User();
		user.setId(id);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);
		user.setComments(null);
		user.setPosts(null);
		user.setRoles(null);
		return user;
	}

	private Category createCategory(Long id, String name) {
		Category category = new Category();
		category.setId(id);
		category.setName(name);
		category.setPosts(null);
		return category;
	}

	private Post createPost(Long id, String title, String description, User user, Category category) {
		Post post = new Post();
		post.setId(id);
		post.setTitle(title);
		post.setDescription(description);
		post.setCreatedDate(LocalDateTime.now());
		post.setUser(user);
		post.setCategory(category);
		post.setComments(null);
		return post;
	}
}
