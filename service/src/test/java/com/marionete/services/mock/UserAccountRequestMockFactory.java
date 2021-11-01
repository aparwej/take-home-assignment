package com.marionete.services.mock;

import com.marionete.services.model.UserAccountRequest;

public class UserAccountRequestMockFactory {
	
	public static UserAccountRequest getValidUser() {
		UserAccountRequest userAccountRequest = new UserAccountRequest();
		userAccountRequest.setUserName("test");
		userAccountRequest.setPassword("VGVzdA==");
		return userAccountRequest;
	}
	
	public static UserAccountRequest getInvalidUser() {
		UserAccountRequest userAccountRequest = new UserAccountRequest();
		userAccountRequest.setUserName("test");
		userAccountRequest.setPassword("pass");
		return userAccountRequest;
	}

}
