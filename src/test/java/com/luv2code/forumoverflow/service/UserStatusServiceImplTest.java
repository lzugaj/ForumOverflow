package com.luv2code.forumoverflow.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
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

import com.luv2code.forumoverflow.domain.UserStatus;
import com.luv2code.forumoverflow.repository.UserStatusRepository;
import com.luv2code.forumoverflow.service.impl.UserStatusServiceImpl;

/**
 * Created by lzugaj on Friday, March 2020
 */

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class UserStatusServiceImplTest {

	@Mock
	private UserStatusRepository userStatusRepository;

	@InjectMocks
	private UserStatusServiceImpl userStatusService;

	private UserStatus firstUserStatus;

	private UserStatus secondUserStatus;

	private List<UserStatus> userStatuses;

	@BeforeEach
	public void setup() {
		firstUserStatus = new UserStatus();
		firstUserStatus.setId(1L);
		firstUserStatus.setName("ACTIVE");
		firstUserStatus.setUser(null);

		secondUserStatus = new UserStatus();
		secondUserStatus.setId(2L);
		secondUserStatus.setName("INACTIVE");
		secondUserStatus.setUser(null);

		userStatuses = new ArrayList<>();
		userStatuses.add(firstUserStatus);
		userStatuses.add(secondUserStatus);

		Mockito.when(userStatusRepository.findById(firstUserStatus.getId())).thenReturn(java.util.Optional.ofNullable(firstUserStatus));
		Mockito.when(userStatusRepository.findByName(firstUserStatus.getName())).thenReturn(java.util.Optional.ofNullable(firstUserStatus));
		Mockito.when(userStatusRepository.findAll()).thenReturn(userStatuses);
	}

	@Test
	public void testFindById() {
		UserStatus newUserStatus = userStatusService.findById(firstUserStatus.getId());

		assertNotNull(newUserStatus);
		assertEquals("1", newUserStatus.getId().toString());
		assertEquals("ACTIVE", newUserStatus.getName());
	}

	@Test
	public void testFindByIdEntityNotFoundException() {
		when(userStatusRepository.findById(secondUserStatus.getId())).thenThrow(new NullPointerException());

		assertThrows(NullPointerException.class, () -> userStatusService.findById(secondUserStatus.getId()));
	}

	@Test
	public void testFindByName() {
		UserStatus newUserStatus = userStatusService.findByName(firstUserStatus.getName());

		assertNotNull(newUserStatus);
		assertEquals("1", newUserStatus.getId().toString());
		assertEquals("ACTIVE", newUserStatus.getName());
	}

	@Test
	public void testFindByNameEntityNotFoundException() {
		when(userStatusRepository.findByName(secondUserStatus.getName())).thenThrow(new NullPointerException());

		assertThrows(NullPointerException.class, () -> userStatusService.findByName(secondUserStatus.getName()));
	}

	@Test
	public void testFindAll() {
		List<UserStatus> searchedUserStatuses = userStatusService.findAll();

		assertEquals(2, userStatuses.size());
		assertEquals(2, searchedUserStatuses.size());
		verify(userStatusRepository, times(1)).findAll();
	}
}
