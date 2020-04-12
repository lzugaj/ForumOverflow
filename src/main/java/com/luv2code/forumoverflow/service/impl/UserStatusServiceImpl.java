package com.luv2code.forumoverflow.service.impl;

import com.luv2code.forumoverflow.domain.UserStatus;
import com.luv2code.forumoverflow.repository.UserStatusRepository;
import com.luv2code.forumoverflow.service.UserStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lzugaj on Friday, March 2020
 */

@Slf4j
@Service
public class UserStatusServiceImpl implements UserStatusService {

	private final UserStatusRepository userStatusRepository;

	@Autowired
	public UserStatusServiceImpl(final UserStatusRepository userStatusRepository) {
		this.userStatusRepository = userStatusRepository;
	}

	@Override
	public UserStatus findById(final Long id) {
		final UserStatus userStatus = userStatusRepository.findById(id).orElse(null);
		log.info("Searching UserStatus with id: `{}`.", id);
		return userStatus;
	}

	@Override
	public UserStatus findByName(final String name) {
		final UserStatus userStatus = userStatusRepository.findByName(name).orElse(null);
		log.info("Searching UserStatus with name: `{}`.", name);
		return userStatus;
	}

	@Override
	public List<UserStatus> findAll() {
		final List<UserStatus> userStatuses = userStatusRepository.findAll();
		log.info("Searching all UserStatuses.");
		return userStatuses;
	}
}
