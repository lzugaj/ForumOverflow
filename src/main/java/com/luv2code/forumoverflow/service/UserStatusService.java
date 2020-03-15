package com.luv2code.forumoverflow.service;

import com.luv2code.forumoverflow.domain.UserStatus;

import java.util.List;

/**
 * Created by lzugaj on Friday, March 2020
 */

public interface UserStatusService {

	UserStatus findById(Long id);

	UserStatus findByName(String name);

	List<UserStatus> findAll();

}
