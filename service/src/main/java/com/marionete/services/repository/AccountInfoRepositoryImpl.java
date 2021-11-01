package com.marionete.services.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.marionete.services.exception.ServiceBusinessException;
import com.marionete.services.model.AccountInfo;
import com.marionete.services.rest.operation.GetAccountInfoOperation;
import com.marionete.services.rest.support.RestServiceSupport;

@Repository
public class AccountInfoRepositoryImpl implements AccountInfoRepository {

	@Value("${account.service.endpoint}")
	String endpoint;

	@Autowired
	RestServiceSupport restServiceSupport;

	@Override
	public AccountInfo getAccountInfo(String loginToken) {
		GetAccountInfoOperation getUserAccountOperation = new GetAccountInfoOperation();
		AccountInfo accountInfo = new AccountInfo();
		try {
			accountInfo = (AccountInfo) restServiceSupport.call(endpoint, getUserAccountOperation, loginToken);
		} catch (ServiceBusinessException e) {
			e.printStackTrace(System.err);
			System.err.println("Failed to Retrieve Account Details from Service " + e.getMessage());
		}
		return accountInfo;
	}

}
