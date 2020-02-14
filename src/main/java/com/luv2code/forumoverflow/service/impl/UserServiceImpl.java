package com.luv2code.forumoverflow.service.impl;

import com.luv2code.forumoverflow.domain.User;
import com.luv2code.forumoverflow.exception.EntityNotFoundException;
import com.luv2code.forumoverflow.repository.UserRepository;
import com.luv2code.forumoverflow.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		User newUser = userRepository.save(user);
		log.info("Saving User with id: `{}`", user.getId());
		return newUser;
	}

	@Override
	public User findByUsername(String username) {
		User searchedUser = userRepository.findByUsername(username);
		log.info("Searching User with username: `{}`", username);
		if (searchedUser == null) {
			throw new EntityNotFoundException("User", "username", username);
		}

		return searchedUser;
	}
}
