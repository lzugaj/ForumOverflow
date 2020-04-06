package com.luv2code.forumoverflow.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.forumoverflow.domain.Comment;
import com.luv2code.forumoverflow.service.CommentService;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by lzugaj on Tuesday, March 2020
 */

@Slf4j
@RequestMapping(
        path = "/comment",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(final CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public ResponseEntity<?> save(@PathVariable Long postId, @RequestBody Comment comment) {
        Comment newComment = commentService.save("lzugaj", postId, comment);
        log.info("Successfully save Comment with id: `{}`", comment.getId());
        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{commentId}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> findById(@PathVariable Long commentId) {
        Optional<Comment> searchedComment = Optional.ofNullable(commentService.findById(commentId));
        if (searchedComment.isPresent()) {
            log.info("Successfully founded Comment with id: `{}`", commentId);
            return new ResponseEntity<>(searchedComment, HttpStatus.OK);
        } else {
            log.info("Comment with id `{}` wasn't founded", commentId);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> findAllForPost(@PathVariable Long id) {
        List<Comment> searchedComments = commentService.findAll();
        log.info("Successfully found all Comments.");
        return new ResponseEntity<>(searchedComments, HttpStatus.OK);
    }
}
