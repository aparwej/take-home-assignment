package com.marionete.services.mock;

import com.marionete.services.model.AccountInfo;

public class AccountInfoMockFactory {

	public static AccountInfo getAccountInfo() {
		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setAccountNumber("11223");

		return accountInfo;
	}

	public static AccountInfo getAccountInfo(String accountNumber) {
		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setAccountNumber(accountNumber);
		return accountInfo;
	}
}
