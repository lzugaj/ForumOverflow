package com.luv2code.forumoverflow.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luv2code.forumoverflow.domain.Role;

/**
 * Created by lzugaj on Wednesday, March 2020
 */

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

}
