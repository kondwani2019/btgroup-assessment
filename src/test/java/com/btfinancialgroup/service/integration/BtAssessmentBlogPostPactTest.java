package com.btfinancialgroup.service.integration;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;

import com.btfinancialgroup.provider.dto.BlogPost;
import com.fasterxml.jackson.databind.ObjectMapper;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.model.RequestResponsePact;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "BlogApiClientProvider")
public class BtAssessmentBlogPostPactTest {
	
	//  We may also want to create a Pact Test with Integration to a pact repo with payloads
	@Pact(provider="BlogApiClientProvider", consumer="test_consumer")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
		Map<String, String> headers = new HashedMap<>();
		headers.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		
		DslPart consumerExpectation = new PactDslJsonBody()
				.integerType("id", 1)
				.integerType("userId", 1)
				.stringType("title", "How to write a Pact Test");
		
        return builder
            .given("There exists a Blog Post for id 1")
            .uponReceiving("A request for id 1")
                .path("/users/1")
                .method("GET")
            .willRespondWith()
                .status(200)
                .headers(headers)
                .body(consumerExpectation)
            .toPact();
    }
	
	@Test
	public void testUserExistsApi(MockServer mockServer) throws ClientProtocolException, IOException {
		HttpUriRequest request = new HttpGet(mockServer.getUrl()+ "/users/1");
		HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
		
		String jsonResponse = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
		BlogPost blogPost = new ObjectMapper().readValue(jsonResponse, BlogPost.class);
		
		assertEquals(httpResponse.getStatusLine().getStatusCode(), 200);
		
		//  We can marshal the response in the contract to a BlogPost
		assertNotNull(blogPost);
		assertEquals("How to write a Pact Test", blogPost.getTitle());
		
	}
}
