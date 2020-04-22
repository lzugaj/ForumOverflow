package com.luv2code.forumoverflow.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.forumoverflow.domain.Role;
import com.luv2code.forumoverflow.domain.User;
import com.luv2code.forumoverflow.domain.UserStatus;
import com.luv2code.forumoverflow.rest.controller.UserController;
import com.luv2code.forumoverflow.service.UserService;
import com.luv2code.forumoverflow.util.Constants;

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

	private UserStatus userStatus;

	private User firstUser;

	private User secondUser;

	private List<User> users;

	public static final String USERNAME = "zug";

	@BeforeEach
	public void setup() {
		userStatus = new UserStatus();
		userStatus.setId(1L);
		userStatus.setName(Constants.ACTIVE);

		Role role = new Role();
		role.setId(1L);
		role.setName(Constants.USER);

		firstUser = new User();
		firstUser.setId(1L);
		firstUser.setFirstName("Luka");
		firstUser.setLastName("Žugaj");
		firstUser.setUsername("lzugaj");
		firstUser.setEmail("lzugaj@racunarstvo.hr");
		firstUser.setPassword("test");

		secondUser = new User();
		secondUser.setId(2L);
		secondUser.setFirstName("Dalibor");
		secondUser.setLastName("Torma");
		secondUser.setUsername("dtorzug");
		secondUser.setEmail("dtorma@racunarstvo.hr");
		secondUser.setPassword("password");

		users = new ArrayList<>();
		users.add(firstUser);
		users.add(secondUser);
	}

	@Test
	public void testSave() throws Exception {
		Mockito.when(userService.isUsernameAlreadyUsed(firstUser)).thenReturn(false);
		Mockito.when(userService.isEmailAlreadyUsed(firstUser)).thenReturn(false);
		Mockito.when(userService.save(firstUser)).thenReturn(firstUser);
		this.mockMvc
				.perform(
						post("/user")
							.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
							.content(objectMapper.writeValueAsString(firstUser))
				)
				.andExpect(status().isCreated());
	}

	@Test
	public void testSaveUsernameAlreadyExistsBadRequest() throws Exception {
		Mockito.when(userService.isUsernameAlreadyUsed(firstUser)).thenReturn(true);
		this.mockMvc
				.perform(
						post("/user")
								.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
								.content(objectMapper.writeValueAsString(firstUser))
				)
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testSaveEmailAlreadyExistsBadRequest() throws Exception {
		Mockito.when(userService.isEmailAlreadyUsed(firstUser)).thenReturn(true);
		this.mockMvc
				.perform(
						post("/user")
								.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
								.content(objectMapper.writeValueAsString(firstUser))
				)
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testFindById() throws Exception {
		Mockito.when(userService.findById(secondUser.getId())).thenReturn(secondUser);
		this.mockMvc
				.perform(
						get("/user/{id}", secondUser.getId())
							.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
							.content(objectMapper.writeValueAsString(secondUser))
				)
				.andExpect(status().isOk());
	}

	@Test
	public void testFindByIdNotFound() throws Exception {
		Mockito.when(userService.findById(secondUser.getId())).thenReturn(null);
		this.mockMvc
				.perform(
						get("/user/{id}", secondUser.getId())
							.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
							.content(objectMapper.writeValueAsString(secondUser))
				)
				.andExpect(status().isNotFound());
	}

	@Test
	public void testFindAll() throws Exception {
		Mockito.when(userService.findAll()).thenReturn(users);
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
		Mockito.when(userService.findAll()).thenReturn(users);
		this.mockMvc
				.perform(
						get("/user/username/{username}", USERNAME)
							.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
							.content(objectMapper.writeValueAsString(users))
				)
				.andExpect(status().isOk());
	}

	@Test
	public void testUpdate() throws Exception {
		Mockito.when(userService.findById(firstUser.getId())).thenReturn(firstUser);
		Mockito.when(userService.isUsernameAlreadyUsed(secondUser)).thenReturn(false);
		Mockito.when(userService.isEmailAlreadyUsed(secondUser)).thenReturn(false);
		Mockito.when(userService.update(firstUser, secondUser)).thenReturn(secondUser);
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
		Mockito.when(userService.findById(firstUser.getId())).thenReturn(firstUser);
		Mockito.when(userService.isUsernameAlreadyUsed(secondUser)).thenReturn(true);
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
		Mockito.when(userService.findById(firstUser.getId())).thenReturn(firstUser);
		Mockito.when(userService.isEmailAlreadyUsed(secondUser)).thenReturn(true);
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
		Mockito.when(userService.findById(firstUser.getId())).thenReturn(null);
		this.mockMvc
				.perform(
						put("/user/{id}", firstUser.getId())
								.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
								.content(objectMapper.writeValueAsString(secondUser))
				)
				.andExpect(status().isNotFound());
	}

//	@Test
//	public void testUpdateUserStatus() throws Exception {
//		Long firstUserStatusId = 1L;
//		UserStatus firstUserStatus = createUserStatus(firstUserStatusId, "ACTIVE");
//
//		Long secondUserStatusId = 2L;
//		UserStatus secondUserStatus = createUserStatus(secondUserStatusId, "INACTIVE");
//
//		Long userId = 1L;
//		User user = createUser(userId, "Luka", "Žugaj", "lzugaj", "lzugaj@gmail.com", "#Lzugaj11");
//		user.setUserStatus(firstUserStatus);
//
//		when(userService.findById(user.getId())).thenReturn(user);
//		when(userService.updateUserStatus(user, secondUserStatus)).thenReturn(user);
//
//		this.mockMvc
//				.perform(
//						put("/user/info/{id}", user.getId())
//							.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
//							.content(objectMapper.writeValueAsString(user))
//				)
//				.andExpect(status().isOk());
//	}

//	@Test
//	public void testUpdateUserStatusNotFound() throws Exception {
//		Long firstUserStatusId = 1L;
//		UserStatus firstUserStatus = createUserStatus(firstUserStatusId, "ACTIVE");
//
//		Long userId = 1L;
//		User user = createUser(userId, "Luka", "Žugaj", "lzugaj", "lzugaj@gmail.com", "#Lzugaj11");
//		user.setUserStatus(firstUserStatus);
//
//		when(userService.findById(user.getId())).thenReturn(null);
//
//		this.mockMvc
//				.perform(
//						put("/user/info/{id}", user.getId())
//								.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
//								.content(objectMapper.writeValueAsString(user))
//				)
//				.andExpect(status().isNotFound());
//	}

	@Test
	public void testDelete() throws Exception {
		Mockito.when(userService.findById(firstUser.getId())).thenReturn(firstUser);
		Mockito.when(userService.delete(firstUser)).thenReturn(firstUser);
		this.mockMvc
				.perform(
						delete("/user/{id}", firstUser.getId())
							.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
							.content(objectMapper.writeValueAsString(firstUser))
				)
				.andExpect(status().isOk());
	}

	@Test
	public void testDeleteNotFound() throws Exception {
		Mockito.when(userService.findById(firstUser.getId())).thenReturn(null);
		this.mockMvc
				.perform(
						delete("/user/{id}", firstUser.getId())
								.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
								.content(objectMapper.writeValueAsString(firstUser))
				)
				.andExpect(status().isNotFound());
	}
}
