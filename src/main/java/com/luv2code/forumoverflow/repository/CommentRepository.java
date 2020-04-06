package com.luv2code.forumoverflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luv2code.forumoverflow.domain.Comment;

/**
 * Created by lzugaj on Tuesday, March 2020
 */

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

}
