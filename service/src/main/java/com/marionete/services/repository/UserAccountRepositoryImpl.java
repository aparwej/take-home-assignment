package com.marionete.services.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.marionete.services.exception.ServiceBusinessException;
import com.marionete.services.model.UserInfo;
import com.marionete.services.rest.operation.GetUserAccountOperation;
import com.marionete.services.rest.support.RestServiceSupport;

@Repository
public class UserAccountRepositoryImpl implements UserAccountRepository {

	@Value("${user.service.endpoint}")
	String endpoint;

	@Autowired
	RestServiceSupport restServiceSupport;

	@Override
	public UserInfo getUserAccount(String loginToken) {
		GetUserAccountOperation getUserAccountOperation = new GetUserAccountOperation();
		UserInfo userInfo = new UserInfo();
		try {
			userInfo =  (UserInfo)  restServiceSupport.call(endpoint, getUserAccountOperation, loginToken);
		} catch (ServiceBusinessException e) {
			e.printStackTrace(System.err);
			System.err.println("Failed to Retrieve User Details from Service " + e.getMessage());
		}
		return userInfo;
	}

}
