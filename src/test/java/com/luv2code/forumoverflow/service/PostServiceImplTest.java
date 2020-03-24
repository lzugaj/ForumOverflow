package com.luv2code.forumoverflow.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.luv2code.forumoverflow.domain.Category;
import com.luv2code.forumoverflow.domain.ContentStatus;
import com.luv2code.forumoverflow.domain.Post;
import com.luv2code.forumoverflow.domain.User;
import com.luv2code.forumoverflow.domain.UserStatus;
import com.luv2code.forumoverflow.repository.PostRepository;
import com.luv2code.forumoverflow.service.impl.PostServiceImpl;

/**
 * Created by lzugaj on Friday, February 2020
 */

@SpringBootTest
public class PostServiceImplTest {

	@Mock
	private PostRepository postRepository;

	@Mock
	private UserService userService;

	@Mock
	private CategoryService categoryService;

	@Mock
	private ContentStatusService contentStatusService;

	@InjectMocks
	private PostServiceImpl postService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
    public void testSave() {
	    Long userStatusId = 1L;
        UserStatus userStatus = new UserStatus(userStatusId, "ACTIVE", null);

	    Long userId = 1L;
	    User user = new User(userId, "Luka", "Žugaj", "lzugaj", "lzugaj@gmail.com", "Lzugaj1234", 0, userStatus, null, null, null);

        Long contentStatusId = 1L;
        ContentStatus contentStatus = new ContentStatus(contentStatusId, "VALID", null);

        Long categoryId = 1L;
        Category category = new Category(categoryId, "Feed", null);

        Long postId = 1L;
        Post post = new Post(postId, "Title", "Description", LocalDateTime.now(), contentStatus, user, category, null);

        when(userService.findByUsername(user.getUsername())).thenReturn(user);
        when(categoryService.findById(category.getId())).thenReturn(category);
        when(contentStatusService.findByName(contentStatus.getName())).thenReturn(contentStatus);

        Post newPost = postService.save(user.getUsername(), post);

        assertNotNull(newPost);
        assertEquals("1", newPost.getId().toString());
        assertEquals("Title", newPost.getTitle());
        assertEquals("Description", newPost.getDescription());
        assertEquals("lzugaj", newPost.getUser().getUsername());
        assertEquals("ACTIVE", newPost.getUser().getUserStatus().getName());
        assertEquals("Feed", newPost.getCategory().getName());
    }

    // TODO: Report as invalid


    @Test
    public void testFindById() {
	    Long id = 1L;
	    Post post = createPost(id, "Title", "Description");

	    when(postRepository.findById(post.getId())).thenReturn(java.util.Optional.of(post));

	    Post searchedPost = postService.findById(post.getId());

	    assertNotNull(searchedPost);
	    assertEquals("1", searchedPost.getId().toString());
        assertEquals("Title", searchedPost.getTitle());
        assertEquals("Description", searchedPost.getDescription());
    }

    @Test
    public void testFindByIdNullPointerException() {
        Long id = 1L;
        Post post = createPost(id, "Naslov", "Opis");

        when(postRepository.findById(post.getId())).thenThrow(new NullPointerException());

        assertThrows(NullPointerException.class, () -> postService.findById(post.getId()));
    }

    @Test
    public void testFindAll() {
        Long firstPostId = 1L;
        Post firstPost = createPost(firstPostId, "Title", "Description");

        Long secondPostId = 1L;
        Post secondPost = createPost(secondPostId, "Naslov", "Opis");

        List<Post> posts = new ArrayList<>();
        posts.add(firstPost);
        posts.add(secondPost);

        when(postRepository.findAll()).thenReturn(posts);

        List<Post> searchedPosts = postService.findAll();

        assertEquals(2, posts.size());
        assertEquals(2, searchedPosts.size());
        verify(postRepository, times(1)).findAll();
    }

    @Test
    public void testFindAllByUsername() {
        Long firstUserId = 1L;
        User firstUser = new User(firstUserId, "Luka", "Žugaj", "lzugaj", "lzugaj@gmail.com", "Lzugaj1234", 0, null, null, null, null);

        Long secondUserId = 2L;
        User secondUser = new User(secondUserId, "Dalibor", "Torma", "dtorma", "dtorma@gmail.com", "dtorma1111", 0, null, null, null, null);

        Long firstPostId = 1L;
        Post firstPost = createPost(firstPostId, "Title", "Description");

        Long secondPostId = 2L;
        Post secondPost = createPost(secondPostId, "Naslov", "Opis");

        Long thirdPostId = 3L;
        Post thirdPost = createPost(thirdPostId, "Title", "Description");

        Long fourthPostId = 4L;
        Post fourthPost = createPost(fourthPostId, "Naslov", "Opis");

        firstPost.setUser(firstUser);
        secondPost.setUser(secondUser);
        thirdPost.setUser(firstUser);
        fourthPost.setUser(firstUser);

        List<Post> posts = new ArrayList<>();
        posts.add(firstPost);
        posts.add(secondPost);
        posts.add(thirdPost);
        posts.add(fourthPost);

        when(postRepository.findAll()).thenReturn(posts);

        String username = "lzugaj";
        List<Post> searchedPosts = postService.findAllByUsername(username);

        assertEquals(4, posts.size());
        assertEquals(3, searchedPosts.size());
    }

    @Test
    public void testFindAllByCategory() {
        Long firstCategoryId = 1L;
        Category firstCategory = new Category(firstCategoryId, "Feed", null);

        Long secondCategoryId = 2L;
        Category secondCategory = new Category(secondCategoryId, "Marketing", null);

        Long firstPostId = 1L;
        Post firstPost = createPost(firstPostId, "Title", "Description");

        Long secondPostId = 2L;
        Post secondPost = createPost(secondPostId, "Naslov", "Opis");

        Long thirdPostId = 3L;
        Post thirdPost = createPost(thirdPostId, "Title", "Description");

        Long fourthPostId = 4L;
        Post fourthPost = createPost(fourthPostId, "Naslov", "Opis");

        firstPost.setCategory(firstCategory);
        secondPost.setCategory(secondCategory);
        thirdPost.setCategory(secondCategory);
        fourthPost.setCategory(firstCategory);

        List<Post> posts = new ArrayList<>();
        posts.add(firstPost);
        posts.add(secondPost);
        posts.add(thirdPost);
        posts.add(fourthPost);

        when(postRepository.findAll()).thenReturn(posts);

        List<Post> searchedPosts = postService.findAllByCategory(2L);

        assertEquals(4, posts.size());
        assertEquals(2, searchedPosts.size());
    }

    @Test
    public void testFindAllReported() {
        Long validContentStatusId = 1L;
        ContentStatus validContentStatus = new ContentStatus(validContentStatusId, "VALID", null);

        Long invalidContentStatusId = 2L;
        ContentStatus invalidContentStatus = new ContentStatus(invalidContentStatusId, "INVALID", null);

        Long firstPostId = 1L;
        Post firstPost = createPost(firstPostId, "Title", "Description");

        Long secondPostId = 2L;
        Post secondPost = createPost(secondPostId, "Naslov", "Opis");

        Long thirdPostId = 3L;
        Post thirdPost = createPost(thirdPostId, "Title", "Description");

        Long fourthPostId = 4L;
        Post fourthPost = createPost(fourthPostId, "Naslov", "Opis");

        firstPost.setContentStatus(validContentStatus);
        secondPost.setContentStatus(validContentStatus);
        thirdPost.setContentStatus(invalidContentStatus);
        fourthPost.setContentStatus(validContentStatus);

        List<Post> posts = new ArrayList<>();
        posts.add(firstPost);
        posts.add(secondPost);
        posts.add(thirdPost);
        posts.add(fourthPost);

        when(postRepository.findAll()).thenReturn(posts);

        List<Post> searchedPosts = postService.findAllReported();

        assertEquals(4, posts.size());
        assertEquals(1, searchedPosts.size());
    }

    @Test
    public void testUpdate() {
        Long firstCategoryId = 1L;
        Category firstCategory = new Category(firstCategoryId, "Feed", null);

        Long secondCategoryId = 2L;
        Category secondCategory = new Category(secondCategoryId, "Marketing", null);

        Long firstPostId = 1L;
        Post firstPost = createPost(firstPostId, "Title", "Description");

        Long secondPostId = 2L;
        Post secondPost = createPost(secondPostId, "Naslov", "Opis");

        firstPost.setCategory(firstCategory);
        secondPost.setCategory(secondCategory);

        when(postRepository.save(secondPost)).thenReturn(secondPost);

        Post updatedPost = postService.update(firstPost, secondPost);

        assertNotNull(updatedPost);
        assertEquals("1", updatedPost.getId().toString());
        assertEquals("Naslov", updatedPost.getTitle());
        assertEquals("Opis", updatedPost.getDescription());
        assertEquals("Marketing", updatedPost.getCategory().getName());
    }

    @Test
    public void testUpdateStatus() {
        Long validContentStatusId = 1L;
        ContentStatus validContentStatus = new ContentStatus(validContentStatusId, "VALID", null);

        Long invalidContentStatusId = 2L;
        ContentStatus invalidContentStatus = new ContentStatus(invalidContentStatusId, "INVALID", null);

        Long firstPostId = 1L;
        Post firstPost = createPost(firstPostId, "Title", "Description");
        firstPost.setContentStatus(validContentStatus);

        when(postRepository.save(firstPost)).thenReturn(firstPost);

        Post updatedPost = postService.updateStatus(firstPost, invalidContentStatus);

        assertNotNull(updatedPost);
        assertEquals("1", updatedPost.getId().toString());
        assertEquals("Title", updatedPost.getTitle());
        assertEquals("Description", updatedPost.getDescription());
        assertEquals("INVALID", updatedPost.getContentStatus().getName());
    }

    @Test
    public void testDelete() {
        Long firstPostId = 1L;
        Post firstPost = createPost(firstPostId, "Title", "Description");

        Post deletePost = postService.delete(firstPost);

        assertEquals("1", deletePost.getId().toString());
        assertEquals("Title", deletePost.getTitle());
        assertEquals("Description", deletePost.getDescription());
        verify(postRepository, times(1)).delete(deletePost);
    }

    private Post createPost(Long id, String title, String description) {
        Post post = new Post();
        post.setId(id);
        post.setTitle(title);
        post.setDescription(description);
        post.setCreatedDate(LocalDateTime.now());
        return post;
    }
}
