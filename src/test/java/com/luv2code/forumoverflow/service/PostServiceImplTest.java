package com.luv2code.forumoverflow.service;

import com.luv2code.forumoverflow.domain.Category;
import com.luv2code.forumoverflow.domain.Post;
import com.luv2code.forumoverflow.domain.User;
import com.luv2code.forumoverflow.repository.PostRepository;
import com.luv2code.forumoverflow.service.impl.PostServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Created by lzugaj on Friday, February 2020
 */

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class PostServiceImplTest {

	@Mock
	private PostRepository postRepository;

	@InjectMocks
	private PostServiceImpl postService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void save() {
		User user = createUser(1, "Luka", "Zugaj", "lzugaj", "luka.zugaj@gmail.com", "lzugaj123");
		Post post = createPost(1, "My first post", "Hello this is my first post");
		when(postRepository.save(post)).thenReturn(post);

		Post newPost = postService.save(user.getUsername(), post);
		assertNotNull(newPost);
		assertEquals("1", newPost.getId().toString());
		assertEquals("My first post", newPost.getTitle());
		assertEquals("Hello this is my first post", newPost.getDescription());
		assertEquals("lzugaj", newPost.getUser().getUsername());
		assertEquals("Feed", newPost.getCategory().getName());
	}

	@Test
	public void findById() {
		Post post = createPost(2, "Spring or C#", "What is better?");
		when(postRepository.findById(2L)).thenReturn(java.util.Optional.of(post));

		assertNotNull(post);
		assertEquals(post.getId().toString(), "2");
		assertEquals("Spring or C#", post.getTitle());
		assertEquals("What is better?", post.getDescription());
	}

	@Test
	public void findById_exception() {
		Post post = new Post();
		try {
			assertEquals(post.getId().toString(), "2");
		} catch (NullPointerException e) {
			throw new NullPointerException();
		}
	}

	@Test
	public void findAll() {
		Post firstPost = createPost(1, "React or Vue", "What is faster to learn?");
		Post secondPost = createPost(2, "Is JS the best?", "Is JS the best programming language?");
		Post thirdPost = createPost(3, "Java History", "When Java was created?");

		List<Post> posts = new ArrayList<>();
		posts.add(firstPost);
		posts.add(secondPost);
		posts.add(thirdPost);

		when(postRepository.findAll()).thenReturn(posts);

		assertEquals(3, posts.size());
		verify(postRepository, times(1)).findAll();
	}

	private User createUser(int id, String firstName, String lastName, String username, String email, String password) {
		User user = new User();
		user.setId((long) id);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);
		return user;
	}

	private Category createCategory(int id, String name) {
		Category category = new Category();
		category.setId((long) id);
		category.setName(name);
		category.setPosts(null);
		return category;
	}

	private Post createPost(int id, String title, String description) {
		User user = createUser(1, "Luka", "Zugaj", "lzugaj", "luka.zugaj@gmail.com", "lzugaj123");
		Category category = createCategory(1, "Feed");

		Post post = new Post();
		post.setId((long) id);
		post.setTitle(title);
		post.setDescription(description);
		post.setCreatedDate(LocalDateTime.now());
		post.setUser(user);
		post.setCategory(category);
		post.setComments(null);
		return post;
	}

}
