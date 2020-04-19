package com.luv2code.forumoverflow.service;

import java.util.List;

import com.luv2code.forumoverflow.domain.Comment;
import com.luv2code.forumoverflow.domain.ContentStatus;

/**
 * Created by lzugaj on Tuesday, March 2020
 */

public interface CommentService {

    Comment save(final String username, final Long postId, final Comment comment);

    Comment findById(final Long id);

    List<Comment> findAll();

    List<Comment> findAllForPost(final Long postId);

    Comment update(final Comment oldComment, final Comment newComment);

    Comment updateStatus(final Comment updatedComment, final ContentStatus contentStatus);

    Comment delete(final Comment comment);

}
