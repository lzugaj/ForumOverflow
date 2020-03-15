package com.luv2code.forumoverflow.service.impl;

import com.luv2code.forumoverflow.domain.User;
import com.luv2code.forumoverflow.domain.UserStatus;
import com.luv2code.forumoverflow.exception.EntityNotFoundException;
import com.luv2code.forumoverflow.exception.UserWithEmailAlreadyExistsException;
import com.luv2code.forumoverflow.exception.UserWithUsernameAlreadyExistsException;
import com.luv2code.forumoverflow.repository.UserRepository;
import com.luv2code.forumoverflow.service.UserService;
import com.luv2code.forumoverflow.service.UserStatusService;
import com.luv2code.forumoverflow.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
		if (isUsernameAlreadyUsed(user)) {
			log.info("User with username(´{}´) already exists.", user.getUsername());
			throw new UserWithUsernameAlreadyExistsException("User", "username", user.getUsername());
		} else if (isEmailAlreadyUsed(user)) {
			log.info("User with email(`{}`) already exists.", user.getEmail());
			throw new UserWithEmailAlreadyExistsException("User", "email", user.getEmail());
		} else {
			UserStatus activeUserStatus = userStatusService.findByName(Constants.ACTIVE);
			log.info("Successfully founded User status with name: `{}`.", activeUserStatus.getName());

			user.setBlockerCounter(0);
			user.setUserStatus(activeUserStatus);
			User newUser = userRepository.save(user);
			log.info("Saving User with id: `{}`.", user.getId());

			activeUserStatus.setUser(Collections.singletonList(newUser));
			return newUser;
		}
	}

	private boolean isUsernameAlreadyUsed(User user) {
		List<User> users = findAll();
		for (User searchedUser : users) {
			return searchedUser.getUsername().equals(user.getUsername());
		}

		return false;
	}

	private boolean isEmailAlreadyUsed(User user) {
		List<User> users = findAll();
		for (User searchedUser : users) {
			return searchedUser.getEmail().equals(user.getEmail());
		}

		return false;
	}

	@Override
	public User findById(Long id) {
		User searchedUser = userRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("User", "id", id.toString()));
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
	public User update(Long id, User user) {
		User updatedUser = findById(id);
		log.info("Successfully founded User with id: `{}`.", id);

		setUpVariables(user, updatedUser);
		userRepository.save(updatedUser);

		log.info("Updating User with id: `{}`.", user.getId());
		return updatedUser;
	}

	private void setUpVariables(User user, User updatedUser) {
		updatedUser.setFirstName(updatedUser.getFirstName());
		updatedUser.setLastName(updatedUser.getLastName());
		updatedUser.setUsername(user.getUsername());
		updatedUser.setEmail(user.getEmail());
		updatedUser.setPassword(updatedUser.getPassword());
		updatedUser.setRoles(updatedUser.getRoles());
		updatedUser.setPosts(updatedUser.getPosts());
		updatedUser.setComments(updatedUser.getComments());
	}

	@Override
	public User updateUserStatus(String username, UserStatus userStatus) {
		User searchedUser = findByUsername(username);
		log.info("Successfully founded User with username: `{}`.", username);

		searchedUser.setUserStatus(userStatus);
		log.info("Change status for User `{}` to `{}`", searchedUser.getUsername(), searchedUser.getUserStatus());

		userRepository.save(searchedUser);
		log.info("Updating User with username: `{}`.", username);

		// TODO: Send notification if Active -> Inactive
		// TODO: Send notification if Active or Inactive -> Blocked

		return searchedUser;
	}

	@Override
	public User delete(Long id) {
		User searchedUser = findById(id);
		log.info("Successfully founded User with id: `{}`.", id);

		userRepository.delete(searchedUser);
		log.info("Deleting User with id: `{}`.", id);
		return searchedUser;
	}
}
