package com.luv2code.forumoverflow.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luv2code.forumoverflow.domain.Comment;
import com.luv2code.forumoverflow.domain.ContentStatus;
import com.luv2code.forumoverflow.domain.Post;
import com.luv2code.forumoverflow.domain.User;
import com.luv2code.forumoverflow.repository.CommentRepository;
import com.luv2code.forumoverflow.service.CommentService;
import com.luv2code.forumoverflow.service.ContentStatusService;
import com.luv2code.forumoverflow.service.PostService;
import com.luv2code.forumoverflow.service.UserService;
import com.luv2code.forumoverflow.util.Constants;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lzugaj on Tuesday, March 2020
 */

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final UserService userService;

    private final PostService postService;

    private final ContentStatusService contentStatusService;

    @Autowired
    public CommentServiceImpl(final CommentRepository commentRepository, final UserService userService,
            final PostService postService, final ContentStatusService contentStatusService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
        this.contentStatusService = contentStatusService;
    }

    @Override
    public Comment save(final String username, final Long postId, final Comment comment) {
        final User user = userService.findByUsername(username);
        log.info("Successfully founded User with username: `{}`", username);

        final Post post = postService.findById(postId);
        log.info("Successfully founded Post with id: `{}`", postId);

        final ContentStatus validContentStatus = contentStatusService.findByName(Constants.VALID);
        log.info("Successfully founded ContentStatus with name: `{}`", validContentStatus.getName());

        comment.setCreatedDate(LocalDateTime.now());
        comment.setContentStatus(validContentStatus);
        comment.setUser(user);
        comment.setPost(post);
        commentRepository.save(comment);
        log.info("Saving Comment with id: `{}`.", comment.getId());
        return comment;
    }

    @Override
    public Comment findById(final Long id) {
        final Comment searchedComment = commentRepository.findById(id).orElse(null);
        log.info("Searching Comment with id: `{}`", id);
        return searchedComment;
    }

    @Override
    public List<Comment> findAll() {
        List<Comment> comments = commentRepository.findAll();
        log.info("Searching all Comments.");
        return comments;
    }

    @Override
    public List<Comment> findAllForPost(final Long postId) {
        return findAll().stream()
                .filter(searchedPost -> searchedPost.getPost().getId().equals(postId))
                .collect(Collectors.toList());
    }

    @Override
    public Comment update(final Comment oldComment, final Comment newComment) {
        final Comment updatedComment = setUpVariables(oldComment, newComment);
        commentRepository.save(updatedComment);
        log.info("Updating Comment with id: `{}`.", updatedComment.getId());
        return updatedComment;
    }

    private Comment setUpVariables(final Comment oldComment, final Comment newComment) {
        oldComment.setDescription(newComment.getDescription());
        oldComment.setCreatedDate(newComment.getCreatedDate());
        log.info("Setting up variables for updated Comment with id: `{}`.", oldComment.getId());
        return oldComment;
    }

    @Override
    public Comment updateStatus(final Comment updatedComment, final ContentStatus contentStatus) {
        updatedComment.setContentStatus(contentStatus);
        log.info("Updating content status to: `{}`", contentStatus.getName());

        commentRepository.save(updatedComment);
        log.info("Updating Comment with id: `{}`", updatedComment.getId());
        return updatedComment;
    }

    @Override
    public Comment delete(final Comment comment) {
        commentRepository.delete(comment);
        log.info("Deleting Comment with id: `{}`.", comment.getId());

        // TODO: Check if current logged in user is Admin and send notification to user if Admin manually delete user post

        return comment;
    }
}
