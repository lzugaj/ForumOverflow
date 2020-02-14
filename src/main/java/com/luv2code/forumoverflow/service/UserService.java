package com.luv2code.forumoverflow.service;

import com.luv2code.forumoverflow.domain.User;

/**
 * Created by lzugaj on Friday, February 2020
 */

public interface UserService {

	User save(User user);

	User findByUsername(String username);

}
