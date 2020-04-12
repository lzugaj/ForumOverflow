package com.luv2code.forumoverflow.service;

import com.luv2code.forumoverflow.domain.UserStatus;

import java.util.List;

/**
 * Created by lzugaj on Friday, March 2020
 */

public interface UserStatusService {

	UserStatus findById(final Long id);

	UserStatus findByName(final String name);

	List<UserStatus> findAll();

}
