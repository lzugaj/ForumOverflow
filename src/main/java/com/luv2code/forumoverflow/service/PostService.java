package com.luv2code.forumoverflow.service;

import java.util.List;

import com.luv2code.forumoverflow.domain.ContentStatus;
import com.luv2code.forumoverflow.domain.Post;

/**
 * Created by lzugaj on Friday, February 2020
 */

public interface PostService {

	Post save(final String username, final Post post);

	Post reportAsInvalid(final Long id);

	Post findById(final Long id);

	List<Post> findAll();

	List<Post> findAllByUsername(final String username);

	List<Post> findAllByCategory(final Long categoryId);

	List<Post> findAllReported();

	Post update(final Post oldPost, final Post newPost);

	Post updateStatus(final Post updatedStatus, final ContentStatus contentStatus);

	Post delete(final Post post);

}
