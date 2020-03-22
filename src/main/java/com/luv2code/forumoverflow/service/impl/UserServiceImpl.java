package com.luv2code.forumoverflow.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luv2code.forumoverflow.domain.User;
import com.luv2code.forumoverflow.domain.UserStatus;
import com.luv2code.forumoverflow.exception.EntityNotFoundException;
import com.luv2code.forumoverflow.repository.UserRepository;
import com.luv2code.forumoverflow.service.UserService;
import com.luv2code.forumoverflow.service.UserStatusService;
import com.luv2code.forumoverflow.util.Constants;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lzugaj on Friday, February 2020
 */

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final UserStatusService userStatusService;

	@Autowired
	public UserServiceImpl(final UserRepository userRepository, final UserStatusService userStatusService) {
		this.userRepository = userRepository;
		this.userStatusService = userStatusService;
	}

	@Override
	public User save(User user) {
		UserStatus activeUserStatus = userStatusService.findByName(Constants.ACTIVE);
		log.info("Successfully founded User status with name: `{}`.", activeUserStatus.getName());

		user.setBlockerCounter(0);
		user.setUserStatus(activeUserStatus);
		User newUser = userRepository.save(user);
		log.info("Saving User with id: `{}`.", user.getId());

		activeUserStatus.setUser(Collections.singletonList(newUser));
		return newUser;
	}

	@Override
	public User findById(Long id) {
		User searchedUser = userRepository.findById(id).orElse(null);
		log.info("Searching User with id: `{}`", id);
		return searchedUser;
	}

	@Override
	public User findByUsername(String username) {
		User searchedUser = userRepository.findByUsername(username)
				.orElseThrow(() -> new EntityNotFoundException("User", "username", username));
		log.info("Searching User with username: `{}`.", username);
		return searchedUser;
	}

	@Override
	public List<User> findAll() {
		List<User> users = userRepository.findAll();
		log.info("Searching all Users.");
		return users;
	}

	@Override
	public List<User> findAllThatContainsUsername(String username) {
		List<User> users = findAll();
		List<User> searchedUsers = new ArrayList<>();
		for (User user : users) {
			if (user.getUsername().contains(username)) {
				searchedUsers.add(user);
			}
		}

		log.info("Searching all Users that contains `{}` in username.", username);
		return searchedUsers;
	}

	@Override
	public User update(User oldUser, User newUser) {
		User updatedUser = setUpVariables(oldUser, newUser);
		userRepository.save(updatedUser);
		log.info("Updating User with id: `{}`.", updatedUser.getId());
		return updatedUser;
	}

	private User setUpVariables(User oldUser, User newUser) {
		User updatedUser = new User();
		updatedUser.setId(oldUser.getId());
		updatedUser.setFirstName(oldUser.getFirstName());
		updatedUser.setLastName(oldUser.getLastName());
		updatedUser.setUsername(newUser.getUsername());
		updatedUser.setEmail(newUser.getEmail());
		updatedUser.setPassword(oldUser.getPassword()); // TODO: User could change his password
		updatedUser.setBlockerCounter(oldUser.getBlockerCounter());
		updatedUser.setUserStatus(oldUser.getUserStatus());
		updatedUser.setPosts(oldUser.getPosts());
		updatedUser.setComments(oldUser.getComments());
		updatedUser.setRoles(oldUser.getRoles());

		log.info("Setting up variables for updated User with id: `{}`", oldUser.getId());
		return updatedUser;
	}

	@Override
	public User updateUserStatus(User user, UserStatus userStatus) {
		user.setUserStatus(userStatus);
		log.info("Change status for User `{}` to `{}`", user.getUsername(), userStatus.getName());

		userRepository.save(user);
		log.info("Updating User with id: `{}`.", user.getId());

		// TODO: Send notification if Active -> Inactive
		// TODO: Send notification if Active or Inactive -> Blocked

		return user;
	}

	@Override
	public User delete(User user) {
		userRepository.delete(user);
		log.info("Deleting User with id: `{}`.", user.getId());
		return user;
	}

	@Override
	public boolean isUsernameAlreadyUsed(User user) {
		for (User searchedUser : findAll()) {
			if (searchedUser.getUsername().equals(user.getUsername())) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isEmailAlreadyUsed(User user) {
		for (User searchedUser : findAll()) {
			if (searchedUser.getEmail().equals(user.getEmail())) {
				return true;
			}
		}

		return false;
	}
}
