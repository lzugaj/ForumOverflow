package com.luv2code.forumoverflow.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import com.luv2code.forumoverflow.domain.Role;
import com.luv2code.forumoverflow.repository.RoleRepository;
import com.luv2code.forumoverflow.service.impl.RoleServiceImpl;

/**
 * Created by lzugaj on Wednesday, March 2020
 */

@SpringBootTest
public class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindById() {
        Long id = 1L;
        Role role = createRole(id, "USER");

        when(roleRepository.findById(role.getId())).thenReturn(java.util.Optional.of(role));

        Role searchedRole = roleService.findById(role.getId());

        assertNotNull(searchedRole);
        assertEquals("1", searchedRole.getId().toString());
        assertEquals("USER", searchedRole.getName());
    }

    @Test
    public void testFindByIdNullPointerException() {
        Long id = 1L;
        Role role = createRole(id, "ADMIN");

        when(roleRepository.findById(role.getId())).thenThrow(new NullPointerException());

        assertThrows(NullPointerException.class, () -> roleService.findById(role.getId()));
    }

    @Test
    public void testFindByName() {
        Long id = 1L;
        Role role = createRole(id, "USER");

        when(roleRepository.findByName(role.getName())).thenReturn(java.util.Optional.of(role));

        Role searchedRole = roleService.findByName(role.getName());

        assertNotNull(searchedRole);
        assertEquals("1", searchedRole.getId().toString());
        assertEquals("USER", searchedRole.getName());
    }

    @Test
    public void testFindByNameNullPointerException() {
        Long id = 1L;
        Role role = createRole(id, "ADMIN");

        when(roleRepository.findByName(role.getName())).thenReturn(java.util.Optional.of(role));

        assertThrows(NullPointerException.class, () -> roleService.findByName(role.getName()));
    }

    @Test
    public void testFindAll() {
        Long firstRoleId = 1L;
        Role firstRole = createRole(firstRoleId, "ADMIN");

        Long secondRoleId = 2L;
        Role secondRole = createRole(secondRoleId, "USER");

        List<Role> roles = new ArrayList<>();
        roles.add(firstRole);
        roles.add(secondRole);

        when(roleRepository.findAll()).thenReturn(roles);

        List<Role> searchedRoles = roleService.findAll();

        assertEquals(2, roles.size());
        assertEquals(2, searchedRoles.size());
        verify(roleRepository, times(1)).findAll();
    }

    private Role createRole(Long id, String name) {
        Role role = new Role();
        role.setId(id);
        role.setName(name);
        role.setUsers(null);
        return role;
    }
}
