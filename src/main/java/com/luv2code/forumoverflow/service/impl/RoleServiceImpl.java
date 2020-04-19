package com.luv2code.forumoverflow.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luv2code.forumoverflow.domain.Role;
import com.luv2code.forumoverflow.repository.RoleRepository;
import com.luv2code.forumoverflow.service.RoleService;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lzugaj on Wednesday, March 2020
 */

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(final RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findById(final Long id) {
        final Role role = roleRepository.findById(id).orElse(null);
        log.info("Searching Role with id: `{}`.", id);
        return role;
    }

    @Override
    public Role findByName(final String name) {
        final Role role = roleRepository.findByName(name).orElse(null);
        log.info("Searching Role with name: `{}`.", name);
        return role;
    }

    @Override
    public List<Role> findAll() {
        final List<Role> roles = roleRepository.findAll();
        log.info("Searching all Roles.");
        return roles;
    }
}
