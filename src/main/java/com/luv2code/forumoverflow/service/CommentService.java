package com.luv2code.forumoverflow.service;

import java.util.List;

import com.luv2code.forumoverflow.domain.Comment;
import com.luv2code.forumoverflow.domain.ContentStatus;

/**
 * Created by lzugaj on Tuesday, March 2020
 */

public interface CommentService {

    Comment save(String username, Long postId, Comment comment);

    Comment findById(Long id);

    List<Comment> findAll();

    List<Comment> findAllForPost(Long postId);

    Comment update(Comment oldComment, Comment newComment);

    Comment updateStatus(Comment updatedComment, ContentStatus contentStatus);

    Comment delete(Comment comment);

}
