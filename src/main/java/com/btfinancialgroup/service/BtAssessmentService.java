package com.btfinancialgroup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btfinancialgroup.client.ApiProviderClient;
import com.btfinancialgroup.provider.dto.BlogPost;
import com.btfinancialgroup.provider.dto.User;

@Service
public class BtAssessmentService {
	private ApiProviderClient apiProviderClient;
	
	@Autowired
	public BtAssessmentService(ApiProviderClient apiProviderClient) {
		this.apiProviderClient = apiProviderClient;
	}
	
	public User getUser(Long id) {
		return apiProviderClient.getUser(id);
	}
	
	public List<User> getUsers() {
		return apiProviderClient.getUsers();
	}

	public BlogPost getPost(Long id) {
		return apiProviderClient.getPost(id);
	}	
	
	public List<BlogPost> getPosts() {
		return apiProviderClient.getPosts();
	}
}
