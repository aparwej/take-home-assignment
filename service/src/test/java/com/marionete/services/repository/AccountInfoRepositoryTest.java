package com.marionete.services.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.marionete.services.exception.ServiceBusinessException;
import com.marionete.services.mock.AccountInfoMockFactory;
import com.marionete.services.model.AccountInfo;
import com.marionete.services.rest.support.RestServiceSupport;

@RunWith(MockitoJUnitRunner.class)
public class AccountInfoRepositoryTest {

	@InjectMocks
	AccountInfoRepository accountInfoRepository = new AccountInfoRepositoryImpl();

	@Mock
	RestServiceSupport restServiceSupport;


	@Before
	public void init() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		MockitoAnnotations.initMocks(this);
		accountInfoRepository.getClass().getDeclaredField("endpoint").set(accountInfoRepository, "345");
	}

	@Test
	public void getAccountInfoTest() throws ServiceBusinessException {

		Mockito.when(restServiceSupport.call(Mockito.anyString(), Mockito.any(), Mockito.anyString())).thenReturn(AccountInfoMockFactory.getAccountInfo());
		AccountInfo accountInfo = accountInfoRepository.getAccountInfo("1234");
		assertEquals(AccountInfoMockFactory.getAccountInfo().getAccountNumber(),
				accountInfo.getAccountNumber());
	}
}
