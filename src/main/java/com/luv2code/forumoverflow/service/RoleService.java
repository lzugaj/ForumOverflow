package com.luv2code.forumoverflow.service;

import java.util.List;

import com.luv2code.forumoverflow.domain.Role;

/**
 * Created by lzugaj on Wednesday, March 2020
 */

public interface RoleService {

    Role findById(final Long id);

    Role findByName(final String name);

    List<Role> findAll();

}
