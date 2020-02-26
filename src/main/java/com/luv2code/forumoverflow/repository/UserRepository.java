package com.luv2code.forumoverflow.repository;

import com.luv2code.forumoverflow.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by lzugaj on Friday, February 2020
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

}
