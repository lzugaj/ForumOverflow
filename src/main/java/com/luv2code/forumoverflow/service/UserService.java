package com.luv2code.forumoverflow.service;

import com.luv2code.forumoverflow.domain.User;
import com.luv2code.forumoverflow.domain.UserStatus;

import java.util.List;

/**
 * Created by lzugaj on Friday, February 2020
 */

public interface UserService {

	User save(User user);

	User findById(Long id);

	User findByUsername(String username);

	List<User> findAll();

	List<User> findAllThatContainsUsername(String username); // TODO: Naziv metode?

	User update(Long id, User user);

	User updateUserStatus(String username, UserStatus userStatus);

	User delete(Long id);

}
