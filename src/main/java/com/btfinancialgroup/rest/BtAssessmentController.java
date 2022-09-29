package com.btfinancialgroup.rest;

import java.util.List;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.btfinancialgroup.provider.dto.BlogPost;
import com.btfinancialgroup.provider.dto.User;
import com.btfinancialgroup.service.BtAssessmentService;

@RequestScope
@RestController
@RequestMapping("btfinancialgroup")
public class BtAssessmentController {

	private BtAssessmentService btAssessmentService;

	@Autowired
	public BtAssessmentController(BtAssessmentService btAssessmentService) {
		this.btAssessmentService = btAssessmentService;
	}

	@GetMapping(value = "/users")
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<List<User>> getUsers() {
		List<User> users = btAssessmentService.getUsers();
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}

	@GetMapping(path = "/users/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<User> getUser(@PathVariable(name = "id", required = false) Long id) {
		User user = btAssessmentService.getUser(id);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}

	@GetMapping(value = "/posts")
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<List<BlogPost>> getPosts() {
		List<BlogPost> posts = btAssessmentService.getPosts();
		return ResponseEntity.status(HttpStatus.OK).body(posts);
	}

	@GetMapping(path = "/posts/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<BlogPost> getPosts(@PathVariable(name = "id", required = false) Long id) {
		BlogPost post = btAssessmentService.getPost(id);
		return ResponseEntity.status(HttpStatus.OK).body(post);
	}

}
