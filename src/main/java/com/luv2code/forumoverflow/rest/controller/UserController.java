package com.luv2code.forumoverflow.rest.controller;

import com.luv2code.forumoverflow.config.constants.Constants;
import com.luv2code.forumoverflow.domain.User;
import com.luv2code.forumoverflow.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lzugaj on Wednesday, February 2020
 */

@Slf4j
@Api(value = "User Controller")
@RestController
@RequestMapping(path = "/user",
		produces = Constants.MEDIA_TYPE_FORUM_OVERFLOW_API_V1_VALUE)
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(final UserService userService) {
		this.userService = userService;
	}

	@ApiOperation(value = "Create user")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully created user")
	})
	@PostMapping(
			consumes = Constants.MEDIA_TYPE_FORUM_OVERFLOW_API_V1_VALUE,
			produces = Constants.MEDIA_TYPE_FORUM_OVERFLOW_API_V1_VALUE
	)
	public ResponseEntity<User> save(@RequestBody User user) {
		User newUser = userService.save(user);
		log.info("Successfully saved User with id: `{}`.", newUser.getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
	}
}
