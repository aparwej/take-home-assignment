package com.marionete.services.rest.support;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.marionete.services.exception.ServiceBusinessException;
import com.marionete.services.mock.AccountInfoMockFactory;
import com.marionete.services.model.AccountInfo;
import com.marionete.services.rest.operation.GetAccountInfoOperation;

public class RestServiceSupportTest {

	@InjectMocks
	RestServiceSupport restServiceSupport = new RestServiceSupport();

	@Mock
	RestTemplate restTemplate;


	@Before
	public void init() throws IOException, InterruptedException {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void callTestForSuccessResponse() throws ServiceBusinessException {

		Mockito.when(restTemplate.exchange(Mockito.anyString(), (HttpMethod)Mockito.any(), Mockito.any(), Mockito.any(Class.class)))
				.thenReturn(ResponseEntity.ok(AccountInfoMockFactory.getAccountInfo()));
		
		AccountInfo accountInfo = (AccountInfo)restServiceSupport.call("testURL", new GetAccountInfoOperation(), "1122334");
		assertEquals(AccountInfoMockFactory.getAccountInfo().getAccountNumber(), accountInfo.getAccountNumber());
	}

	

}
