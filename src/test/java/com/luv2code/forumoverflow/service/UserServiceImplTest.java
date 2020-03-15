package com.luv2code.forumoverflow.service;

import com.luv2code.forumoverflow.domain.User;
import com.luv2code.forumoverflow.domain.UserStatus;
import com.luv2code.forumoverflow.exception.EntityNotFoundException;
import com.luv2code.forumoverflow.exception.UserWithEmailAlreadyExistsException;
import com.luv2code.forumoverflow.exception.UserWithUsernameAlreadyExistsException;
import com.luv2code.forumoverflow.repository.UserRepository;
import com.luv2code.forumoverflow.service.impl.UserServiceImpl;
import com.luv2code.forumoverflow.util.Constants;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
		Long userId = 1L;
		User user = createUser(userId, "Luka", "Zugaj", "lzugaj", "lzugaj@gmail.com", "luka123");

		Long userStatusId = 1L;
		UserStatus userStatus = new UserStatus(userStatusId, "ACTIVE", null);

		when(userRepository.save(user)).thenReturn(user);
		when(userStatusService.findByName(Constants.ACTIVE)).thenReturn(userStatus);

		User newUser = userService.save(user);

		assertNotNull(newUser);
		assertEquals("1", newUser.getId().toString());
		assertEquals("Luka", newUser.getFirstName());
		assertEquals("Zugaj", newUser.getLastName());
		assertEquals("lzugaj", newUser.getUsername());
		assertEquals("lzugaj@gmail.com",newUser.getEmail());
		assertEquals("luka123", newUser.getPassword());
		assertEquals("ACTIVE", newUser.getUserStatus().getName());
	}

	@Test
	public void testSaveWhenUserWithUsernameAlreadyExistsException() {
		Long firstId = 1L;
		User firstUser = createUser(firstId, "Luka", "Zugaj", "palsi", "lzugaj@gmail.com", "lzugaj123");

		Long secondId = 2L;
		User secondUser = createUser(secondId, "Dalibor", "Torma", "palsi", "dtorma@gmail.com", "dtorma10");

		List<User> users = new ArrayList<>();
		users.add(firstUser);

		when(userRepository.findAll()).thenReturn(users);
		when(userRepository.save(secondUser)).thenThrow(new UserWithUsernameAlreadyExistsException("User", "username", secondUser.getUsername()));

		assertThrows(UserWithUsernameAlreadyExistsException.class, () -> userService.save(secondUser));
	}

	@Test
	public void testSaveWhenUserWithEmailAlreadyExistsException() {
		Long firstId = 1L;
		User firstUser = createUser(firstId, "Luka", "Zugaj", "lzugaj", "palsi@gmail.com", "lzugaj123");

		Long secondId = 2L;
		User secondUser = createUser(secondId, "Dalibor", "Torma", "dtorma", "palsi@gmail.com", "dtorma10");

		List<User> users = new ArrayList<>();
		users.add(firstUser);

		when(userRepository.findAll()).thenReturn(users);
		when(userRepository.save(secondUser)).thenThrow(new UserWithEmailAlreadyExistsException("User", "email", secondUser.getEmail()));

		assertThrows(UserWithEmailAlreadyExistsException.class, () -> userService.save(secondUser));
	}

	@Test
	public void testFindById() {
		Long id = 1L;
		User firstUser = createUser(id, "Grgur", "Zugaj", "gzugaj", "grgo.zugaj@gmail.com", "grobro10");

		when(userRepository.findById(firstUser.getId())).thenReturn(java.util.Optional.of(firstUser));

		User searchedUser = userService.findById(id);

		assertNotNull(searchedUser);
		assertEquals("1", searchedUser.getId().toString());
		assertEquals("Grgur", searchedUser.getFirstName());
		assertEquals("Zugaj", searchedUser.getLastName());
		assertEquals("gzugaj", searchedUser.getUsername());
		assertEquals("grgo.zugaj@gmail.com", searchedUser.getEmail());
		assertEquals("grobro10", searchedUser.getPassword());
	}

	@Test
	public void testFindByIdEntityNotFoundException() {
		Long id = 1L;

		when(userRepository.findById(id)).thenThrow(new EntityNotFoundException("User", "id", id.toString()));

		assertThrows(EntityNotFoundException.class, () -> userService.findById(id));
	}

	@Test
	public void testFindByUsername() {
		Long id = 1L;
		User firstUser = createUser(id, "Dalibor", "Torma", "palsi", "dtorma@gmail.com", "dtorma10");

		when(userRepository.findByUsername(firstUser.getUsername())).thenReturn(java.util.Optional.of(firstUser));

		String searchedUsername = "palsi";
		User searchedUser = userService.findByUsername(searchedUsername);

		assertNotNull(searchedUser);
		assertEquals("1", searchedUser.getId().toString());
		assertEquals("Dalibor", searchedUser.getFirstName());
		assertEquals("Torma", searchedUser.getLastName());
		assertEquals("palsi", searchedUser.getUsername());
		assertEquals("dtorma@gmail.com", searchedUser.getEmail());
		assertEquals("dtorma10", searchedUser.getPassword());
	}

	@Test
	public void testFindByUsernameEntityNotFoundException() {
		String searchedUser = "lzugaj";

		when(userRepository.findByUsername(searchedUser)).thenThrow(new EntityNotFoundException("User", "username", searchedUser));

		assertThrows(EntityNotFoundException.class, () -> userService.findByUsername(searchedUser));
	}

	@Test
	public void testFindAll() {
		Long firstId = 1L;
		User firstUser = createUser(firstId, "Luka", "Zugaj", "lzugaj", "lzugaj@gmail.com", "lzugaj123");

		Long secondId = 2L;
		User secondUser = createUser(secondId, "Dalibor", "Torma", "palsi", "dtorma@gmail.com", "dtorma10");

		Long thirdId = 3L;
		User thirdUser = createUser(thirdId, "Domagoj", "Cep", "dcep", "dcep@gmail.com", "dcpe12");

		List<User> users = new ArrayList<>();
		users.add(firstUser);
		users.add(secondUser);
		users.add(thirdUser);

		when(userRepository.findAll()).thenReturn(users);

		List<User> searchedUsers = userService.findAll();

		assertEquals(3, users.size());
		assertEquals(3, searchedUsers.size());
		verify(userRepository, times(1)).findAll();
	}

	@Test
	public void testFindAllThatContainsUsername() {
		Long firstId = 1L;
		User firstUser = createUser(firstId, "Luka", "Zugaj", "lpalsizugaj", "lzugaj@gmail.com", "lzugaj123");

		Long secondId = 2L;
		User secondUser = createUser(secondId, "Dalibor", "Torma", "palsi", "dtorma@gmail.com", "dtorma10");

		Long thirdId = 3L;
		User thirdUser = createUser(thirdId, "Domagoj", "Cep", "dcep", "dcep@gmail.com", "dcpe12");

		List<User> users = new ArrayList<>();
		users.add(firstUser);
		users.add(secondUser);
		users.add(thirdUser);

		when(userRepository.findAll()).thenReturn(users);

		String username = "palsi";
		List<User> searchedUsers = userService.findAllThatContainsUsername(username);

		assertEquals(3, users.size());
		assertEquals(2, searchedUsers.size());
		verify(userRepository, times(1)).findAll();
	}

	@Test
	public void testUpdate() {
		Long id = 1L;
		User firstUser = createUser(id, "Ivan", "Ivic", "iivic", "iivic@gmail.com", "iivic1111");

		User secondUser = createUser(id, "Ivan", "Ivic", "testko", "test@gmail.com", "iivic1111");

		when(userRepository.findById(firstUser.getId())).thenReturn(java.util.Optional.of(firstUser));
		when(userRepository.save(secondUser)).thenReturn(secondUser);

		User updatedUser = userService.update(secondUser.getId(), secondUser);

		assertNotNull(updatedUser);
		assertEquals("1", updatedUser.getId().toString());
		assertEquals("Ivan", updatedUser.getFirstName());
		assertEquals("Ivic", updatedUser.getLastName());
		assertEquals("testko", updatedUser.getUsername());
		assertEquals("test@gmail.com", updatedUser.getEmail());
		assertEquals("iivic1111", updatedUser.getPassword());
	}

	@Test
	public void testUpdateEntityNotFoundException() {
		Long id = 1L;
		User firstUser = createUser(id, "Luka", "Zugaj", "lzugaj", "lzugaj@gmail.com", "lzugaj123");

		when(userRepository.findById(firstUser.getId())).thenThrow(new EntityNotFoundException("User", "id", id.toString()));

		assertThrows(EntityNotFoundException.class, () -> userService.update(id, firstUser));
	}

	@Test
	public void testUpdateUserStatus() {
		UserStatus firstStatus = new UserStatus(1L, "ACTIVE", null);
		UserStatus secondStatus = new UserStatus(1L, "BLOCKED", null);

		Long id = 1L;
		String username = "lzugaj";
		User user = new User(id, "Luka", "Zugaj", "lzugaj", "luka.zugaj@gmail.com", "luka123", 0, firstStatus, null, null, null);

		when(userRepository.findByUsername(username)).thenReturn(java.util.Optional.of(user));

		User updatedUser = userService.updateUserStatus(user.getUsername(), secondStatus);

		assertNotNull(updatedUser);
		assertEquals("1", updatedUser.getId().toString());
		assertEquals("Luka", updatedUser.getFirstName());
		assertEquals("Zugaj", updatedUser.getLastName());
		assertEquals("lzugaj", updatedUser.getUsername());
		assertEquals("luka.zugaj@gmail.com", updatedUser.getEmail());
		assertEquals("luka123", updatedUser.getPassword());
		assertEquals("BLOCKED", user.getUserStatus().getName());
	}

	@Test
	public void testUpdateUserStatusEntityNotFoundException() {
		UserStatus firstStatus = new UserStatus(1L, "ACTIVE", null);

		Long id = 1L;
		User user = createUser(id, "Luka", "Zugaj", "lzugaj", "lzugaj@gmail.com", "lzugaj123");

		when(userRepository.findByUsername(user.getUsername())).thenThrow(new EntityNotFoundException("User", "username", user.getUsername()));

		assertThrows(EntityNotFoundException.class, () -> userService.updateUserStatus(user.getUsername(), firstStatus));
	}

	@Test
	public void testDelete() {
		Long id = 1L;
		User firstUser = createUser(id, "Luka", "Zugaj", "palsi", "dtorma@gmail.com", "lzugaj123");

		when(userRepository.findById(id)).thenReturn(java.util.Optional.of(firstUser));

		User deletedUser = userService.delete(firstUser.getId());

		assertNotNull(deletedUser);
		assertEquals("1", deletedUser.getId().toString());
		assertEquals("Luka", deletedUser.getFirstName());
		assertEquals("Zugaj", deletedUser.getLastName());
		assertEquals("palsi", deletedUser.getUsername());
		assertEquals("dtorma@gmail.com", deletedUser.getEmail());
		assertEquals("lzugaj123", deletedUser.getPassword());
	}

	@Test
	public void testDeleteEntityNotFoundException() {
		Long id = 1L;
		User firstUser = createUser(id, "Luka", "Zugaj", "lzugaj", "lzugaj@gmail.com", "lzugaj123");

		when(userRepository.findById(firstUser.getId())).thenThrow(new EntityNotFoundException("User", "id", id.toString()));

		assertThrows(EntityNotFoundException.class, () -> userService.delete(id));
	}

	private User createUser(Long id, String firstName, String lastName, String username, String email, String password) {
		User user = new User();
		user.setId(id);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);
		user.setBlockerCounter(0);
		return user;
	}
}
