package com.luv2code.forumoverflow.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    public Comment save(String username, Long postId, Comment comment) {
        User user = userService.findByUsername(username);
        log.info("Successfully founded User with username: `{}`", username);

        Post post = postService.findById(postId);
        log.info("Successfully founded Post with id: `{}`", postId);

        ContentStatus validContentStatus = contentStatusService.findByName(Constants.VALID);
        log.info("Successfully founded ContentStatus with name: `{}`", validContentStatus.getName());

        comment.setUser(user);
        comment.setPost(post);
        comment.setContentStatus(validContentStatus);
        comment.setCreatedDate(LocalDateTime.now());
        commentRepository.save(comment);
        log.info("Saving Comment with id: `{}`.", comment.getId());
        return comment;
    }

    @Override
    public Comment findById(Long id) {
        Comment searchedComment = commentRepository.findById(id).orElse(null);
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
    public List<Comment> findAllForPost(Long postId) {
        List<Comment> comments = findAll();
        List<Comment> searchedComments = new ArrayList<>();
        for (Comment comment : comments) {
            if (comment.getPost().getId().equals(postId)) {
                searchedComments.add(comment);
            }
        }

        log.info("Searching Comments for Post with id: `{}`", postId);
        return searchedComments;
    }

    @Override
    public Comment update(Comment oldComment, Comment newComment) {
        Comment updatedComment = setUpVariables(oldComment, newComment);
        commentRepository.save(updatedComment);
        log.info("Updating Comment with id: `{}`.", updatedComment.getId());
        return updatedComment;
    }

    private Comment setUpVariables(Comment oldComment, Comment newComment) {
        Comment comment = new Comment();
        comment.setId(oldComment.getId());
        comment.setDescription(newComment.getDescription());
        comment.setCreatedDate(newComment.getCreatedDate());
        comment.setContentStatus(oldComment.getContentStatus());
        comment.setPost(oldComment.getPost());
        comment.setUser(oldComment.getUser());

        log.info("Setting up variables for updated Comment with id: `{}`.", oldComment.getId());
        return comment;
    }

    @Override
    public Comment updateStatus(Comment updatedComment, ContentStatus contentStatus) {
        updatedComment.setContentStatus(contentStatus);
        log.info("Updating content status to: `{}`", contentStatus.getName());

        commentRepository.save(updatedComment);
        log.info("Updating Comment with id: `{}`", updatedComment.getId());
        return updatedComment;
    }

    @Override
    public Comment delete(Comment comment) {
        commentRepository.delete(comment);
        log.info("Deleting Comment with id: `{}`.", comment.getId());

        // TODO: Check if current logged in user is Admin and send notification to user if Admin manually delete user post

        return comment;
    }
}
