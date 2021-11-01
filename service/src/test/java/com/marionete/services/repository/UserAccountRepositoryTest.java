package com.marionete.services.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.marionete.services.exception.ServiceBusinessException;
import com.marionete.services.mock.UserInfoMockFactory;
import com.marionete.services.model.UserInfo;
import com.marionete.services.rest.support.RestServiceSupport;

public class UserAccountRepositoryTest {

	@InjectMocks
	UserAccountRepository userAccountRepository = new UserAccountRepositoryImpl();

	@Mock
	RestServiceSupport restServiceSupport;

	@Before
	public void init() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		MockitoAnnotations.initMocks(this);
		userAccountRepository.getClass().getDeclaredField("endpoint").set(userAccountRepository, "345");
	}

	@Test
	public void getAccountInfoTest() throws ServiceBusinessException {

		Mockito.when(restServiceSupport.call(Mockito.anyString(), Mockito.any(), Mockito.anyString()))
				.thenReturn(UserInfoMockFactory.getUserInfo());
		UserInfo userAccount = userAccountRepository.getUserAccount("1234");
		assertEquals(UserInfoMockFactory.getUserInfo().getName(), userAccount.getName());
	}

}
