package com.luv2code.forumoverflow.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.forumoverflow.domain.User;
import com.luv2code.forumoverflow.domain.UserStatus;
import com.luv2code.forumoverflow.rest.controller.UserController;
import com.luv2code.forumoverflow.service.UserService;

/**
 * Created by lzugaj on Saturday, February 2020
 */

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private UserService userService;

	@Test
	public void testSave() throws Exception {
		Long id = 1L;
		User user = createUser(id, "Luka", "Žugaj", "lzugaj", "lzugaj@gmail.com", "#Lzugaj11");

		when(userService.isUsernameAlreadyUsed(user)).thenReturn(false);
		when(userService.isEmailAlreadyUsed(user)).thenReturn(false);
		when(userService.save(user)).thenReturn(user);

		this.mockMvc
				.perform(
						post("/user")
							.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
							.content(objectMapper.writeValueAsString(user))
				)
				.andExpect(status().isCreated());
	}

	@Test
	public void testSaveUsernameAlreadyExistsBadRequest() throws Exception {
		Long id = 1L;
		User user = createUser(id, "Luka", "Žugaj", "lzugaj", "lzugaj@gmail.com", "#Lzugaj11");

		when(userService.isUsernameAlreadyUsed(user)).thenReturn(true);

		this.mockMvc
				.perform(
						post("/user")
								.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
								.content(objectMapper.writeValueAsString(user))
				)
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testSaveEmailAlreadyExistsBadRequest() throws Exception {
		Long id = 1L;
		User user = createUser(id, "Luka", "Žugaj", "lzugaj", "lzugaj@gmail.com", "#Lzugaj11");

		when(userService.isEmailAlreadyUsed(user)).thenReturn(true);

		this.mockMvc
				.perform(
						post("/user")
								.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
								.content(objectMapper.writeValueAsString(user))
				)
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testFindById() throws Exception {
		Long id = 1L;
		User user = createUser(id, "Dalibor", "Torma", "dtorma", "dtorma@gmail.com", "10dtorma");

		when(userService.findById(user.getId())).thenReturn(user);

		this.mockMvc
				.perform(
						get("/user/{id}", user.getId())
							.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
							.content(objectMapper.writeValueAsString(user))
				)
				.andExpect(status().isOk());
	}

	@Test
	public void testFindByIdNotFound() throws Exception {
		Long id = 1L;
		User user = createUser(id, "Dalibor", "Torma", "dtorma", "dtorma@gmail.com", "10dtorma");

		when(userService.findById(user.getId())).thenReturn(null);

		this.mockMvc
				.perform(
						get("/user/{id}", user.getId())
							.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
							.content(objectMapper.writeValueAsString(user))
				)
				.andExpect(status().isNotFound());
	}

	@Test
	public void testFindAll() throws Exception {
		Long firstUserId = 1L;
		User firstUser = createUser(firstUserId, "Luka", "Žugaj", "lzugaj", "lzugaj@gmail.com", "#Lzugaj11");

		Long secondUserId = 2L;
		User secondUser = createUser(secondUserId, "Dalibor", "Torma", "dtorma", "dtorma@gmail.com", "10dtorma");

		List<User> users = new ArrayList<>();
		users.add(firstUser);
		users.add(secondUser);

		when(userService.findAll()).thenReturn(users);

		this.mockMvc
				.perform(
						get("/user")
							.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
							.content(objectMapper.writeValueAsString(users))
				)
				.andExpect(status().isOk());
	}

	@Test
	public void testFindAllByUsername() throws Exception {
		Long firstUserId = 1L;
		User firstUser = createUser(firstUserId, "Luka", "Žugaj", "lzugaj", "lzugaj@gmail.com", "#Lzugaj11");

		Long secondUserId = 2L;
		User secondUser = createUser(secondUserId, "Grgur", "Žugaj", "gzugaj", "gzugaj@gmail.com", "grgo1234");

		List<User> users = new ArrayList<>();
		users.add(firstUser);
		users.add(secondUser);

		String username = "zugaj";
		when(userService.findAllThatContainsUsername(username)).thenReturn(users);

		this.mockMvc
				.perform(
						get("/user/username/{username}", username)
							.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
							.content(objectMapper.writeValueAsString(users))
				)
				.andExpect(status().isOk());
	}

	@Test
	public void testUpdate() throws Exception {
		Long firstUserId = 1L;
		User firstUser = createUser(firstUserId, "Luka", "Žugaj", "lzugaj", "lzugaj@gmail.com", "#Lzugaj11");

		Long secondUserId = 2L;
		User secondUser = createUser(secondUserId, "Dalibor", "Torma", "dtorma", "dtorma@gmail.com", "10dtorma");

		when(userService.findById(firstUser.getId())).thenReturn(firstUser);
		when(userService.isUsernameAlreadyUsed(secondUser)).thenReturn(false);
		when(userService.isEmailAlreadyUsed(secondUser)).thenReturn(false);
		when(userService.update(firstUser, secondUser)).thenReturn(secondUser);

		this.mockMvc
				.perform(
						put("/user/{id}", firstUser.getId())
							.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
							.content(objectMapper.writeValueAsString(secondUser))
				)
				.andExpect(status().isOk());
	}

	@Test
	public void testUpdateUsernameAlreadyUsedBadRequest() throws Exception {
		Long firstUserId = 1L;
		User firstUser = createUser(firstUserId, "Luka", "Žugaj", "lzugaj", "lzugaj@gmail.com", "#Lzugaj11");

		Long secondUserId = 2L;
		User secondUser = createUser(secondUserId, "Dalibor", "Torma", "dtorma", "dtorma@gmail.com", "10dtorma");

		when(userService.findById(firstUser.getId())).thenReturn(firstUser);
		when(userService.isUsernameAlreadyUsed(secondUser)).thenReturn(true);

		this.mockMvc
				.perform(
						put("/user/{id}", firstUser.getId())
								.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
								.content(objectMapper.writeValueAsString(secondUser))
				)
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testUpdateEmailAlreadyUsedBadRequest() throws Exception {
		Long firstUserId = 1L;
		User firstUser = createUser(firstUserId, "Luka", "Žugaj", "lzugaj", "lzugaj@gmail.com", "#Lzugaj11");

		Long secondUserId = 2L;
		User secondUser = createUser(secondUserId, "Dalibor", "Torma", "dtorma", "dtorma@gmail.com", "10dtorma");

		when(userService.findById(firstUser.getId())).thenReturn(firstUser);
		when(userService.isEmailAlreadyUsed(secondUser)).thenReturn(true);

		this.mockMvc
				.perform(
						put("/user/{id}", firstUser.getId())
								.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
								.content(objectMapper.writeValueAsString(secondUser))
				)
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testUpdateNotFound() throws Exception {
		Long firstUserId = 1L;
		User firstUser = createUser(firstUserId, "Luka", "Žugaj", "lzugaj", "lzugaj@gmail.com", "#Lzugaj11");

		Long secondUserId = 2L;
		User secondUser = createUser(secondUserId, "Dalibor", "Torma", "dtorma", "dtorma@gmail.com", "10dtorma");

		when(userService.findById(firstUser.getId())).thenReturn(null);

		this.mockMvc
				.perform(
						put("/user/{id}", firstUser.getId())
								.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
								.content(objectMapper.writeValueAsString(secondUser))
				)
				.andExpect(status().isNotFound());
	}

	@Test
	public void testUpdateUserStatus() throws Exception {
		Long firstUserStatusId = 1L;
		UserStatus firstUserStatus = createUserStatus(firstUserStatusId, "ACTIVE");

		Long secondUserStatusId = 2L;
		UserStatus secondUserStatus = createUserStatus(secondUserStatusId, "INACTIVE");

		Long userId = 1L;
		User user = createUser(userId, "Luka", "Žugaj", "lzugaj", "lzugaj@gmail.com", "#Lzugaj11");
		user.setUserStatus(firstUserStatus);

		when(userService.findById(user.getId())).thenReturn(user);
		when(userService.updateUserStatus(user, secondUserStatus)).thenReturn(user);

		this.mockMvc
				.perform(
						put("/user/info/{id}", user.getId())
							.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
							.content(objectMapper.writeValueAsString(user))
				)
				.andExpect(status().isOk());
	}

	@Test
	public void testUpdateUserStatusNotFound() throws Exception {
		Long firstUserStatusId = 1L;
		UserStatus firstUserStatus = createUserStatus(firstUserStatusId, "ACTIVE");

		Long userId = 1L;
		User user = createUser(userId, "Luka", "Žugaj", "lzugaj", "lzugaj@gmail.com", "#Lzugaj11");
		user.setUserStatus(firstUserStatus);

		when(userService.findById(user.getId())).thenReturn(null);

		this.mockMvc
				.perform(
						put("/user/info/{id}", user.getId())
								.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
								.content(objectMapper.writeValueAsString(user))
				)
				.andExpect(status().isNotFound());
	}

	@Test
	public void testDelete() throws Exception {
		Long userId = 1L;
		User user = createUser(userId, "Luka", "Žugaj", "lzugaj", "lzugaj@gmail.com", "#Lzugaj11");

		when(userService.findById(user.getId())).thenReturn(user);
		when(userService.delete(user)).thenReturn(user);

		this.mockMvc
				.perform(
						delete("/user/{id}", user.getId())
							.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
							.content(objectMapper.writeValueAsString(user))
				)
				.andExpect(status().isOk());
	}

	@Test
	public void testDeleteNotFound() throws Exception {
		Long userId = 1L;
		User user = createUser(userId, "Luka", "Žugaj", "lzugaj", "lzugaj@gmail.com", "#Lzugaj11");

		when(userService.findById(user.getId())).thenReturn(null);

		this.mockMvc
				.perform(
						delete("/user/{id}", user.getId())
								.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
								.content(objectMapper.writeValueAsString(user))
				)
				.andExpect(status().isNotFound());
	}

	private UserStatus createUserStatus(Long id, String name) {
		UserStatus userStatus = new UserStatus();
		userStatus.setId(id);
		userStatus.setName(name);
		userStatus.setUser(null);
		return userStatus;
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
}
