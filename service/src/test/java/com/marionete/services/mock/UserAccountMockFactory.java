package com.marionete.services.mock;

import com.marionete.services.model.UserAccount;

public class UserAccountMockFactory {

	public static UserAccount getUserAccount() {
		UserAccount userAccount = new UserAccount();
		userAccount.setAccountInfo(AccountInfoMockFactory.getAccountInfo());
		userAccount.setUserInfo(UserInfoMockFactory.getUserInfo());
		return userAccount;
	}

}
