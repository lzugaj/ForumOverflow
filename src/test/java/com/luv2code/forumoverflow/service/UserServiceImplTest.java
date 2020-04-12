package com.luv2code.forumoverflow.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.luv2code.forumoverflow.domain.User;
import com.luv2code.forumoverflow.domain.UserStatus;
import com.luv2code.forumoverflow.exception.EntityNotFoundException;
import com.luv2code.forumoverflow.repository.UserRepository;
import com.luv2code.forumoverflow.service.impl.UserServiceImpl;
import com.luv2code.forumoverflow.util.Constants;

/**
 * Created by lzugaj on Friday, February 2020
 */

@SpringBootTest
public class UserServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserStatusService userStatusService;

	@InjectMocks
	private UserServiceImpl userService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testSave() {
		Long userStatusId = 1L;
		UserStatus userStatus = createUserStatus(userStatusId, "ACTIVE");

		Long userId = 1L;
		User user = createUser(userId, "Luka", "Žugaj", "lzugaj", "lzugaj@gmail.com", "#Lzugaj12312");

		when(userStatusService.findByName(Constants.ACTIVE)).thenReturn(userStatus);
		when(userRepository.save(user)).thenReturn(user);

		User newUser = userService.save(user);

		assertNotNull(newUser);
		assertEquals("1", newUser.getId().toString());
		assertEquals("Luka", newUser.getFirstName());
		assertEquals("Žugaj", newUser.getLastName());
		assertEquals("lzugaj", newUser.getUsername());
		assertEquals("lzugaj@gmail.com", newUser.getEmail());
		assertEquals("#Lzugaj12312", newUser.getPassword());
		assertEquals(0, newUser.getBlockerCounter());
		assertEquals("ACTIVE", newUser.getUserStatus().getName());
	}

	@Test
	public void testFindById() {
		Long userId = 1L;
		User user = createUser(userId, "Dalibor", "Torma", "dtorma", "dtorma@gmail.com", "torma10");

		when(userRepository.findById(user.getId())).thenReturn(java.util.Optional.of(user));

		User searchedUser = userService.findById(user.getId());

		assertNotNull(searchedUser);
		assertEquals("1", searchedUser.getId().toString());
		assertEquals("Dalibor", searchedUser.getFirstName());
		assertEquals("Torma", searchedUser.getLastName());
		assertEquals("dtorma", searchedUser.getUsername());
		assertEquals("dtorma@gmail.com", searchedUser.getEmail());
		assertEquals("torma10", searchedUser.getPassword());
	}

	@Test
	public void testFindByIdNullPointerException() {
		User user = new User(1L, "Ivan", "Žugaj", "izugaj", "izugaj@gmail.com", "ivan007", 2, null, null, null, null);

		when(userRepository.findById(user.getId())).thenThrow(new NullPointerException());

		assertThrows(NullPointerException.class, () -> userService.findById(user.getId()));
	}

	@Test
	public void testFindByUsername() {
		Long userId = 1L;
		User user = createUser(userId, "Dalibor", "Torma", "dtorma", "dtorma@gmail.com", "torma10");

		when(userRepository.findByUsername(user.getUsername())).thenReturn(java.util.Optional.of(user));

		User searchedUser = userService.findByUsername(user.getUsername());

		assertNotNull(searchedUser);
		assertEquals("1", searchedUser.getId().toString());
		assertEquals("Dalibor", searchedUser.getFirstName());
		assertEquals("Torma", searchedUser.getLastName());
		assertEquals("dtorma", searchedUser.getUsername());
		assertEquals("dtorma@gmail.com", searchedUser.getEmail());
		assertEquals("torma10", searchedUser.getPassword());
	}

//	@Test
//	public void testFindByUsernameEntityNotFoundException() {
//		User user = new User(1L, "Ivan", "Žugaj", "izugaj", "izugaj@gmail.com", "ivan007", 2, null, null, null, null);
//
//		when(userRepository.findById(user.getId())).thenThrow(new EntityNotFoundException("User", "username", user.getId().toString()));
//
//		assertThrows(EntityNotFoundException.class, () -> userService.findByUsername(user.getUsername()));
//	}

	@Test
	public void testFindAll() {
		Long firstUserId = 1L;
		User firstUser = createUser(firstUserId, "Luka", "Žugaj", "lzugaj", "lzugaj@gmail.com", "#Lzugaj12312");

		Long secondUserId = 2L;
		User secondUser = createUser(secondUserId, "Dalibor", "Torma", "dtorma", "dtorma@gmail.com", "torma10");

		List<User> users = new ArrayList<>();
		users.add(firstUser);
		users.add(secondUser);

		when(userRepository.findAll()).thenReturn(users);

		List<User> searchedUsers = userService.findAll();

		assertEquals(2, users.size());
		assertEquals(2, searchedUsers.size());
		verify(userRepository, times(1)).findAll();
	}

	@Test
	public void testFindAllThatContainsUsername() {
		Long firstUserId = 1L;
		User firstUser = createUser(firstUserId, "Luka", "Žugaj", "lzugaj", "lzugaj@gmail.com", "#Lzugaj12312");

		Long secondUserId = 2L;
		User secondUser = createUser(secondUserId, "Ivan", "Žugaj", "izugaj", "izugaj@gmail.com", "izugaj1111");

		Long thirdUserId = 3L;
		User thirdUser = createUser(thirdUserId, "Dalibor", "Torma", "dtorma", "dtorma@gmail.com", "torma10");

		List<User> users = new ArrayList<>();
		users.add(firstUser);
		users.add(secondUser);
		users.add(thirdUser);

		when(userRepository.findAll()).thenReturn(users);

		String username = "zug";
		List<User> searchedUsers = userService.findAllThatContainsUsername(username);

		assertEquals(3, users.size());
		assertEquals(2, searchedUsers.size());
		verify(userRepository, times(1)).findAll();
	}

	@Test
	public void testUpdate() {
		Long firstUserId = 1L;
		User firstUser = createUser(firstUserId, "Luka", "Žugaj", "lzugaj", "lzugaj@gmail.com", "#Lzugaj12312");

		Long secondUserId = 2L;
		User secondUser = createUser(secondUserId, "Ivan", "Žugaj", "izugaj", "izugaj@gmail.com", "izugaj1111");

		when(userRepository.save(secondUser)).thenReturn(secondUser);

		User updatedUser = userService.update(firstUser, secondUser);

		assertNotNull(updatedUser);
		assertEquals("1", updatedUser.getId().toString());
		assertEquals("Luka", updatedUser.getFirstName());
		assertEquals("Žugaj", updatedUser.getLastName());
		assertEquals("izugaj", updatedUser.getUsername());
		assertEquals("izugaj@gmail.com", updatedUser.getEmail());
		assertEquals("#Lzugaj12312", updatedUser.getPassword());
	}

	@Test
	public void testUpdateUserStatus() {
		Long firstUserStatus = 1L;
		UserStatus activeUserStatus = createUserStatus(firstUserStatus, "ACTIVE");

		Long secondUserStatus = 1L;
		UserStatus blockedUserStatus = createUserStatus(secondUserStatus, "BLOCKED");

		Long userId = 1L;
		User user = createUser(userId, "Luka", "Žugaj", "lzugaj", "lzugaj@gmail.com", "#Lzugaj12312");
		user.setUserStatus(activeUserStatus);

		when(userRepository.save(user)).thenReturn(user);

		User updatedUser = userService.updateUserStatus(user, blockedUserStatus);

		assertNotNull(updatedUser);
		assertEquals("1", updatedUser.getId().toString());
		assertEquals("Luka", updatedUser.getFirstName());
		assertEquals("Žugaj", updatedUser.getLastName());
		assertEquals("lzugaj", updatedUser.getUsername());
		assertEquals("lzugaj@gmail.com", updatedUser.getEmail());
		assertEquals("#Lzugaj12312", updatedUser.getPassword());
		assertEquals("BLOCKED", updatedUser.getUserStatus().getName());
	}

	@Test
	public void testDelete() {
		Long userId = 1L;
		User user = createUser(userId, "Luka", "Žugaj", "lzugaj", "lzugaj@gmail.com", "#Lzugaj12312");

		User deletedUser = userService.delete(user);

		assertEquals("1", deletedUser.getId().toString());
		assertEquals("Luka", deletedUser.getFirstName());
		assertEquals("Žugaj", deletedUser.getLastName());
		assertEquals("lzugaj", deletedUser.getUsername());
		assertEquals("lzugaj@gmail.com", deletedUser.getEmail());
		assertEquals("#Lzugaj12312", deletedUser.getPassword());
		verify(userRepository, times(1)).delete(user);
	}

	@Test
	public void testIsUsernameAlreadyUsedReturnsTrue() {
		Long firstUserId = 1L;
		User firstUser = createUser(firstUserId, "Luka", "Žugaj", "lzugaj", "lzugaj@gmail.com", "#Lzugaj12312");

		Long secondUserId = 2L;
		User secondUser = createUser(secondUserId, "Ivan", "Žugaj", "izugaj", "izugaj@gmail.com", "izugaj1111");

		Long thirdUserId = 3L;
		User thirdUser = createUser(thirdUserId, "Dalibor", "Torma", "lzugaj", "dtorma@gmail.com", "torma10");

		List<User> users = new ArrayList<>();
		users.add(firstUser);
		users.add(secondUser);

		when(userRepository.findAll()).thenReturn(users);

		boolean usernameAlreadyExists = userService.isUsernameAlreadyUsed(thirdUser);

		assertEquals("lzugaj", firstUser.getUsername());
		assertEquals("lzugaj", thirdUser.getUsername());
		assertTrue(usernameAlreadyExists);
	}

	@Test
	public void testIsUsernameAlreadyUsedReturnsFalse() {
		Long firstUserId = 1L;
		User firstUser = createUser(firstUserId, "Luka", "Žugaj", "lzugaj", "lzugaj@gmail.com", "#Lzugaj12312");

		Long secondUserId = 2L;
		User secondUser = createUser(secondUserId, "Ivan", "Žugaj", "izugaj", "izugaj@gmail.com", "izugaj1111");

		Long thirdUserId = 3L;
		User thirdUser = createUser(thirdUserId, "Dalibor", "Torma", "dtorma", "dtorma@gmail.com", "torma10");

		List<User> users = new ArrayList<>();
		users.add(firstUser);
		users.add(secondUser);

		when(userRepository.findAll()).thenReturn(users);

		boolean usernameAlreadyExists = userService.isUsernameAlreadyUsed(thirdUser);

		assertEquals("lzugaj", firstUser.getUsername());
		assertEquals("dtorma", thirdUser.getUsername());
		assertFalse(usernameAlreadyExists);
	}

	@Test
	public void testIsEmailAlreadyUsedReturnsTrue() {
		Long firstUserId = 1L;
		User firstUser = createUser(firstUserId, "Luka", "Žugaj", "lzugaj", "lzugaj@gmail.com", "#Lzugaj12312");

		Long secondUserId = 2L;
		User secondUser = createUser(secondUserId, "Ivan", "Žugaj", "izugaj", "izugaj@gmail.com", "izugaj1111");

		Long thirdUserId = 3L;
		User thirdUser = createUser(thirdUserId, "Dalibor", "Torma", "dtorma", "lzugaj@gmail.com", "torma10");

		List<User> users = new ArrayList<>();
		users.add(firstUser);
		users.add(secondUser);

		when(userRepository.findAll()).thenReturn(users);

		boolean usernameAlreadyExists = userService.isEmailAlreadyUsed(thirdUser);

		assertTrue(usernameAlreadyExists);
	}

	@Test
	public void testIsEmailAlreadyUsedReturnsFalse() {
		Long firstUserId = 1L;
		User firstUser = createUser(firstUserId, "Luka", "Žugaj", "lzugaj", "lzugaj@gmail.com", "#Lzugaj12312");

		Long secondUserId = 2L;
		User secondUser = createUser(secondUserId, "Ivan", "Žugaj", "izugaj", "izugaj@gmail.com", "izugaj1111");

		Long thirdUserId = 3L;
		User thirdUser = createUser(thirdUserId, "Dalibor", "Torma", "dtorma", "dtorma@gmail.com", "torma10");

		List<User> users = new ArrayList<>();
		users.add(firstUser);
		users.add(secondUser);

		when(userRepository.findAll()).thenReturn(users);

		boolean usernameAlreadyExists = userService.isEmailAlreadyUsed(thirdUser);

		assertFalse(usernameAlreadyExists);
	}

	private UserStatus createUserStatus(Long id, String name) {
		UserStatus userStatus = new UserStatus();
		userStatus.setId(id);
		userStatus.setName(name);
		return userStatus;
	}

	private User createUser(Long id, String firstName, String lastName, String username, String email, String password) {
		User user = new User();
		user.setId(id);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);
		return user;
	}
}
