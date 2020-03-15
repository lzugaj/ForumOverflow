package com.luv2code.forumoverflow.repository;

import com.luv2code.forumoverflow.domain.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by lzugaj on Friday, March 2020
 */

@Repository
public interface UserStatusRepository extends JpaRepository<UserStatus, Long> {

	Optional<UserStatus> findByName(String name);

}
