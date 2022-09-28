package com.btfinancialgroup.service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.btfinancialgroup.client.ApiProviderClient;
import com.btfinancialgroup.provider.dto.BlogPost;
import com.btfinancialgroup.provider.dto.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class BtAssessmentServiceTest {

	@Mock
	private ApiProviderClient apiProviderClient;
	
	private BtAssessmentService btAssessmentService;
	
	@BeforeEach
	public void init() {
		btAssessmentService = new BtAssessmentService(apiProviderClient);
	}
	
	@Test
	/**
	 * Verify that if my Feign Client returns correct results 
	 * then I will get the correct results from my Service
	 *
	 */
	public void testGetUser() {
		User expectedUserResult = new User();
		expectedUserResult.setId(1L);
		expectedUserResult.setName("Jim");
		
		when(apiProviderClient.getUser(1L)).thenReturn(expectedUserResult);
		
		User foundUser = btAssessmentService.getUser(1L);
		
		assertEquals("Jim", foundUser.getName());
	}
	
	@Test
	public void testGetUsers() {
		User expectedUserResult = new User();
		expectedUserResult.setId(1L);
		expectedUserResult.setName("Jim");
		List<User> expectedUsers = new ArrayList();
		expectedUsers.add(expectedUserResult);
		
		when(apiProviderClient.getUsers()).thenReturn(expectedUsers);
		
		List<User> foundUsers = btAssessmentService.getUsers();
		
		assertEquals(1, foundUsers.size());
		assertEquals("Jim", foundUsers.get(0).getName());
	}
	
	/**
	 * Verify that if my Feign Client returns correct results 
	 * then I will get the correct results from my Service
	 *
	 */
	@Test
	public void testGetPost() {
		BlogPost blogResult = new BlogPost();
		blogResult.setId(1L);
		blogResult.setUserId(1L);
		
		String blogTitle = "Is Java trying to do too much?";
		blogResult.setTitle(blogTitle);
		
		when(apiProviderClient.getPost(1L)).thenReturn(blogResult);
		
		BlogPost foundBlog = btAssessmentService.getPost(1L);
		assertEquals(blogTitle, foundBlog.getTitle());
	}
	
	@Test
	public void testGetPosts() {
		BlogPost blogResult = new BlogPost();
		blogResult.setId(1L);
		blogResult.setUserId(1L);
		
		String blogTitle = "Is Java trying to do too much?";
		blogResult.setTitle(blogTitle);
		List<BlogPost> expectedBlogs = new ArrayList();
		expectedBlogs.add(blogResult);
		
		when(apiProviderClient.getPosts()).thenReturn(expectedBlogs);
		
		List<BlogPost> foundBlogs = btAssessmentService.getPosts();
		
		assertEquals(1, foundBlogs.size());
		assertEquals(blogTitle, foundBlogs.get(0).getTitle());
	}
	
	/**
	 * Verify that json strings can be unmarshalled
	 *
	 */
	@Test
	public void testUnmarshalUser() throws IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		File userJsonFile = new File(this.getClass().getResource("/json/user-sample.json").getFile());
		User user = new ObjectMapper().readValue(userJsonFile, User.class);
		
		assertEquals("Leanne Graham", user.getName());
	}
	
	
	/**
	 * Verify that json strings can be unmarshalled
	 *
	 */
	@Test
	public void testUnmarshalBlogPost() throws IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		File blogPostJsonFile = new File(this.getClass().getResource("/json/blog-post-sample.json").getFile());
		BlogPost blogPost = new ObjectMapper().readValue(blogPostJsonFile, BlogPost.class);
		
		assertEquals(1L, blogPost.getId());
		
		File multipleBlogPostsFile = new File(this.getClass().getResource("/json/multiple-blog-posts.json").getFile());
		List<BlogPost> blogPosts = new ObjectMapper().readValue(multipleBlogPostsFile, List.class);
		
		assertTrue(blogPosts.size() > 1);
	}
}
