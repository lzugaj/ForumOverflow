package com.luv2code.forumoverflow.service;

import com.luv2code.forumoverflow.domain.UserStatus;
import com.luv2code.forumoverflow.exception.EntityNotFoundException;
import com.luv2code.forumoverflow.repository.UserStatusRepository;
import com.luv2code.forumoverflow.service.impl.UserStatusServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by lzugaj on Friday, March 2020
 */

@SpringBootTest
public class UserStatusServiceImplTest {

	@Mock
	private UserStatusRepository userStatusRepository;

	@InjectMocks
	private UserStatusServiceImpl userStatusService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFindById() {
		Long id = 1L;
		UserStatus userStatus = new UserStatus(id, "ACTIVE", null);

		when(userStatusRepository.findById(id)).thenReturn(java.util.Optional.of(userStatus));

		UserStatus newUserStatus = userStatusService.findById(id);

		assertNotNull(newUserStatus);
		assertEquals("1", newUserStatus.getId().toString());
		assertEquals("ACTIVE", newUserStatus.getName());
	}

	@Test
	public void testFindByIdEntityNotFoundException() {
		Long id = 1L;

		when(userStatusRepository.findById(id)).thenThrow(new EntityNotFoundException("UserStatus", "id", id.toString()));

		assertThrows(EntityNotFoundException.class, () -> userStatusService.findById(id));
	}

	@Test
	public void testFindByName() {
		Long id = 1L;
		UserStatus userStatus = createUserStatus(id, "ACTIVE");

		when(userStatusRepository.findByName(userStatus.getName())).thenReturn(java.util.Optional.of(userStatus));

		UserStatus newUserStatus = userStatusService.findByName(userStatus.getName());

		assertNotNull(newUserStatus);
		assertEquals("1", newUserStatus.getId().toString());
		assertEquals("ACTIVE", newUserStatus.getName());
	}

	@Test
	public void testFindByNameEntityNotFoundException() {
		Long id = 1L;
		UserStatus userStatus = createUserStatus(id, "INACTIVE");

		when(userStatusRepository.findByName(userStatus.getName())).thenThrow(new EntityNotFoundException("UserStatus", "name", userStatus.getName()));

		assertThrows(EntityNotFoundException.class, () -> userStatusService.findByName(userStatus.getName()));
	}

	@Test
	public void testFindAll() {
		Long firstId = 1L;
		UserStatus firstUserStatus = createUserStatus(firstId, "ACTIVE");

		Long secondId = 1L;
		UserStatus secondUserStatus = createUserStatus(secondId, "ACTIVE");

		Long thirdId = 1L;
		UserStatus thirdUserStatus = createUserStatus(thirdId, "ACTIVE");

		List<UserStatus> userStatuses = new ArrayList<>();
		userStatuses.add(firstUserStatus);
		userStatuses.add(secondUserStatus);
		userStatuses.add(thirdUserStatus);

		when(userStatusRepository.findAll()).thenReturn(userStatuses);

		List<UserStatus> searchedUserStatuses = userStatusService.findAll();

		assertEquals(3, userStatuses.size());
		assertEquals(3, searchedUserStatuses.size());
		verify(userStatusRepository, times(1)).findAll();
	}

	private UserStatus createUserStatus(Long id, String name) {
		UserStatus userStatus = new UserStatus();
		userStatus.setId(id);
		userStatus.setName(name);
		userStatus.setUser(null);
		return userStatus;
	}
}
