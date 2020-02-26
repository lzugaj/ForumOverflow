package com.luv2code.forumoverflow.service;

import com.luv2code.forumoverflow.domain.Post;

import java.util.List;

/**
 * Created by lzugaj on Friday, February 2020
 */

public interface PostService {

	Post save(String username, Post post);

	Post findById(Long id);

	List<Post> findAll();

	Post update(Long id, Post post);

	Post delete(Long id);

}
