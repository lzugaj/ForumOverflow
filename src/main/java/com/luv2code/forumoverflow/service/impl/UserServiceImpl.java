package com.luv2code.forumoverflow.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.luv2code.forumoverflow.domain.Role;
import com.luv2code.forumoverflow.domain.User;
import com.luv2code.forumoverflow.domain.UserStatus;
import com.luv2code.forumoverflow.repository.UserRepository;
import com.luv2code.forumoverflow.service.EmailService;
import com.luv2code.forumoverflow.service.RoleService;
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

	public static final int MAX_NUMBER_OF_REPORTS = 3;

	private final PasswordEncoder passwordEncoder;

	private final UserRepository userRepository;

	private final UserStatusService userStatusService;

	private final RoleService roleService;

	private final EmailService emailService;

	@Autowired
	public UserServiceImpl(final PasswordEncoder passwordEncoder, final UserRepository userRepository,
			final UserStatusService userStatusService, final RoleService roleService, final EmailService emailService) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.userStatusService = userStatusService;
		this.roleService = roleService;
		this.emailService = emailService;
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

	// TODO: Ovog nece biti kad ubacim SpringSecuirty i JWT
	@Override
	public boolean isUserPasswordCorrect(final User user, String password) {
		return user.getPassword().equals(password);
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
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		log.info("Saving User with id: `{}`.", user.getId());

		activeUserStatus.setUser(Collections.singletonList(user));
		userRole.setUsers(Collections.singletonList(user));
		return user;
	}

	@Override
	public User findById(final Long id) {
		final User searchedUser = userRepository.findById(id).orElse(null);
		log.info("Searching User with id: `{}`.", id);
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
	public User update(final User oldUser, final User newUser) {
		final User updatedUser = updatedVariables(oldUser, newUser);
		userRepository.save(updatedUser);
		log.info("Updating User with id: `{}`.", updatedUser.getId());
		return updatedUser;
	}

	private User updatedVariables(final User oldUser, final User newUser) {
		if (newUser.getUsername() != null) {
			oldUser.setUsername(newUser.getUsername());
		}

		if (newUser.getEmail() != null) {
			oldUser.setEmail(newUser.getEmail());
		}

		if (newUser.getPassword() != null) {
			oldUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		}

		return oldUser;
	}

	@Override
	public User updateUserStatus(final User updatedUser, final UserStatus userStatus) {
		updatedUser.setUserStatus(userStatus);
		log.info("Updating UserStatus for User with id: `{}`", updatedUser.getId());

		handleChangedUserStatusManuallyByAdmin(updatedUser);
		userRepository.save(updatedUser);
		return updatedUser;
	}

	private void handleChangedUserStatusManuallyByAdmin(final User updatedUser) {
		emailService.sendUserStatusChangedNotification(updatedUser);
	}

	@Override
	public User delete(final User user) {
		userRepository.delete(user);
		log.info("Deleting User with id: `{}`.", user.getId());
		return user;
	}

	// TODO: Kako ovo napraviti?
	@Override
	public User report(final User user) {
		user.setBlockerCounter(user.getBlockerCounter() + 1);
		if (user.getBlockerCounter() == MAX_NUMBER_OF_REPORTS) {
			// TODO: Inactive for next 24 hours
			// TODO: Create Date variable that counts 24 hours
			handleBlockedUserStatus(user);
		} else {
			// TODO: Only admin can manually change user status
			handleInactiveUserStatus(user);
		}

		userRepository.save(user);
		log.info("Updating User with id: `{}`.", user.getId());
		return user;
	}

	private void handleBlockedUserStatus(final User user) {
		final UserStatus blockedStatus = userStatusService.findByName(Constants.BLOCKED);
		log.info("Successfully founded UserStatus with name: `{}`.", blockedStatus.getName());

		user.setUserStatus(blockedStatus);
		sendBlockerStatusNotification(user);
	}

	private void sendBlockerStatusNotification(final User user) {
		emailService.sendBlockerNotification(user);
	}

	private void handleInactiveUserStatus(final User user) {
		final UserStatus inactiveStatus = userStatusService.findByName(Constants.INACTIVE);
		log.info("Successfully founded UserStatus with name: `{}`.", inactiveStatus.getName());

		user.setUserStatus(inactiveStatus);
		sendInactiveStatusNotification(user);
	}

	private void sendInactiveStatusNotification(final User user) {
		emailService.sendInactiveNotification(user);
	}
}
