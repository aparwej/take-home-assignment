package com.marionete.services.mock;

import com.marionete.services.model.UserInfo;

public class UserInfoMockFactory {
	
	public static UserInfo getUserInfo() {
		UserInfo userInfo = new UserInfo();
		userInfo.setName("Jane");
		userInfo.setSex("F");
		userInfo.setSurname("Doe");
		userInfo.setAge(25);
		return userInfo;
	}
	
	public static UserInfo getUserInfo(String name) {
		UserInfo userInfo = new UserInfo();
		userInfo.setName(name);
		return userInfo;
	}
	

}
