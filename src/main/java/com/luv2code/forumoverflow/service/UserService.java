package com.luv2code.forumoverflow.service;

import com.luv2code.forumoverflow.domain.User;
import com.luv2code.forumoverflow.domain.UserStatus;

import java.util.List;
import java.util.Optional;

/**
 * Created by lzugaj on Friday, February 2020
 */

public interface UserService {

	boolean isUsernameAlreadyUsed(final User user);

	boolean isEmailAlreadyUsed(final User user);

	boolean isUserPasswordCorrect(final User user, final String password);

	User save(final User user);

	User findById(final Long id);

	User findByUsername(final String username);

	List<User> findAll();

	User update(final User oldUser, final User newUser);

	User updateUserStatus(final User updatedUser, final UserStatus userStatus);

	User delete(final User user);

	User report(final User user);

}
