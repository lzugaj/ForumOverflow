package com.luv2code.forumoverflow.service.impl;

import com.luv2code.forumoverflow.domain.User;
import com.luv2code.forumoverflow.exception.EntityNotFoundException;
import com.luv2code.forumoverflow.exception.UserWithEmailAlreadyExistsException;
import com.luv2code.forumoverflow.exception.UserWithUsernameAlreadyExistsException;
import com.luv2code.forumoverflow.repository.UserRepository;
import com.luv2code.forumoverflow.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lzugaj on Friday, February 2020
 */

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User save(User user) {
		if (isUsernameAlreadyUsed(user)) {
			log.info("User with username(´{}´) already exists.", user.getUsername());
			throw new UserWithUsernameAlreadyExistsException("User", "username", user.getUsername());
		} else if (isEmailAlreadyUser(user)) {
			log.info("User with email(`{}`) already exists.", user.getEmail());
			throw new UserWithEmailAlreadyExistsException("User", "email", user.getEmail());
		} else {
			User newUser = userRepository.save(user);
			log.info("Saving User with id: `{}`.", user.getId());
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

	private boolean isEmailAlreadyUser(User user) {
		List<User> users = findAll();
		for (User searchedUser : users) {
			return searchedUser.getEmail().equals(user.getEmail());
		}

		return false;
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
}
