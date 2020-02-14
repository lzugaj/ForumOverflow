package com.luv2code.forumoverflow.rest.controller;

import com.luv2code.forumoverflow.domain.User;
import com.luv2code.forumoverflow.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lzugaj on Wednesday, February 2020
 */

@Slf4j
@RestController
@RequestMapping(path = "/user",
		consumes = MediaType.APPLICATION_JSON_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(final UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<User> save(@RequestBody User user) {
		User newUser = userService.save(user);
		log.info("Successfully saved User with id: `{}`.", newUser.getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
	}
}
