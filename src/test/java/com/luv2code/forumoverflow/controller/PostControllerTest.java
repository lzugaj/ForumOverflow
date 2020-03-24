package com.luv2code.forumoverflow.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.forumoverflow.domain.Category;
import com.luv2code.forumoverflow.domain.ContentStatus;
import com.luv2code.forumoverflow.domain.Post;
import com.luv2code.forumoverflow.domain.User;
import com.luv2code.forumoverflow.domain.UserStatus;
import com.luv2code.forumoverflow.rest.controller.PostController;
import com.luv2code.forumoverflow.service.PostService;

/**
 * Created by lzugaj on Sunday, February 2020
 */

@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)
public class PostControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private PostService postService;

//	@Test
//	public void testSave() throws Exception {
//	    Long contentStatusId = 1L;
//        ContentStatus contentStatus = createContentStatus(contentStatusId, "VALID");
//
//        Long userStatusId = 1L;
//        UserStatus userStatus = new UserStatus(userStatusId, "ACTIVE", null);
//
//        Long userId = 1L;
//        User user = new User(userId, "Luka", "Žugaj", "lzugaj", "lzugaj@gmail.com", "#Lzugaj11", 0, userStatus, null, null, null);
//
//        Long categoryId = 1L;
//        Category category = createCategory(categoryId, "Feed");
//
//	    Long postId = 1L;
//        Post post = createPost(postId, "Title", "Description");
//        post.setContentStatus(contentStatus);
//        post.setCategory(category);
//
//        when(postService.save(user.getUsername(), post)).thenReturn(post);
//
//        this.mockMvc
//                .perform(
//                        post("/post")
//                            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
//                            .content(objectMapper.writeValueAsString(post))
//                )
//                .andExpect(status().isCreated());
//    }

    @Test
    public void testFindAllByCategory() throws Exception {
        Long firstCategoryId = 1L;
        Category firstCategory = createCategory(firstCategoryId, "Feed");

        Long secondCategoryId = 2L;
        Category secondCategory = createCategory(secondCategoryId, "Marketing");

        Long firstPostId = 1L;
        Post firstPost = createPost(firstPostId, "Title 1", "Description 1");
        firstPost.setCategory(firstCategory);

        Long secondPostId = 1L;
        Post secondPost = createPost(secondPostId, "Title 2", "Description 2");
        secondPost.setCategory(firstCategory);

        Long thirdPostId = 1L;
        Post thirdPost = createPost(thirdPostId, "Title 3", "Description 3");
        thirdPost.setCategory(secondCategory);

        List<Post> posts = new ArrayList<>();
        posts.add(firstPost);
        posts.add(secondPost);

        when(postService.findAllByCategory(firstCategory.getId())).thenReturn(posts);

        this.mockMvc
                .perform(
                        post("/post/{categoryId}", firstCategory.getId())
                            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .content(objectMapper.writeValueAsString(posts))
                )
                .andExpect(status().isOk());
    }

    @Test
    public void testFindById() throws Exception {
        Long postId = 1L;
        Post post = createPost(postId, "Title", "Description");

        when(postService.findById(post.getId())).thenReturn(post);

        this.mockMvc
                .perform(
                        get("/post/{id}", post.getId())
                            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .content(objectMapper.writeValueAsString(post))
                )
                .andExpect(status().isOk());
    }

    @Test
    public void testFindByIdNotFound() throws Exception {
        Long postId = 1L;
        Post post = createPost(postId, "Title", "Description");

        when(postService.findById(post.getId())).thenReturn(null);

        this.mockMvc
                .perform(
                        get("/post/{id}", post.getId())
                            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .content(objectMapper.writeValueAsString(post))
                )
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFindAll() throws Exception {
        Long firstPostId = 1L;
        Post firstPost = createPost(firstPostId, "Title 1", "Description 1");

        Long secondPostId = 1L;
        Post secondPost = createPost(secondPostId, "Title 2", "Description 2");

        Long thirdPostId = 1L;
        Post thirdPost = createPost(thirdPostId, "Title 3", "Description 3");

        List<Post> posts = new ArrayList<>();
        posts.add(firstPost);
        posts.add(secondPost);
        posts.add(thirdPost);

        when(postService.findAll()).thenReturn(posts);

        this.mockMvc
                .perform(
                        get("/post")
                            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .content(objectMapper.writeValueAsString(posts))
                )
                .andExpect(status().isOk());
    }

    @Test
    public void testFindAllReported() throws Exception {
        Long validContentStatusId = 1L;
        ContentStatus validContentStatus = createContentStatus(validContentStatusId, "VALID");

        Long invalidContentStatusId = 1L;
        ContentStatus invalidContentStatus = createContentStatus(invalidContentStatusId, "INVALID");

        Long firstPostId = 1L;
        Post firstPost = createPost(firstPostId, "Title 1", "Description 1");
        firstPost.setContentStatus(validContentStatus);

        Long secondPostId = 1L;
        Post secondPost = createPost(secondPostId, "Title 2", "Description 2");
        secondPost.setContentStatus(invalidContentStatus);

        Long thirdPostId = 1L;
        Post thirdPost = createPost(thirdPostId, "Title 3", "Description 3");
        thirdPost.setContentStatus(invalidContentStatus);

        List<Post> posts = new ArrayList<>();
        posts.add(secondPost);
        posts.add(thirdPost);

        when(postService.findAllReported()).thenReturn(posts);

        this.mockMvc
                .perform(
                        get("/post/reported")
                            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .content(objectMapper.writeValueAsString(posts))
                )
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdate() throws Exception {
        Long firstPostId = 1L;
        Post firstPost = createPost(firstPostId, "Title 1", "Description 1");

        Long secondPostId = 1L;
        Post secondPost = createPost(secondPostId, "Title 2", "Description 2");

        when(postService.findById(firstPost.getId())).thenReturn(firstPost);
        when(postService.update(firstPost, secondPost)).thenReturn(secondPost);

        this.mockMvc
                .perform(
                        put("/post/{id}", firstPost.getId())
                            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .content(objectMapper.writeValueAsString(secondPost))
                )
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateNotFound() throws Exception {
        Long firstPostId = 1L;
        Post firstPost = createPost(firstPostId, "Title 1", "Description 1");

        when(postService.findById(firstPost.getId())).thenReturn(null);

        this.mockMvc
                .perform(
                        put("/post/{id}", firstPost.getId())
                            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .content(objectMapper.writeValueAsString(firstPost))
                )
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateContentStatus() throws Exception {
        Long contentStatusId = 1L;
        ContentStatus contentStatus = createContentStatus(contentStatusId, "INVALID");

        Long postId = 1L;
        Post post = createPost(postId, "Title", "Description");

        when(postService.findById(post.getId())).thenReturn(post);
        when(postService.updateStatus(post, contentStatus)).thenReturn(post);

        this.mockMvc
                .perform(
                        put("/post/info/{id}", post.getId())
                            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .content(objectMapper.writeValueAsString(contentStatus))
                )
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateContentStatusNotFound() throws Exception {
        Long contentStatusId = 1L;
        ContentStatus contentStatus = createContentStatus(contentStatusId, "INVALID");

        Long postId = 1L;
        Post post = createPost(postId, "Title", "Description");

        when(postService.findById(post.getId())).thenReturn(null);

        this.mockMvc
                .perform(
                        put("/post/info/{id}", post.getId())
                            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .content(objectMapper.writeValueAsString(contentStatus))
                )
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDelete() throws Exception {
        Long postId = 1L;
        Post post = createPost(postId, "Title", "Description");

        when(postService.findById(post.getId())).thenReturn(post);
        when(postService.delete(post)).thenReturn(post);

        this.mockMvc
                .perform(
                        delete("/post/{id}", post.getId())
                            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .content(objectMapper.writeValueAsString(post))
                )
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        Long postId = 1L;
        Post post = createPost(postId, "Title", "Description");

        when(postService.findById(post.getId())).thenReturn(null);

        this.mockMvc
                .perform(
                        delete("/post/{id}", post.getId())
                            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                            .content(objectMapper.writeValueAsString(post))
                )
                .andExpect(status().isNotFound());
    }

    private ContentStatus createContentStatus(Long id, String name) {
	    ContentStatus contentStatus = new ContentStatus();
	    contentStatus.setId(id);
	    contentStatus.setName(name);
	    contentStatus.setPosts(null);
	    return contentStatus;
    }

    private Category createCategory(Long id, String name) {
	    Category category = new Category();
	    category.setId(id);
	    category.setName(name);
	    category.setPosts(null);
	    return category;
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
