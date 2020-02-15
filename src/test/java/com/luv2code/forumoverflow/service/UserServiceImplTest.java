package com.luv2code.forumoverflow.service;

import com.luv2code.forumoverflow.domain.Comment;
import com.luv2code.forumoverflow.domain.Post;
import com.luv2code.forumoverflow.domain.Role;
import com.luv2code.forumoverflow.domain.User;
import com.luv2code.forumoverflow.exception.EntityNotFoundException;
import com.luv2code.forumoverflow.exception.UserWithEmailAlreadyExistsException;
import com.luv2code.forumoverflow.exception.UserWithUsernameAlreadyExistsException;
import com.luv2code.forumoverflow.repository.UserRepository;
import com.luv2code.forumoverflow.service.impl.UserServiceImpl;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Created by lzugaj on Friday, February 2020
 */

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserServiceImpl userService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testSave() {
		Long id = 1L;
		User user = createUser(id, "Luka", "Zugaj", "lzugaj", "lzugaj@gmail.com", "luka123");

		when(userRepository.save(user)).thenReturn(user);

		User newUser = userService.save(user);

		assertNotNull(newUser);
		assertEquals("1", newUser.getId().toString());
		assertEquals("Luka", newUser.getFirstName());
		assertEquals("Zugaj", newUser.getLastName());
		assertEquals("lzugaj", newUser.getUsername());
		assertEquals("lzugaj@gmail.com",newUser.getEmail());
		assertEquals("luka123", newUser.getPassword());
	}

	@Test
	public void testSaveWhenUserWithUsernameAlreadyExistsException() {
		Long firstId = 1L;
		User firstUser = createUser(firstId, "Luka", "Zugaj", "palsi", "lzugaj@gmail.com", "lzugaj123");

		Long secondId = 2L;
		User secondUser = createUser(secondId, "Dalibor", "Torma", "palsi", "dtorma@gmail.com", "dtorma10");

		List<User> users = new ArrayList<>();
		users.add(firstUser);

		when(userRepository.findAll()).thenReturn(users);
		when(userRepository.save(secondUser)).thenThrow(new UserWithUsernameAlreadyExistsException("User", "username", secondUser.getUsername()));

		assertThrows(UserWithUsernameAlreadyExistsException.class, () -> userService.save(secondUser));
	}

	@Test
	public void testSaveWhenUserWithEmailAlreadyExistsException() {
		Long firstId = 1L;
		User firstUser = createUser(firstId, "Luka", "Zugaj", "lzugaj", "palsi@gmail.com", "lzugaj123");

		Long secondId = 2L;
		User secondUser = createUser(secondId, "Dalibor", "Torma", "dtorma", "palsi@gmail.com", "dtorma10");

		List<User> users = new ArrayList<>();
		users.add(firstUser);

		when(userRepository.findAll()).thenReturn(users);
		when(userRepository.save(secondUser)).thenThrow(new UserWithEmailAlreadyExistsException("User", "email", secondUser.getEmail()));

		assertThrows(UserWithEmailAlreadyExistsException.class, () -> userService.save(secondUser));
	}

	@Test
	public void testFindByUsername() {
		Long id = 1L;
		User firstUser = createUser(id, "Dalibor", "Torma", "palsi", "dtorma@gmail.com", "dtorma10");

		when(userRepository.findByUsername(firstUser.getUsername())).thenReturn(firstUser);

		String searchedUsername = "palsi";
		User searchedUser = userService.findByUsername(searchedUsername);

		assertNotNull(searchedUser);
		assertEquals("1", searchedUser.getId().toString());
		assertEquals("Dalibor", searchedUser.getFirstName());
		assertEquals("Torma", searchedUser.getLastName());
		assertEquals("palsi", searchedUser.getUsername());
		assertEquals("dtorma@gmail.com", searchedUser.getEmail());
		assertEquals("dtorma10", searchedUser.getPassword());
	}

	@Test
	public void testFindByUsernameEntityNotFoundException() {
		String searchedUser = "lzugaj";

		when(userRepository.findByUsername(searchedUser)).thenThrow(new EntityNotFoundException("User", "username", searchedUser));

		assertThrows(EntityNotFoundException.class, () -> userService.findByUsername(searchedUser));
	}

	@Test
	public void testFindAll() {
		Long firstId = 1L;
		User firstUser = createUser(firstId, "Luka", "Zugaj", "lzugaj", "lzugaj@gmail.com", "lzugaj123");

		Long secondId = 2L;
		User secondUser = createUser(secondId, "Dalibor", "Torma", "palsi", "dtorma@gmail.com", "dtorma10");

		Long thirdId = 3L;
		User thirdUser = createUser(thirdId, "Domagoj", "Cep", "dcep", "dcep@gmail.com", "dcpe12");

		List<User> users = new ArrayList<>();
		users.add(firstUser);
		users.add(secondUser);
		users.add(thirdUser);

		when(userRepository.findAll()).thenReturn(users);

		List<User> newUsers = userService.findAll();

		assertEquals(3, users.size());
		assertEquals(3, newUsers.size());
		verify(userRepository, times(1)).findAll();
	}

	private Post createPost(Long id, String title, String description, User user) {
		Post post = new Post();
		post.setId(id);
		post.setTitle(title);
		post.setDescription(description);
		post.setCreatedDate(LocalDateTime.now());
		post.setUser(user);
		post.setComments(null);
		post.setCategory(null);
		return post;
	}

	private Comment createComment(Long id, String description, User user, Post post) {
		Comment comment = new Comment();
		comment.setId(id);
		comment.setDescription(description);
		comment.setCreatedDate(LocalDateTime.now());
		comment.setUser(user);
		comment.setPost(post);
		return comment;
	}

	private Role createRole(Long id, String name, List<User> users) {
		Role role = new Role();
		role.setId(id);
		role.setName(name);
		role.setUsers(users);
		return role;
	}

	private User createUser(Long id, String firstName, String lastName, String username, String email, String password) {
		User user = new User();
		user.setId(id);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);

		Long firstPostId = 1L;
		Post firstPost = createPost(firstPostId, "Title", "Description", user);

		Long secondPostId = 2L;
		Post secondPost = createPost(secondPostId, "Naslov", "Opis", user);

		List<Post> posts = new ArrayList<>();
		posts.add(firstPost);
		posts.add(secondPost);

		Long firstCommentId = 1L;
		Comment firstComment = createComment(firstCommentId, "Comment", user, firstPost);

		Long secondCommentId = 2L;
		Comment secondComment = createComment(secondCommentId, "Komentar", user, secondPost);

		List<Comment> comments = new ArrayList<>();
		comments.add(firstComment);
		comments.add(secondComment);

		List<User> users = new ArrayList<>();
		users.add(user);

		Long firstRoleId = 1L;
		Role firstRole = createRole(firstRoleId, "Admin", users);

		Long secondRoleId = 1L;
		Role secondRole = createRole(secondRoleId, "User", users);

		List<Role> roles = new ArrayList<>();
		roles.add(firstRole);
		roles.add(secondRole);

		user.setPosts(posts);
		user.setComments(comments);
		user.setRoles(roles);
		return user;
	}
}
