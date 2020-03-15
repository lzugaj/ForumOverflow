package com.luv2code.forumoverflow.service;

import com.luv2code.forumoverflow.domain.Category;
import com.luv2code.forumoverflow.domain.Post;
import com.luv2code.forumoverflow.domain.User;
import com.luv2code.forumoverflow.exception.EntityNotFoundException;
import com.luv2code.forumoverflow.repository.PostRepository;
import com.luv2code.forumoverflow.service.impl.PostServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by lzugaj on Friday, February 2020
 */

@SpringBootTest
public class PostServiceImplTest {

	@Mock
	private PostRepository postRepository;

	@Mock
	private UserService userService;

	@Mock
	private CategoryService categoryService;

	@InjectMocks
	private PostServiceImpl postService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testSave() {
		Long userId = 1L;
		User user = new User(userId, "Alma", "Zugaj", "azugaj", "azugaj@gmail.com", "azugaj123", 0, null, null, null, null);

		Long categoryId = 1L;
		Category category = new Category(categoryId, "Feed", null);

		Long postId = 1L;
		Post post = new Post(postId, "Title", "Description", LocalDateTime.now(), user, category, null);

		when(userService.findByUsername(user.getUsername())).thenReturn(user);
		when(categoryService.findById(category.getId())).thenReturn(category);
		when(postRepository.save(post)).thenReturn(post);

		Post newPost = postService.save(user.getUsername(), post);

		assertNotNull(newPost);
		assertEquals("1", newPost.getId().toString());
		assertEquals("Title", newPost.getTitle());
		assertEquals("Description", newPost.getDescription());
		assertEquals(user, newPost.getUser());
		assertEquals(category, newPost.getCategory());
		assertNull(newPost.getComments());
	}

	@Test
	public void testFindById() {
		Long userId = 1L;
		User user = createUser(userId, "Dalibor", "Torma", "dtorma", "dtorma@gmail.com", "dtorma10");

		Long categoryId = 1L;
		Category category = createCategory(categoryId, "School");

		Long postId = 1L;
		Post post = createPost(postId, "Naslov", "Opis", user, category);

		when(postRepository.findById(post.getId())).thenReturn(java.util.Optional.of(post));

		Post searchedPost = postService.findById(post.getId());

		assertNotNull(searchedPost);
		assertEquals("1", searchedPost.getId().toString());
		assertEquals("Naslov", searchedPost.getTitle());
		assertEquals("Opis", searchedPost.getDescription());
		assertNull(searchedPost.getComments());
	}

	@Test
	public void testFindByIdEntityNotFoundException() {
		Long id = 2L;

		when(postRepository.findById(id)).thenThrow(new EntityNotFoundException("Post", "id", id.toString()));

		assertThrows(EntityNotFoundException.class, () -> postService.findById(id));
	}

	@Test
	public void testFindAll() {
		Long userId = 1L;
		User user = createUser(userId, "Dalibor", "Torma", "dtorma", "dtorma@gmail.com", "dtorma10");

		Long categoryId = 1L;
		Category category = createCategory(categoryId, "School");

		Long firstPostId = 1L;
		Post firstPost = createPost(firstPostId, "Naslov", "Opis", user, category);

		Long secondPostId = 1L;
		Post secondPost = createPost(secondPostId, "Title", "Description", user, category);

		List<Post> posts = new ArrayList<>();
		posts.add(firstPost);
		posts.add(secondPost);

		when(postRepository.findAll()).thenReturn(posts);

		List<Post> searchedPosts = postService.findAll();

		assertEquals(2, searchedPosts.size());
		verify(postRepository, times(1)).findAll();
	}

	@Test
	public void testFindAllByUsername() {
		Long userId = 1L;
		User user = createUser(userId, "Dalibor", "Torma", "dtorma", "dtorma@gmail.com", "dtorma10");

		Long categoryId = 1L;
		Category category = createCategory(categoryId, "School");

		Long firstPostId = 1L;
		Post firstPost = createPost(firstPostId, "Naslov", "Opis", user, category);

		Long secondPostId = 1L;
		Post secondPost = createPost(secondPostId, "Title", "Description", user, category);

		List<Post> posts = new ArrayList<>();
		posts.add(firstPost);
		posts.add(secondPost);

		when(postRepository.findAll()).thenReturn(posts);

		List<Post> searchedPosts = postService.findAllByUsername(user.getUsername());

		assertEquals(2, searchedPosts.size());
		verify(postRepository, times(1)).findAll();
	}

	@Test
	public void testUpdate() {
		Long userId = 1L;
		User user = createUser(userId, "Dalibor", "Torma", "dtorma", "dtorma@gmail.com", "dtorma10");

		Long categoryId = 1L;
		Category category = createCategory(categoryId, "School");

		Long postId = 1L;
		Post firstPost = createPost(postId, "Naslov", "Opis", user, category);

		Post secondPost = createPost(postId, "Title", "Description", user, category);

		when(postRepository.findById(firstPost.getId())).thenReturn(java.util.Optional.of(firstPost));
		when(postRepository.save(secondPost)).thenReturn(secondPost);

		Post updatedPost = postService.update(secondPost.getId(), secondPost);

		assertNotNull(updatedPost);
		assertEquals("1", updatedPost.getId().toString());
		assertEquals("Title", updatedPost.getTitle());
		assertEquals("Description", updatedPost.getDescription());
		assertNull(updatedPost.getComments());
	}

	@Test
	public void testUpdateEntityNotFoundException() {
		Long userId = 1L;
		User user = createUser(userId, "Luka", "Zugaj", "lzugaj", "lzugaj@gmail.com", "lzugaj123");

		Long categoryId = 1L;
		Category category = createCategory(categoryId, "Feed");

		Long postId = 1L;
		Post post = createPost(postId, "Naslov", "Opis", user, category);

		when(postRepository.findById(post.getId())).thenThrow(new EntityNotFoundException("Post", "id", post.getId().toString()));

		assertThrows(EntityNotFoundException.class, () -> postService.update(postId, post));
	}

	@Test
	public void testDelete() {
		Long userId = 1L;
		User user = createUser(userId, "Luka", "Zugaj", "lzugaj", "lzugaj@gmail.com", "lzugaj123");

		Long categoryId = 1L;
		Category category = createCategory(categoryId, "Feed");

		Long id = 1L;
		Post post = createPost(id, "Title", "Decscription", user, category);

		when(postRepository.findById(post.getId())).thenReturn(java.util.Optional.of(post));

		Post deletedPost = postService.delete(post.getId());

		assertNotNull(deletedPost);
		assertEquals("1", deletedPost.getId().toString());
		assertEquals("Title", deletedPost.getTitle());
		assertEquals("Decscription", deletedPost.getDescription());
		assertEquals(user, deletedPost.getUser());
		assertEquals(category, deletedPost.getCategory());
	}

	@Test
	public void testDeleteEntityNotFoundException() {
		Long userId = 1L;
		User user = createUser(userId, "Luka", "Zugaj", "lzugaj", "lzugaj@gmail.com", "lzugaj123");

		Long categoryId = 1L;
		Category category = createCategory(categoryId, "Feed");

		Long id = 1L;
		Post post = createPost(id, "Title", "Decscription", user, category);

		when(postRepository.findById(post.getId())).thenThrow(new EntityNotFoundException("Post", "id", post.getId().toString()));

		assertThrows(EntityNotFoundException.class, () -> postService.delete(post.getId()));
	}

	private User createUser(Long id, String firstName, String lastName, String username, String email, String password) {
		User user = new User();
		user.setId(id);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);
		return user;
	}

	private Category createCategory(Long id, String name) {
		Category category = new Category();
		category.setId(id);
		category.setName(name);
		category.setPosts(null);
		return category;
	}

	private Post createPost(Long postId, String title, String description, User user, Category category) {
		Post post = new Post();
		post.setId(postId);
		post.setTitle(title);
		post.setDescription(description);
		post.setCreatedDate(LocalDateTime.now());
		post.setUser(user);
		post.setCategory(category);
		return post;
	}
}
