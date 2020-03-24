package com.luv2code.forumoverflow.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luv2code.forumoverflow.domain.ContentStatus;

/**
 * Created by lzugaj on Wednesday, March 2020
 */

@Repository
public interface ContentStatusRepository extends JpaRepository<ContentStatus, Long> {

    Optional<ContentStatus> findByName(String name);

}
