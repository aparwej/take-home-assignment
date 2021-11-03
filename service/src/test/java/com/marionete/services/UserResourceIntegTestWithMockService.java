package com.marionete.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marionete.backends.AccountInfoMock;
import com.marionete.backends.UserInfoMock;
import com.marionete.services.mock.UserAccountRequestMockFactory;
import com.marionete.services.model.UserAccount;
import com.marionete.services.model.UserAccountRequest;
import com.marionete.services.resources.UserResource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
public class UserResourceIntegTestWithMockService {

	@Value("${server.port}")
	Integer port;

	@Autowired
	UserResource userResource;

	@Autowired
	RestTemplate restTemplate;

	@Value("${account.service.endpoint}")
	String accountServiceEndpoint;

	@Value("${user.service.endpoint}")
	String userServiceEndpoint;

	ObjectMapper mapper = new ObjectMapper();
	TestRestTemplate testRestTemplate = new TestRestTemplate();

	@Before
	public void init() throws IOException, InterruptedException {
		UserInfoMock.start();
		AccountInfoMock.start();
	}

	@Test
	public void getUserAccountTest() throws Exception {

		String apiEndPoint = "http://localhost:"+port+"/marionete/useraccount";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserAccountRequest> request = new HttpEntity<>(UserAccountRequestMockFactory.getValidUser(),
				headers);

		//First few Account Service call will fail as per backend service design. As a result 'useraccount' service response
		//will not have any value for account number. Server will internally log message for account service unavailability
		ResponseEntity<UserAccount> postForEntity = testRestTemplate
				.postForEntity(apiEndPoint, request, UserAccount.class);
		assertEquals(postForEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(null, postForEntity.getBody().getAccountInfo().getAccountNumber());
		assertEquals("John", postForEntity.getBody().getUserInfo().getName());

		postForEntity = testRestTemplate
				.postForEntity(apiEndPoint, request, UserAccount.class);
		assertEquals(postForEntity.getStatusCode(), HttpStatus.OK);
		assertEquals(null, postForEntity.getBody().getAccountInfo().getAccountNumber());
		assertEquals("Doe", postForEntity.getBody().getUserInfo().getSurname());

		//This Call onwards response with have account details
		postForEntity = testRestTemplate
				.postForEntity(apiEndPoint, request, UserAccount.class);
		assertEquals(postForEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("12345-3346-3335-4456", postForEntity.getBody().getAccountInfo().getAccountNumber());
		assertEquals("John", postForEntity.getBody().getUserInfo().getName());
		assertEquals("Doe", postForEntity.getBody().getUserInfo().getSurname());
		assertEquals(32, postForEntity.getBody().getUserInfo().getAge());
		assertEquals("male", postForEntity.getBody().getUserInfo().getSex());

		postForEntity = testRestTemplate
				.postForEntity("http://localhost:" + port + "/marionete/useraccount", request, UserAccount.class);
		assertEquals(postForEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("12345-3346-3335-4456", postForEntity.getBody().getAccountInfo().getAccountNumber());
		assertEquals("John", postForEntity.getBody().getUserInfo().getName());
		assertEquals("Doe", postForEntity.getBody().getUserInfo().getSurname());
		assertEquals(32, postForEntity.getBody().getUserInfo().getAge());
		assertEquals("male", postForEntity.getBody().getUserInfo().getSex());
	}
}
