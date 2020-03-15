package com.luv2code.forumoverflow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.forumoverflow.config.constants.Constants;
import com.luv2code.forumoverflow.domain.User;
import com.luv2code.forumoverflow.domain.UserStatus;
import com.luv2code.forumoverflow.rest.controller.UserController;
import com.luv2code.forumoverflow.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by lzugaj on Saturday, February 2020
 */

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	private UserService userService;

	@Test
	public void testSave() throws Exception {
		Long id = 1L;
		User user = createUser(id, "Tereza", "Zugaj", "tzugaj", "tzugaj@gmail.com", "teri");

		when(userService.save(user)).thenReturn(user);

		this.mockMvc
				.perform(
						post("/user")
							.contentType(MediaType.APPLICATION_JSON_VALUE)
							.content(objectMapper.writeValueAsString(user))
				)
				.andExpect(status().isCreated());
	}

	@Test
	public void testFindById() throws Exception {
		Long id = 1L;
		User user = createUser(id, "Dalibor", "Torma", "dtorma", "dtorma@gmail.com", "dtorma12");

		when(userService.findById(user.getId())).thenReturn(user);

		this.mockMvc
				.perform(
						get("/user/{id}", user.getId())
								.contentType(Constants.MEDIA_TYPE_FORUM_OVERFLOW_API_V1_VALUE)
								.content(objectMapper.writeValueAsString(user))
				)
				.andExpect(status().isOk());
	}

	@Test
	public void testFindAll() throws Exception {
		Long firstId = 1L;
		User firstUser = createUser(firstId, "Dalibor", "Torma", "dtorma", "dtorma@gmail.com", "dtorma12");

		Long secondId = 1L;
		User secondUser = createUser(secondId, "Luka", "Zugaj", "lzugaj", "lzugaj@gmail.com", "lzugaj1111");

		List<User> users = new ArrayList<>();
		users.add(firstUser);
		users.add(secondUser);

		when(userService.findAll()).thenReturn(users);

		this.mockMvc
				.perform(
						get("/user")
							.contentType(Constants.MEDIA_TYPE_FORUM_OVERFLOW_API_V1_VALUE)
							.content(objectMapper.writeValueAsString(users))
				)
				.andExpect(status().isOk());
	}

	@Test
	public void testFindAllThatContainsUsername() throws Exception {
		Long firstId = 1L;
		User firstUser = createUser(firstId, "Dalibor", "Torma", "dtorma", "dtorma@gmail.com", "dtorma12");

		Long secondId = 1L;
		User secondUser = createUser(secondId, "Luka", "Zugaj", "lzugaj", "lzugaj@gmail.com", "lzugaj1111");

		List<User> users = new ArrayList<>();
		users.add(firstUser);
		users.add(secondUser);

		String username = "zug";
		when(userService.findAllThatContainsUsername(username)).thenReturn(users);

		this.mockMvc
				.perform(
						get("/user/username/{username}", username)
								.contentType(Constants.MEDIA_TYPE_FORUM_OVERFLOW_API_V1_VALUE)
								.content(objectMapper.writeValueAsString(users))
				)
				.andExpect(status().isOk());
	}

	@Test
	public void testUpdate() throws Exception {
		Long id = 1L;
		User user = createUser(id, "Dalibor", "Torma", "dtorma", "dtorma@gmail.com", "dtorma12");

		when(userService.update(id, user)).thenReturn(user);

		this.mockMvc
				.perform(
						put("/user/{id}", user.getId())
								.contentType(MediaType.APPLICATION_JSON_VALUE)
								.content(objectMapper.writeValueAsString(user))
				)
				.andExpect(status().isOk());
	}

	@Test
	public void testUpdateUserStatus() throws Exception {
		UserStatus firstStatus = new UserStatus(1L, "ACTIVE", null);
		UserStatus secondStatus = new UserStatus(1L, "BLOCKED", null);

		Long id = 1L;
		String username = "lzugaj";
		User user = new User(id, "Luka", "Zugaj", "lzugaj", "luka.zugaj@gmail.com", "luka123", 0, firstStatus, null, null, null);

		when(userService.updateUserStatus(username, secondStatus)).thenReturn(user);

		this.mockMvc
				.perform(
						put("/user/username/{username}", user.getUsername())
								.contentType(MediaType.APPLICATION_JSON_VALUE)
								.content(objectMapper.writeValueAsString(user))
				)
				.andExpect(status().isOk());
	}

	@Test
	public void testDelete() throws Exception {
		Long id = 1L;
		User user = createUser(id, "Dalibor", "Torma", "dtorma", "dtorma@gmail.com", "dtorma12");

		when(userService.delete(id)).thenReturn(user);

		this.mockMvc
				.perform(
						delete("/user/{id}", user.getId())
								.contentType(Constants.MEDIA_TYPE_FORUM_OVERFLOW_API_V1_VALUE)
								.content(objectMapper.writeValueAsString(user))
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
		user.setRoles(null);
		user.setPosts(null);
		user.setComments(null);
		return user;
	}
}
