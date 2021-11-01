package com.marionete.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URI;

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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marionete.services.mock.AccountInfoMockFactory;
import com.marionete.services.mock.UserAccountRequestMockFactory;
import com.marionete.services.mock.UserInfoMockFactory;
import com.marionete.services.model.UserAccount;
import com.marionete.services.model.UserAccountRequest;
import com.marionete.services.resources.UserResource;
import com.marionete.services.rest.operation.GetAccountInfoOperation;
import com.marionete.services.rest.operation.GetUserAccountOperation;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserResourceIntegrationTest {

	@LocalServerPort
	private int port;

	@Autowired
	UserResource userResource;

	@Autowired
	RestTemplate restTemplate;

	@Value("${account.service.endpoint}")
	String accountServiceEndpoint;

	@Value("${user.service.endpoint}")
	String userServiceEndpoint;

	MockRestServiceServer mockServer;
	ObjectMapper mapper = new ObjectMapper();
	TestRestTemplate testRestTemplate = new TestRestTemplate();

	@Before
	public void init() throws IOException, InterruptedException {
		mockServer = MockRestServiceServer.createServer(restTemplate);
	}

	@Test
	public void successfulMessageForValidUser() throws Exception {

		mockServer
				.expect(ExpectedCount.once(),
						MockRestRequestMatchers
								.requestTo(new URI(accountServiceEndpoint + new GetAccountInfoOperation().getPath())))
				.andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
				.andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
						.body(mapper.writeValueAsString(AccountInfoMockFactory.getAccountInfo())));

		mockServer
				.expect(ExpectedCount.once(),
						MockRestRequestMatchers
								.requestTo(new URI(userServiceEndpoint + new GetUserAccountOperation().getPath())))
				.andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
				.andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
						.body(mapper.writeValueAsString(UserInfoMockFactory.getUserInfo())));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserAccountRequest> request = new HttpEntity<>(UserAccountRequestMockFactory.getValidUser(),
				headers);

		ResponseEntity<UserAccount> postForEntity = testRestTemplate
				.postForEntity("http://localhost:" + port + "/marionete/useraccount", request, UserAccount.class);
		assertEquals("11223", postForEntity.getBody().getAccountInfo().getAccountNumber());
		assertEquals(postForEntity.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void notAuthorizedMessageForInvalidUser() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserAccountRequest> request = new HttpEntity<>(UserAccountRequestMockFactory.getInvalidUser(),
				headers);

		ResponseEntity<UserAccount> postForEntity = testRestTemplate
				.postForEntity("http://localhost:" + port + "/marionete/useraccount", request, UserAccount.class);
		assertEquals(null, postForEntity.getBody());
		assertEquals(postForEntity.getStatusCode(), HttpStatus.UNAUTHORIZED);
	}

	@Test
	public void successfulMessageForValidUserAccountServiceFailure() throws Exception {

		mockServer
				.expect(ExpectedCount.once(),
						MockRestRequestMatchers
								.requestTo(new URI(accountServiceEndpoint + new GetAccountInfoOperation().getPath())))
				.andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
				.andRespond(MockRestResponseCreators.withStatus(HttpStatus.TOO_MANY_REQUESTS).body(""));

		mockServer
				.expect(ExpectedCount.once(),
						MockRestRequestMatchers
								.requestTo(new URI(userServiceEndpoint + new GetUserAccountOperation().getPath())))
				.andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
				.andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
						.body(mapper.writeValueAsString(UserInfoMockFactory.getUserInfo())));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UserAccountRequest> request = new HttpEntity<>(UserAccountRequestMockFactory.getValidUser(),
				headers);

		ResponseEntity<UserAccount> postForEntity = testRestTemplate
				.postForEntity("http://localhost:" + port + "/marionete/useraccount", request, UserAccount.class);
		assertEquals(null, postForEntity.getBody().getAccountInfo().getAccountNumber());
		assertEquals(postForEntity.getStatusCode(), HttpStatus.OK);
	}
}
