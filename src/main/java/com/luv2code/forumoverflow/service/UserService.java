package com.luv2code.forumoverflow.service;

import com.luv2code.forumoverflow.domain.User;
import com.luv2code.forumoverflow.domain.UserStatus;

import java.util.List;
import java.util.Optional;

/**
 * Created by lzugaj on Friday, February 2020
 */

public interface UserService {

	User save(User user);

	User findById(Long id);

	User findByUsername(String username);

	List<User> findAll();

	List<User> findAllThatContainsUsername(String username); // TODO: Naziv metode?

	User update(User oldUser, User newUser);

	User updateUserStatus(User user, UserStatus userStatus);

	User delete(User user);

	boolean isUsernameAlreadyUsed(User user);

	boolean isEmailAlreadyUsed(User user);

}
