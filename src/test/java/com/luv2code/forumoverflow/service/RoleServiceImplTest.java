package com.luv2code.forumoverflow.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import com.luv2code.forumoverflow.domain.Role;
import com.luv2code.forumoverflow.repository.RoleRepository;
import com.luv2code.forumoverflow.service.impl.RoleServiceImpl;
import com.luv2code.forumoverflow.util.Constants;

/**
 * Created by lzugaj on Wednesday, March 2020
 */

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role firstRole;

    private Role secondRole;

    private List<Role> roles;

    @BeforeEach
    public void setup() {
        firstRole = new Role();
        firstRole.setId(1L);
        firstRole.setName(Constants.ADMIN);
        firstRole.setUsers(null);

        secondRole = new Role();
        secondRole.setId(2L);
        secondRole.setName(Constants.USER);
        secondRole.setUsers(null);

        roles = new ArrayList<>();
        roles.add(firstRole);
        roles.add(secondRole);

        Mockito.when(roleRepository.findById(firstRole.getId())).thenReturn(java.util.Optional.ofNullable(firstRole));
        Mockito.when(roleRepository.findByName(firstRole.getName())).thenReturn(java.util.Optional.ofNullable(firstRole));
        Mockito.when(roleRepository.findAll()).thenReturn(roles);
    }

    @Test
    public void testFindById() {
        Role searchedRole = roleService.findById(firstRole.getId());

        assertNotNull(searchedRole);
        assertEquals("1", searchedRole.getId().toString());
        assertEquals("ADMIN", searchedRole.getName());
    }

    @Test
    public void testFindByIdNullPointerException() {
        when(roleRepository.findById(secondRole.getId())).thenThrow(new NullPointerException());

        assertThrows(NullPointerException.class, () -> roleService.findById(secondRole.getId()));
    }

    @Test
    public void testFindByName() {
        Role searchedRole = roleService.findByName(firstRole.getName());

        assertNotNull(searchedRole);
        assertEquals("1", searchedRole.getId().toString());
        assertEquals("ADMIN", searchedRole.getName());
    }

    @Test
    public void testFindByNameNullPointerException() {
        when(roleRepository.findByName(secondRole.getName())).thenThrow(new NullPointerException());

        assertThrows(NullPointerException.class, () -> roleService.findByName(secondRole.getName()));
    }

    @Test
    public void testFindAll() {
        List<Role> searchedRoles = roleService.findAll();

        assertEquals(2, roles.size());
        assertEquals(2, searchedRoles.size());
        verify(roleRepository, times(1)).findAll();
    }
}
