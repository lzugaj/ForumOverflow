package com.luv2code.forumoverflow.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.luv2code.forumoverflow.domain.Role;
import com.luv2code.forumoverflow.domain.User;
import com.luv2code.forumoverflow.domain.UserStatus;
import com.luv2code.forumoverflow.repository.UserRepository;
import com.luv2code.forumoverflow.service.impl.UserServiceImpl;
import com.luv2code.forumoverflow.util.Constants;

/**
 * Created by lzugaj on Friday, February 2020
 */

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private UserRepository userRepository;

	@Mock
	private UserStatusService userStatusService;

	@Mock
	private RoleService roleService;

	@Mock
	private EmailService emailService;

	@InjectMocks
	private UserServiceImpl userService;

	private UserStatus userStatus;

	private User firstUser;

	private User secondUser;

	private User thirdUser;

	private List<User> users;

	public static final String TEST_PASSWORD = "test";

	public static final String TEST_PASSWORD_ENCODED = "$2y$12$RxttGdEa.uMwjF1CqrcymeKHnA.CGk4fEMWNfL7QriwRZcgzqei7W";

	@BeforeEach
	public void setup() {
		userStatus = new UserStatus();
		userStatus.setId(1L);
		userStatus.setName(Constants.ACTIVE);

		Role role = new Role();
		role.setId(1L);
		role.setName(Constants.USER);

		firstUser = new User();
		firstUser.setId(1L);
		firstUser.setFirstName("Luka");
		firstUser.setLastName("Žugaj");
		firstUser.setUsername("lzugaj");
		firstUser.setEmail("lzugaj@racunarstvo.hr");
		firstUser.setPassword("test");

		secondUser = new User();
		secondUser.setId(2L);
		secondUser.setFirstName("Dalibor");
		secondUser.setLastName("Torma");
		secondUser.setUsername("dtorma");
		secondUser.setEmail("dtorma@racunarstvo.hr");
		secondUser.setPassword("password");

		thirdUser = new User();
		thirdUser.setId(3L);
		thirdUser.setFirstName("Anna");
		thirdUser.setLastName("Smith");
		thirdUser.setUsername("asmith");
		thirdUser.setEmail("asmith@gmail.com");
		thirdUser.setPassword("hello");

		users = new ArrayList<>();
		users.add(firstUser);
		users.add(secondUser);

		Mockito.when(userStatusService.findByName(Constants.ACTIVE)).thenReturn(userStatus);
		Mockito.when(roleService.findByName(Constants.USER)).thenReturn(role);
		Mockito.when(passwordEncoder.encode(TEST_PASSWORD)).thenReturn(TEST_PASSWORD_ENCODED);
		Mockito.when(userRepository.save(firstUser)).thenReturn(firstUser);
		Mockito.when(userRepository.findById(firstUser.getId())).thenReturn(java.util.Optional.ofNullable(firstUser));
		Mockito.when(userRepository.findByUsername(secondUser.getUsername())).thenReturn(java.util.Optional.ofNullable(secondUser));
		Mockito.when(userRepository.findAll()).thenReturn(users);
	}

	@Test
	public void testIsUsernameAlreadyUsed() {
		boolean isUsernameAlreadyUsed = userService.isUsernameAlreadyUsed(secondUser);

		assertTrue(isUsernameAlreadyUsed);
	}

	@Test
	public void testIsEmailAlreadyUsed() {
		boolean isEmailAlreadyUsed = userService.isEmailAlreadyUsed(firstUser);

		assertTrue(isEmailAlreadyUsed);
	}

	@Test
	public void testSave() {
		User newUser = userService.save(firstUser);

		assertNotNull(newUser);
		assertEquals("1", newUser.getId().toString());
		assertEquals("Luka", newUser.getFirstName());
		assertEquals("Žugaj", newUser.getLastName());
		assertEquals("lzugaj", newUser.getUsername());
		assertEquals("lzugaj@racunarstvo.hr", newUser.getEmail());
		assertEquals(0, newUser.getBlockerCounter());
		assertEquals("ACTIVE", newUser.getUserStatus().getName());
	}

	@Test
	public void testFindById() {
		User searchedUser = userService.findById(firstUser.getId());

		assertNotNull(searchedUser);
		assertEquals("1", searchedUser.getId().toString());
		assertEquals("Luka", searchedUser.getFirstName());
		assertEquals("Žugaj", searchedUser.getLastName());
		assertEquals("lzugaj", searchedUser.getUsername());
		assertEquals("lzugaj@racunarstvo.hr", searchedUser.getEmail());
		assertEquals(0, searchedUser.getBlockerCounter());
	}

	@Test
	public void testFindByIdNullPointerException() {
		when(userRepository.findById(secondUser.getId())).thenThrow(new NullPointerException());

		assertThrows(NullPointerException.class, () -> userService.findById(secondUser.getId()));
	}

	@Test
	public void testFindByUsername() {
		User searchedUser = userService.findByUsername(secondUser.getUsername());

		assertNotNull(searchedUser);
		assertEquals("2", searchedUser.getId().toString());
		assertEquals("Dalibor", searchedUser.getFirstName());
		assertEquals("Torma", searchedUser.getLastName());
		assertEquals("dtorma", searchedUser.getUsername());
		assertEquals("dtorma@racunarstvo.hr", searchedUser.getEmail());
	}

	@Test
	public void testFindByUsernameEntityNotFoundException() {
		when(userRepository.findByUsername(firstUser.getUsername())).thenThrow(new NullPointerException());

		assertThrows(NullPointerException.class, () -> userService.findByUsername(firstUser.getUsername()));
	}

	@Test
	public void testFindAll() {
		List<User> searchedUsers = userService.findAll();

		assertEquals(2, users.size());
		assertEquals(2, searchedUsers.size());
		verify(userRepository, times(1)).findAll();
	}

	@Test
	public void testUpdate() {
		User updatedUser = userService.update(firstUser, thirdUser);

		assertNotNull(updatedUser);
		assertEquals("1", updatedUser.getId().toString());
		assertEquals("Luka", updatedUser.getFirstName());
		assertEquals("Žugaj", updatedUser.getLastName());
		assertEquals("asmith", updatedUser.getUsername());
		assertEquals("asmith@gmail.com", updatedUser.getEmail());
	}

	@Test
	public void testUpdateUserStatus() {
		User updatedUser = userService.updateUserStatus(firstUser, userStatus);

		assertNotNull(updatedUser);
		assertEquals("1", updatedUser.getId().toString());
		assertEquals("Luka", updatedUser.getFirstName());
		assertEquals("Žugaj", updatedUser.getLastName());
		assertEquals("lzugaj", updatedUser.getUsername());
		assertEquals("lzugaj@racunarstvo.hr", updatedUser.getEmail());
		assertEquals("ACTIVE", updatedUser.getUserStatus().getName());
		verify(emailService, times(1)).sendUserStatusChangedNotification(updatedUser);
	}

	@Test
	public void testDelete() {
		User deletedUser = userService.delete(firstUser);

		assertNotNull(deletedUser);
		assertEquals("1", deletedUser.getId().toString());
		assertEquals("Luka", deletedUser.getFirstName());
		assertEquals("Žugaj", deletedUser.getLastName());
		assertEquals("lzugaj", deletedUser.getUsername());
		assertEquals("lzugaj@racunarstvo.hr", deletedUser.getEmail());
		verify(userRepository, times(1)).delete(firstUser);
	}
}
