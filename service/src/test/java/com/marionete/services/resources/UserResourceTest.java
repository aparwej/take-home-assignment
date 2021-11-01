package com.marionete.services.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.marionete.services.grpc.LoginClient;
import com.marionete.services.mock.UserAccountMockFactory;
import com.marionete.services.mock.UserAccountRequestMockFactory;
import com.marionete.services.model.UserAccount;
import com.marionete.services.service.UserAccountService;

import services.LoginResponse;

public class UserResourceTest {
	
	@InjectMocks
	UserResource userResource = new UserResource();

	@Mock
	UserAccountService userAccountService;

	@Mock
	LoginClient loginClient;

	@Before
	public void init() throws IOException, InterruptedException {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getUserAccountTestWithInvalidUser() {

		Mockito.when(userAccountService.getUserAccount(Mockito.anyString()))
				.thenReturn(UserAccountMockFactory.getUserAccount());
		Mockito.when(loginClient.login(Mockito.any())).thenReturn(LoginResponse.getDefaultInstance());

		ResponseEntity<UserAccount> responseEntity = userResource
				.getUserAccount(UserAccountRequestMockFactory.getInvalidUser());
		assertEquals(responseEntity.getStatusCode(), HttpStatus.UNAUTHORIZED);
	}

	@Test
	public void getUserAccountTestWithValidUser() {

		Mockito.when(userAccountService.getUserAccount(Mockito.anyString()))
				.thenReturn(UserAccountMockFactory.getUserAccount());
		Mockito.when(loginClient.login(Mockito.any()))
				.thenReturn(LoginResponse.newBuilder().setToken("2233445").build());

		ResponseEntity<UserAccount> responseEntity = userResource
				.getUserAccount(UserAccountRequestMockFactory.getValidUser());
		assertEquals(UserAccountMockFactory.getUserAccount().getAccountInfo().getAccountNumber(),
				responseEntity.getBody().getAccountInfo().getAccountNumber());
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}
}
