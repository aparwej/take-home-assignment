package com.marionete.services.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.marionete.services.mock.AccountInfoMockFactory;
import com.marionete.services.mock.UserInfoMockFactory;
import com.marionete.services.model.UserAccount;
import com.marionete.services.repository.AccountInfoRepository;
import com.marionete.services.repository.UserAccountRepository;
import com.marionete.services.service.UserAccountService;
import com.marionete.services.service.UserAccountServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserAccountServiceTest {

	@InjectMocks
	UserAccountService userAccountService = new UserAccountServiceImpl();

	@Mock
	UserAccountRepository userAccountRepository;

	@Mock
	AccountInfoRepository accountInfoRepository;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getUserAccountTest() {

		Mockito.when(userAccountRepository.getUserAccount(Mockito.anyString())).thenReturn(UserInfoMockFactory.getUserInfo());
		Mockito.when(accountInfoRepository.getAccountInfo(Mockito.anyString())).thenReturn(AccountInfoMockFactory.getAccountInfo());
		UserAccount userAccount = userAccountService.getUserAccount("123");
		assertEquals(AccountInfoMockFactory.getAccountInfo().getAccountNumber(),
				userAccount.getAccountInfo().getAccountNumber());
	}

}
