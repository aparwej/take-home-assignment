package com.marionete.services.service;

import com.marionete.services.model.UserAccount;

public interface UserAccountService {

	public UserAccount getUserAccount(String loginToken);
}
