package com.luv2code.forumoverflow.rest.controller;

import com.luv2code.forumoverflow.config.constants.Constants;
import com.luv2code.forumoverflow.domain.User;
import com.luv2code.forumoverflow.domain.UserStatus;
import com.luv2code.forumoverflow.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> save(@RequestBody User user) {
		User newUser = userService.save(user);
		log.info("Successfully saved User with id: `{}`.", newUser.getId());
		return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
	}

	@ApiOperation(value = "Find user by id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully founded user"),
			@ApiResponse(code = 401, message = "You are not authorized to find the user"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to find is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to find is not found")
	})
	@GetMapping(path = "/{id}",
			produces = Constants.MEDIA_TYPE_FORUM_OVERFLOW_API_V1_VALUE)
	public ResponseEntity<User> findById(@PathVariable Long id) {
		User searchedUser = userService.findById(id);
		log.info("Successfully founded User with id: `{}`", id);
		return ResponseEntity.status(HttpStatus.OK).body(searchedUser);
	}

	@ApiOperation(value = "Find all users")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully founded all users"),
			@ApiResponse(code = 401, message = "You are not authorized to find all the users"),
			@ApiResponse(code = 403, message = "Accessing the resources you were trying to find is forbidden"),
			@ApiResponse(code = 404, message = "The resources you were trying to find is not found")
	})
	@GetMapping(produces = Constants.MEDIA_TYPE_FORUM_OVERFLOW_API_V1_VALUE)
	public ResponseEntity<List<User>> findAll() {
		List<User> searchedUsers = userService.findAll();
		log.info("Successfully found all Users.");
		return ResponseEntity.status(HttpStatus.OK).body(searchedUsers);
	}

	@ApiOperation(value = "Find all users by given username")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully founded all users by given username"),
			@ApiResponse(code = 401, message = "You are not authorized to find all the users by given username"),
			@ApiResponse(code = 403, message = "Accessing the resources you were trying to find is forbidden"),
			@ApiResponse(code = 404, message = "The resources you were trying to find is not found")
	})
	@GetMapping(path = "/username/{username}",
			produces = Constants.MEDIA_TYPE_FORUM_OVERFLOW_API_V1_VALUE)
	public ResponseEntity<List<User>> findAllByUsername(@PathVariable String username) {
		List<User> searchedUsers = userService.findAllThatContainsUsername(username);
		log.info("Successfully founded all Users that contains `{}` in username", username);
		return ResponseEntity.status(HttpStatus.OK).body(searchedUsers);
	}

	@ApiOperation(value = "Update user")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully updated user"),
			@ApiResponse(code = 401, message = "You are not authorized to update the user"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to update is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to update is not found")
	})
	@PutMapping(path = "/{id}",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
		User updatedUser = userService.update(id, user);
		log.info("Successfully updated User with id: `{}`", id);
		return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
	}

	@ApiOperation(value = "Update user status")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully updated user status"),
			@ApiResponse(code = 401, message = "You are not authorized to update the user status"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to update is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to update is not found")
	})
	@PutMapping(path = "/username/{username}",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> updateUserStatus(@PathVariable String username, @RequestBody UserStatus userStatus) {
		User updatedUser = userService.updateUserStatus(username, userStatus);
		log.info("Successfully updated User status for User with username: `{}`.", username);
		return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
	}

	@ApiOperation(value = "Delete user")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully deleted user"),
			@ApiResponse(code = 401, message = "You are not authorized to delete the user"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to delete is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to delete is not found")
	})
	@DeleteMapping(path = "/{id}",
			produces = Constants.MEDIA_TYPE_FORUM_OVERFLOW_API_V1_VALUE)
	public ResponseEntity<User> delete(@PathVariable Long id) {
		User deletedUser = userService.delete(id);
		log.info("Successfully deleted User with id: `{}`", deletedUser.getId());
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
