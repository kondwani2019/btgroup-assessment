package com.btfinancialgroup.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.btfinancialgroup.provider.dto.BlogPost;
import com.btfinancialgroup.provider.dto.User;

@FeignClient(name="ApiProvider", url="${api.provider.url}")
public interface ApiProviderClient {

	@RequestMapping(method = RequestMethod.GET, path = "/users/{id}")
	User getUser(@PathVariable(name = "id", required = false)Long id);
	
	@RequestMapping(method = RequestMethod.GET, value = "/users")
	List<User> getUsers();
	
	@RequestMapping(method = RequestMethod.GET, path = "/posts/{id}")
	BlogPost getPost(@PathVariable(name = "id", required = false)Long id);
	
	@RequestMapping(method = RequestMethod.GET, value = "/posts")
	List<BlogPost> getPosts();
}
