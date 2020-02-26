package com.luv2code.forumoverflow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.forumoverflow.domain.User;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
							.contentType(MediaType.APPLICATION_JSON)
							.content(objectMapper.writeValueAsString(user))
				)
				.andExpect(status().isCreated());
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
