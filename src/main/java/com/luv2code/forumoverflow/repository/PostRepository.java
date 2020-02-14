package com.luv2code.forumoverflow.repository;

import com.luv2code.forumoverflow.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lzugaj on Friday, February 2020
 */

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
