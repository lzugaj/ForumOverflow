package com.luv2code.forumoverflow.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.forumoverflow.domain.User;
import com.luv2code.forumoverflow.domain.UserStatus;
import com.luv2code.forumoverflow.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by lzugaj on Wednesday, February 2020
 */

@Slf4j
@Api(value = "User Controller")
@RequestMapping(
		path = "/user",
		produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(final UserService userService) {
		this.userService = userService;
	}

	@ApiOperation(value = "Create")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Successfully created user")
	})
	@PostMapping(
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> save(@RequestBody User user) {
		if (userService.isUsernameAlreadyUsed(user)) {
			log.info("User with username `{}` already exists.", user.getUsername());
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} else if (userService.isEmailAlreadyUsed(user)) {
			log.info("User with email `{}` already exists.", user.getEmail());
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} else {
			User newUser = userService.save(user);
			log.info("Successfully create new User.");
			return new ResponseEntity<>(newUser, HttpStatus.CREATED);
		}
	}

	@ApiOperation(value = "Find by id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully founded user"),
			@ApiResponse(code = 401, message = "You are not authorized to find the user"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to find is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to find is not found")
	})
	@GetMapping(
			path = "/{id}",
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> findById(@PathVariable Long id) {
		Optional<User> searchedUser = Optional.ofNullable(userService.findById(id));
		if (searchedUser.isPresent()) {
			log.info("Successfully founded User with id: `{}`", id);
			return new ResponseEntity<>(searchedUser, HttpStatus.OK);
		} else {
			log.info("User with id `{}` wasn't founded.", id);
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	// TODO: Mislim da ovo nije dobra metoda?
	// TODO: Ovdje trebam implementirati Spring Security + JWT
	@GetMapping(
			path = "/search/username/{username}/password/{password}",
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> findByUsername(@PathVariable String username, @PathVariable String password) {
		Optional<User> searchedUser = Optional.ofNullable(userService.findByUsername(username));
		if (searchedUser.isPresent()) {
			if (userService.isUserPasswordCorrect(searchedUser.get(), password)) {
				log.info("Successfully founded User with username: `{}`", username);
				return new ResponseEntity<>(searchedUser, HttpStatus.OK);
			} else {
				log.info("User with username `{}` already exists", username);
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
		} else {
			log.info("User with username `{}` was't founded", username);
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation(value = "Find all")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully founded all users"),
			@ApiResponse(code = 401, message = "You are not authorized to find all the users"),
			@ApiResponse(code = 403, message = "Accessing the resources you were trying to find is forbidden"),
			@ApiResponse(code = 404, message = "The resources you were trying to find is not found")
	})
	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> findAll() {
		List<User> searchedUsers = userService.findAll();
		log.info("Successfully found all Users.");
		return new ResponseEntity<>(searchedUsers, HttpStatus.OK);
	}

	@ApiOperation(value = "Find all by given username")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully founded all users by given username"),
			@ApiResponse(code = 401, message = "You are not authorized to find all the users by given username"),
			@ApiResponse(code = 403, message = "Accessing the resources you were trying to find is forbidden"),
			@ApiResponse(code = 404, message = "The resources you were trying to find is not found")
	})
	@GetMapping(
			path = "/username/{username}",
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> findAllByUsername(@PathVariable String username) {
		List<User> searchedUsers = userService.findAllThatContainsUsername(username);
		log.info("Successfully founded all Users that contains `{}` in username", username);
		return new ResponseEntity<>(searchedUsers, HttpStatus.OK);
	}

	@ApiOperation(value = "Update")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully updated user"),
			@ApiResponse(code = 401, message = "You are not authorized to update the user"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to update is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to update is not found")
	})
	@PutMapping(
			path = "/{id}",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody User user) {
		Optional<User> searchedUser = Optional.ofNullable(userService.findById(id));
		if (searchedUser.isPresent()) {
			if (userService.isUsernameAlreadyUsed(user)) {
				log.info("User with username `{}` already exists.", user.getUsername());
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			} else if (userService.isEmailAlreadyUsed(user)) {
				log.info("User with email `{}` already exists.", user.getEmail());
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			} else {
				User updatedUser = userService.update(searchedUser.get(), user);
				log.info("Successfully updated User with id: `{}`", id);
				return new ResponseEntity<>(updatedUser, HttpStatus.OK);
			}
		} else {
			log.info("User with id `{}` wasn't founded.", id);
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation(value = "Update status")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully updated user status"),
			@ApiResponse(code = 401, message = "You are not authorized to update the user status"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to update is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to update is not found")
	})
	@PutMapping(
			path = "/info/{id}",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> updateUserStatus(@PathVariable Long id, @RequestBody UserStatus userStatus) {
		Optional<User> searchedUser = Optional.ofNullable(userService.findById(id));
		if (searchedUser.isPresent()) {
			User updatedUser = userService.updateUserStatus(searchedUser.get(), userStatus);
			log.info("Successfully update User status to `{}` for User with id: `{}`", userStatus.getName(), id);
			return new ResponseEntity<>(updatedUser, HttpStatus.OK);
		} else {
			log.info("User with id `{}` wasn't founded.", id);
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation(value = "Delete")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully delete user"),
			@ApiResponse(code = 401, message = "You are not authorized to delete the user"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to delete is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to delete is not found")
	})
	@DeleteMapping(
			path = "/{id}",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<User> searchedUser = Optional.ofNullable(userService.findById(id));
		if (searchedUser.isPresent()) {
			User deletedUser = userService.delete(searchedUser.get());
			log.info("Successfully deleted User with id: `{}`", deletedUser.getId());
			return new ResponseEntity<>(deletedUser, HttpStatus.OK);
		} else {
			log.info("User with id `{}` wasn't founded.", id);
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
}
