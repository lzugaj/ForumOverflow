package com.luv2code.forumoverflow.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luv2code.forumoverflow.domain.Role;
import com.luv2code.forumoverflow.domain.User;
import com.luv2code.forumoverflow.domain.UserStatus;
import com.luv2code.forumoverflow.domain.type.RoleType;
import com.luv2code.forumoverflow.repository.UserRepository;
import com.luv2code.forumoverflow.service.RoleService;
import com.luv2code.forumoverflow.service.UserService;
import com.luv2code.forumoverflow.service.UserStatusService;
import com.luv2code.forumoverflow.util.Constants;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lzugaj on Friday, February 2020
 */

@Slf4j // TODO: Da li je potrebno sve logirati
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final UserStatusService userStatusService;

	private final RoleService roleService;

	@Autowired
	public UserServiceImpl(final UserRepository userRepository, final UserStatusService userStatusService,
			final RoleService roleService) {
		this.userRepository = userRepository;
		this.userStatusService = userStatusService;
		this.roleService = roleService;
	}

	@Override
	public User save(final User user) {
		final UserStatus activeUserStatus = userStatusService.findByName(Constants.ACTIVE);
		log.info("Successfully founded User status with name: `{}`.", activeUserStatus.getName());

		final Role userRole = roleService.findByName(Constants.USER);
		log.info("Successfully founded Role with name: `{}`.", userRole.getName());

		user.setBlockerCounter(0);
		user.setUserStatus(activeUserStatus);
		user.setRoles(Collections.singletonList(userRole));
		userRepository.save(user);
		log.info("Saving User with id: `{}`.", user.getId());

		activeUserStatus.setUser(Collections.singletonList(user));
		userRole.setUsers(Collections.singletonList(user));
		return user;
	}

	@Override
	public User findById(final Long id) {
		final User searchedUser = userRepository.findById(id).orElse(null);
		log.info("Searching User with id: `{}`", id);
		return searchedUser;
	}

	@Override
	public User findByUsername(final String username) {
		final User searchedUser = userRepository.findByUsername(username).orElse(null);
		log.info("Searching User with username: `{}`.", username);
		return searchedUser;
	}

	@Override
	public List<User> findAll() {
		final List<User> users = userRepository.findAll();
		log.info("Searching all Users.");
		return users;
	}

	@Override
	public List<User> findAllThatContainsUsername(final String username) {
		log.info("Searching all Users that contains `{}` in username.", username);
		return findAll().stream()
				.filter(user -> user.getUsername().toLowerCase().contains(username.toLowerCase()))
				.collect(Collectors.toList());
	}

	@Override
	public User update(final User oldUser, final User newUser) {
		final User updatedUser = setUpVariables(oldUser, newUser);
		userRepository.save(updatedUser);
		log.info("Updating User with id: `{}`.", updatedUser.getId());
		return updatedUser;
	}

	// TODO: User could change his password also
	private User setUpVariables(final User oldUser, final User newUser) {
		oldUser.setUsername(newUser.getUsername());
		oldUser.setEmail(newUser.getEmail());
		return oldUser;
	}

	@Override
	public User updateUserStatus(final User user, final UserStatus userStatus) {
		user.setUserStatus(userStatus);
		log.info("Change status for User `{}` to `{}`", user.getUsername(), userStatus.getName());

		userRepository.save(user);
		log.info("Updating User with id: `{}`.", user.getId());

		// TODO: Send notification if Active -> Inactive
		// TODO: Send notification if Active or Inactive -> Blocked

		return user;
	}

	@Override
	public User delete(final User user) {
		userRepository.delete(user);
		log.info("Deleting User with id: `{}`.", user.getId());
		return user;
	}

	@Override
	public boolean isUsernameAlreadyUsed(final User user) {
		return findAll().stream()
				.anyMatch(searchedUser -> searchedUser.getUsername().equals(user.getUsername()));
	}

	@Override
	public boolean isEmailAlreadyUsed(final User user) {
		return findAll().stream()
				.anyMatch(searchedUser -> searchedUser.getEmail().equals(user.getEmail()));
	}

	@Override
	public boolean isUserPasswordCorrect(final User user, String password) {
		return user.getPassword().equals(password);
	}
}
