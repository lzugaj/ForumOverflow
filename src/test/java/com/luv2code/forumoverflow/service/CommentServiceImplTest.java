package com.luv2code.forumoverflow.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.luv2code.forumoverflow.domain.Comment;
import com.luv2code.forumoverflow.domain.ContentStatus;
import com.luv2code.forumoverflow.domain.Post;
import com.luv2code.forumoverflow.domain.User;
import com.luv2code.forumoverflow.repository.CommentRepository;
import com.luv2code.forumoverflow.service.impl.CommentServiceImpl;
import com.luv2code.forumoverflow.util.Constants;

/**
 * Created by lzugaj on Tuesday, March 2020
 */

@SpringBootTest
public class CommentServiceImplTest {

    @Mock
    private UserService userService;

    @Mock
    private PostService postService;

    @Mock
    private ContentStatusService contentStatusService;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSave() {
        Long userId = 1L;
        User user = createUser(userId, "Luka", "Žugaj", "lzugaj", "lzugaj@gmail.com", "#Lzugaj11");

        Long postId = 1L;
        Post post = createPost(postId, "Title", "Description");

        Long contentStatusId = 1L;
        ContentStatus contentStatus = createContentStatus(contentStatusId, "VALID");

        Long commentId = 1L;
        Comment comment = createComment(commentId, "Comment description");

        when(userService.findByUsername(user.getUsername())).thenReturn(user);
        when(postService.findById(post.getId())).thenReturn(post);
        when(contentStatusService.findByName(Constants.VALID)).thenReturn(contentStatus);
        when(commentRepository.save(comment)).thenReturn(comment);

        Comment newComment = commentService.save(user.getUsername(), post.getId(), comment);

        assertNotNull(newComment);
        assertEquals("1", comment.getId().toString());
        assertEquals("Comment description", comment.getDescription());
        assertEquals("lzugaj", comment.getUser().getUsername());
        assertEquals("Title", comment.getPost().getTitle());
        assertEquals("VALID", comment.getContentStatus().getName());
    }

    @Test
    public void testFindById() {
        Long userId = 1L;
        User user = createUser(userId, "Dalibor", "Torma", "dtorma", "dtorma@gmail.com", "dtorma10");

        Long postId = 1L;
        Post post = createPost(postId, "Naslov", "Opis");

        Long contentStatusId = 1L;
        ContentStatus contentStatus = createContentStatus(contentStatusId, "VALID");

        Long commentId = 1L;
        Comment comment = createComment(commentId, "Komentar");
        comment.setUser(user);
        comment.setPost(post);
        comment.setContentStatus(contentStatus);

        when(commentRepository.findById(comment.getId())).thenReturn(java.util.Optional.of(comment));

        Comment searchedComment = commentService.findById(comment.getId());

        assertNotNull(searchedComment);
        assertEquals("1", searchedComment.getId().toString());
        assertEquals("Komentar", comment.getDescription());
        assertEquals("dtorma", comment.getUser().getUsername());
        assertEquals("Naslov", comment.getPost().getTitle());
        assertEquals("VALID", comment.getContentStatus().getName());
    }

    @Test
    public void testFindByIdNullPointerException() {
        Long commentId = 1L;
        Comment comment = createComment(commentId, "Komentar");

        when(commentRepository.findById(comment.getId())).thenThrow(new NullPointerException());

        assertThrows(NullPointerException.class, () -> commentService.findById(comment.getId()));
    }

    @Test
    public void testFindAll() {
        Long firstCommentId = 1L;
        Comment firstComment = createComment(firstCommentId, "Komentar 1");

        Long secondCommentId = 2L;
        Comment secondComment = createComment(secondCommentId, "Komentar 2");

        Long thirdCommentId = 3L;
        Comment thirdComment = createComment(thirdCommentId, "Komentar 3");

        List<Comment> comments = new ArrayList<>();
        comments.add(firstComment);
        comments.add(secondComment);
        comments.add(thirdComment);

        when(commentRepository.findAll()).thenReturn(comments);

        List<Comment> searchedComments = commentService.findAll();

        assertEquals(3, comments.size());
        assertEquals(3, searchedComments.size());
        verify(commentRepository, times(1)).findAll();
    }

    @Test
    public void testFindAllForPost() {
        Long firstPostId = 1L;
        Post firstPost = createPost(firstPostId, "Naslov 1", "Opis 1");

        Long secondPostId = 2L;
        Post secondPost = createPost(secondPostId, "Naslov 2", "Opis 2");

        Long firstCommentId = 1L;
        Comment firstComment = createComment(firstCommentId, "Komentar 1");
        firstComment.setPost(firstPost);

        Long secondCommentId = 2L;
        Comment secondComment = createComment(secondCommentId, "Komentar 2");
        secondComment.setPost(secondPost);

        Long thirdCommentId = 3L;
        Comment thirdComment = createComment(thirdCommentId, "Komentar 3");
        thirdComment.setPost(firstPost);

        List<Comment> comments = new ArrayList<>();
        comments.add(firstComment);
        comments.add(secondComment);
        comments.add(thirdComment);

        when(commentRepository.findAll()).thenReturn(comments);

        List<Comment> searchedComments = commentService.findAllForPost(firstPost.getId());

        assertEquals(3, comments.size());
        assertEquals(2, searchedComments.size());
        verify(commentRepository, times(1)).findAll();
    }

    @Test
    public void testUpdate() {
        Long firstCommentId = 1L;
        Comment firstComment = createComment(firstCommentId, "Komentar 1");

        Long secondCommentId = 2L;
        Comment secondComment = createComment(secondCommentId, "Komentar 2");

        when(commentRepository.save(secondComment)).thenReturn(secondComment);

        Comment updatedComment = commentService.update(firstComment, secondComment);

        assertNotNull(updatedComment);
        assertEquals("1", updatedComment.getId().toString());
        assertEquals("Komentar 2", updatedComment.getDescription());
    }

    @Test
    public void testUpdateStatus() {
        Long validContentStatusId = 1L;
        ContentStatus validContentStatus = createContentStatus(validContentStatusId, "VALID");

        Long invalidContentStatusId = 2L;
        ContentStatus invalidContentStatus = createContentStatus(invalidContentStatusId, "INVALID");

        Long commentId = 1L;
        Comment comment = createComment(commentId, "Komentar");
        comment.setContentStatus(validContentStatus);

        when(commentRepository.save(comment)).thenReturn(comment);

        Comment updatedComment = commentService.updateStatus(comment, invalidContentStatus);

        assertNotNull(updatedComment);
        assertEquals("1", updatedComment.getId().toString());
        assertEquals("Komentar", updatedComment.getDescription());
        assertEquals("INVALID", updatedComment.getContentStatus().getName());
    }

    @Test
    public void testDelete() {
        Long commentId = 1L;
        Comment comment = createComment(commentId, "Komentar");

        Comment deletedComment = commentService.delete(comment);

        Assert.assertEquals("1", deletedComment.getId().toString());
        Assert.assertEquals("Komentar", deletedComment.getDescription());
        verify(commentRepository, times(1)).delete(deletedComment);
    }

    private User createUser(Long id, String firstName, String lastName, String username, String email, String password) {
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

    private Post createPost(Long id, String title, String description) {
        Post post = new Post();
        post.setId(id);
        post.setTitle(title);
        post.setDescription(description);
        post.setCreatedDate(LocalDateTime.now());
        return post;
    }

    private ContentStatus createContentStatus(Long id, String name) {
        ContentStatus contentStatus = new ContentStatus();
        contentStatus.setId(id);
        contentStatus.setName(name);
        contentStatus.setPosts(null);
        return contentStatus;
    }

    private Comment createComment(Long id, String description) {
        Comment comment = new Comment();
        comment.setId(id);
        comment.setDescription(description);
        comment.setCreatedDate(LocalDateTime.now());
        return comment;
    }
}
